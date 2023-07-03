package com.shakya.userinfochange.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoChangeEvent {
    @Autowired
    private Meta meta;
    @Autowired
    private Payload payload;

    public UserInfoChangeEvent(Meta meta, Payload payload) {
        this.meta = meta;
        this.payload = payload;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
