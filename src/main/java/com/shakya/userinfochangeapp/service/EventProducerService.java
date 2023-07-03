package com.shakya.userinfochangeapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shakya.userinfochangeapp.model.Meta;
import com.shakya.userinfochangeapp.model.Payload;
import com.shakya.userinfochangeapp.model.UserInfoChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EventProducerService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public UserInfoChangeEvent createProducerEvent(Payload payload) {
        Meta metaData = new Meta();
        metaData.setType("UserInfoChanged");
        metaData.setEventId(UUID.randomUUID().toString());
        metaData.setCreatedAt(new Date().getTime());
        metaData.setTraceId(UUID.randomUUID().toString());
        metaData.setServiceId("user-service");

        UserInfoChangeEvent userInfoChangeEvent = new UserInfoChangeEvent(metaData, payload);

        return userInfoChangeEvent;
    }

    public void sendEvent(UserInfoChangeEvent userInfoChangeEvent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userInfoChangeEvent);
        kafkaTemplate.send("users", json);
    }
}
