package com.rajsubhod.userservice.message;

import com.rajsubhod.userservice.entity.UserInfo;
import com.rajsubhod.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConsumer{

    private static final Logger logger = LoggerFactory.getLogger(UserInfoConsumer.class);
    private final UserService userService;

    public UserInfoConsumer(UserService userService) {
        logger.info("UserInfoConsumer Started");
        this.userService = userService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveEventFromQueue(UserInfo userInfo) {
        try{
            logger.info("Received UserInfo: {}", userInfo);
            userService.saveUserInfo(userInfo);
        } catch (RuntimeException e) {
            logger.error("Error while processing UserInfo: {}", userInfo);
            throw new RuntimeException(e);
        }
    }
}
