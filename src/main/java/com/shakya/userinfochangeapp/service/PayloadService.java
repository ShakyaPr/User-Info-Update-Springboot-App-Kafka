package com.shakya.userinfochangeapp.service;

import com.shakya.userinfochangeapp.constants.UserInfoConstants;
import com.shakya.userinfochangeapp.model.Payload;
import com.shakya.userinfochangeapp.model.UserData;
import com.shakya.userinfochangeapp.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PayloadService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public Payload createPayload(Map<String, Object> userData) throws JSONException {

        JSONObject jsonObject = (JSONObject) userData.get(UserInfoConstants.BASIC_USER_DETAILS);
        UserData requestBody = (UserData) userData.get(UserInfoConstants.REQUEST_BODY);

        Payload payload = new Payload();
        payload.setId(Integer.parseInt(jsonObject.getString("id")));
        payload.setUserName(jsonObject.getString("login"));
        payload.setEmail(requestBody.getData().getEmail());
        payload.setFirstName(requestBody.getData().getFirstName());
        payload.setLastName(requestBody.getData().getLastName());
        payload.setTimeZoneId(requestBody.getData().getTimeZoneId());
        payload.setFollowers((List<String>) userData.get(UserInfoConstants.FOLLOWERS));
        payload.setRepos((List<String>) userData.get(UserInfoConstants.REPOSITORIES));

        return payload;
    }

    public void savePayload(Payload payload) {
        userInfoRepository.save(payload);
    }

    public Payload getPayloadById(int id) {
        return userInfoRepository.findById(id).orElse(null);
    }
}
