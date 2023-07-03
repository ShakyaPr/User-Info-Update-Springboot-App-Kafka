package com.shakya.userinfochange.controller;

import com.shakya.userinfochange.model.UserData;
import com.shakya.userinfochange.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestControllerForService {

    @Autowired
    private GitHubService gitHubService;

    @PutMapping(value = "/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> updateUserInfo(@PathVariable("user_id") String userId, @RequestBody UserData userData) throws JSONException {
        System.out.println("User email " + userData.getData().getEmail());
        JSONObject jsonObject = gitHubService.getUserDetails(userId);
        System.out.println("Fetched data: " + jsonObject);

        return ResponseEntity.ok(jsonObject);
    }

    @PostMapping("/produce/{user_id}")
    public ResponseEntity<String> produceUserEvent(@PathVariable("user_id") String userId) {
        System.out.println("User id: " + userId);

        return ResponseEntity.ok("User event produced successfully!");
    }
}
