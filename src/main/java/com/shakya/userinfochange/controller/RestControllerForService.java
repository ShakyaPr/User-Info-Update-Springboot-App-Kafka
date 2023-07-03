package com.shakya.userinfochange.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shakya.userinfochange.model.Meta;
import com.shakya.userinfochange.model.User;
import com.shakya.userinfochange.model.UserData;
import com.shakya.userinfochange.model.UserInfoChangeEvent;
import com.shakya.userinfochange.repository.UserInfoRepository;
import com.shakya.userinfochange.service.GitHubService;
import com.shakya.userinfochange.service.CreatePayloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class RestControllerForService {

    @Autowired
    private GitHubService gitHubService;
    @Autowired
    private CreatePayloadService createPayloadService;

    @PutMapping(value = "/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUserInfo(@PathVariable("user_id") String userId, @RequestBody UserData userData) throws JSONException, JsonProcessingException {

        JSONObject userInfoJsonObject = gitHubService.getUserDetails(userId);
        List<String> followers = gitHubService.getFollowers(userId);
        List<String> repos = gitHubService.getRepos(userId);

        Map<String, Object> userInfo = Map.of("basic_details", userInfoJsonObject,
                "followers", followers,
                "repos", repos,
                "user_data", userData);

        createPayloadService.createAndSavePayload(userInfo);

        return ResponseEntity.ok("Updated successfully");
    }

    @PostMapping("/produce/{user_id}")
    public ResponseEntity<String> produceUserEvent(@PathVariable("user_id") String userId) {

        return ResponseEntity.ok("User event produced successfully!");
    }
}
