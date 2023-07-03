package com.shakya.userinfochange.service;

import com.shakya.userinfochange.model.Meta;
import com.shakya.userinfochange.model.Payload;
import com.shakya.userinfochange.model.UserData;
import com.shakya.userinfochange.model.UserInfoChangeEvent;
import org.apache.kafka.common.message.LeaderAndIsrRequestData;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UpdateUserInfoService {

    public static UserInfoChangeEvent updateUserInfo(HashMap<String, Object> userData) throws JSONException {

        JSONObject jsonObject = (JSONObject) userData.get("basic_details");
        UserData requestBody = (UserData) userData.get("user_data");
        long nowTimeMilli = new Date().getTime();

        Payload payload = new Payload();
        payload.setId(jsonObject.getString("id"));
        payload.setUserName(jsonObject.getString("login"));
        payload.setEmail(requestBody.getData().getEmail());
        payload.setFirstName(requestBody.getData().getFirstName());
        payload.setLastName(requestBody.getData().getLastName());
        payload.setTimeZoneId(requestBody.getData().getTimeZoneId());
        payload.setFollowers((List<String>) userData.get("followers"));
        payload.setRepos((List<String>) userData.get("repos"));

        Meta metaData = new Meta();
        metaData.setType("UserInfoChanged");
        metaData.setEventId(UUID.randomUUID().toString());
        metaData.setTraceId(UUID.randomUUID().toString());
        metaData.setCreatedAt(nowTimeMilli);
        metaData.setServiceId("user-service");

        UserInfoChangeEvent userInfoChangedEvent = new UserInfoChangeEvent(metaData, payload);

        return userInfoChangedEvent;

    }
}
