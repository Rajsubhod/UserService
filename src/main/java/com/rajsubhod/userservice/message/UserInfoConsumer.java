package com.rajsubhod.userservice.message;

import com.rajsubhod.userservice.entity.UserInfo;
import com.rajsubhod.userservice.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConsumer{

    private final UserService userService;

    public UserInfoConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveEventFromQueue(UserInfo userInfo) {
        try{
            userService.saveUserInfo(userInfo);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
