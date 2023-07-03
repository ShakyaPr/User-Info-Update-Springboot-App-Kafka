package com.shakya.userinfochangeapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shakya.userinfochangeapp.model.*;
import com.shakya.userinfochangeapp.repository.UserInfoRepository;
import com.shakya.userinfochangeapp.service.EventProducerService;
import com.shakya.userinfochangeapp.service.GitHubService;
import com.shakya.userinfochangeapp.service.PayloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shakya.userinfochangeapp.constants.UserInfoConstants;

import java.util.*;

@RestController
public class RestControllerForService {

    @Autowired
    private GitHubService gitHubService;
    @Autowired
    private PayloadService payloadService;
    private UserInfoRepository userInfoRepository;
    @Autowired
    private EventProducerService eventProducerService;

    @PutMapping(value = "/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUserInfo(@PathVariable("user_id") String userId, @RequestBody UserData userData) throws JSONException, JsonProcessingException {

        JSONObject userInfoJsonObject = gitHubService.getUserDetails(userId);
        List<String> followers = gitHubService.getFollowers(userId);
        List<String> repos = gitHubService.getRepos(userId);

        Map<String, Object> userInfo = Map.of(UserInfoConstants.BASIC_USER_DETAILS, userInfoJsonObject,
                UserInfoConstants.FOLLOWERS, followers,
                UserInfoConstants.REPOSITORIES, repos,
                UserInfoConstants.REQUEST_BODY, userData);

        Payload payload = payloadService.createPayload(userInfo);
        payloadService.savePayload(payload);

        return ResponseEntity.ok("Updated successfully");
    }

    @PostMapping("/produce/{user_id}")
    public ResponseEntity<?> produceUserEvent(@PathVariable("user_id") String userId) throws JsonProcessingException {

        Payload payload = payloadService.getPayloadById(Integer.parseInt(userId));
        UserInfoChangeEvent userInfoChangeEvent;
        if (payload != null) {
            userInfoChangeEvent = eventProducerService.createProducerEvent(payload);
            eventProducerService.sendEvent(userInfoChangeEvent);
            return ResponseEntity.ok(userInfoChangeEvent);
        } else {
            return new ResponseEntity("User information for given ID hasn't been updated", HttpStatus.BAD_REQUEST);
        }
    }
}
