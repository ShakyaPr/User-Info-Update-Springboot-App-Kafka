package com.shakya.userinfochange.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getFollowers(String userId) throws JSONException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/users/" + getUserNameFromUserId(userId) + "/followers";
        String followers = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(followers);
        List<String> followersList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            followersList.add(node.get("login").asText());
        }
        return followersList;
    }

    public List<String> getRepos(String userId) throws JSONException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/users/" + getUserNameFromUserId(userId) + "/repos";
        String repos = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(repos);
        List<String> repoList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            repoList.add(node.get("full_name").asText());
        }
        return repoList;
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
