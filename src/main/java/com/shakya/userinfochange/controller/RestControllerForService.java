package com.shakya.userinfochange.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shakya.userinfochange.model.UserData;
import com.shakya.userinfochange.model.UserInfoChangeEvent;
import com.shakya.userinfochange.service.GitHubService;
import com.shakya.userinfochange.service.UpdateUserInfoService;
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

@RestController
public class RestControllerForService {

    @Autowired
    private GitHubService gitHubService;

    @PutMapping(value = "/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoChangeEvent> updateUserInfo(@PathVariable("user_id") String userId, @RequestBody UserData userData) throws JSONException, JsonProcessingException {

        HashMap<String, Object> userInfo = new HashMap<>();
        JSONObject userInfoJsonObject = gitHubService.getUserDetails(userId);
        List<String> followers = gitHubService.getFollowers(userId);
        List<String> repos = gitHubService.getRepos(userId);

        userInfo.put("basic_details", userInfoJsonObject);
        userInfo.put("followers", followers);
        userInfo.put("repos", repos);
        userInfo.put("user_data", userData);

        UserInfoChangeEvent userInfoChangeEvent = UpdateUserInfoService.updateUserInfo(userInfo);

        System.out.println("UserInfoChangeEvent: " + userInfoChangeEvent);
        return ResponseEntity.ok(userInfoChangeEvent);
    }

    @PostMapping("/produce/{user_id}")
    public ResponseEntity<String> produceUserEvent(@PathVariable("user_id") String userId) {
        System.out.println("User id: " + userId);

        return ResponseEntity.ok("User event produced successfully!");
    }
}
