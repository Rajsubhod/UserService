package com.rajsubhod.userservice.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajsubhod.userservice.entity.UserInfo;
import org.apache.kafka.common.serialization.Deserializer;


import java.util.Map;

public class UserInfoDeserializer implements Deserializer<UserInfo> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public UserInfo deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        UserInfo userInfo = null;
        try {
            userInfo = mapper.readValue(bytes, UserInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("Error when deserializing UserInfo from byte[]");
        }
        return userInfo;
    }


    @Override
    public void close() {
        Deserializer.super.close();
    }
}
