package com.shakya.userinfochange.service;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class GitHubService {

    private final String baseUrl = "https://api.github.com";

    public JSONObject getUserDetails(String userId) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/users/" + getUserNameFromUserId(userId);
        String userInfo = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = new JSONObject(userInfo);
        return jsonObject;
    }

    public String getUserNameFromUserId(String UserId) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/users?since=" + Integer.toString(Integer.parseInt(UserId) - 1);
        String userList = restTemplate.getForObject(url, String.class);
        JSONArray jsonArray = new JSONArray(userList);
        String userName = jsonArray.getJSONObject(0).getString("login");
        return userName;

    }
}
