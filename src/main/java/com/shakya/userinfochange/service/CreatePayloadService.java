package com.shakya.userinfochange.service;

import com.shakya.userinfochange.model.Payload;
import com.shakya.userinfochange.model.UserData;
import com.shakya.userinfochange.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CreatePayloadService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public void createAndSavePayload(Map<String, Object> userData) throws JSONException {

        JSONObject jsonObject = (JSONObject) userData.get("basic_details");
        UserData requestBody = (UserData) userData.get("user_data");

        Payload payload = new Payload();
        payload.setId(Integer.parseInt(jsonObject.getString("id")));
        payload.setUserName(jsonObject.getString("login"));
        payload.setEmail(requestBody.getData().getEmail());
        payload.setFirstName(requestBody.getData().getFirstName());
        payload.setLastName(requestBody.getData().getLastName());
        payload.setTimeZoneId(requestBody.getData().getTimeZoneId());
        payload.setFollowers((List<String>) userData.get("followers"));
        payload.setRepos((List<String>) userData.get("repos"));

        userInfoRepository.save(payload);

    }
}
