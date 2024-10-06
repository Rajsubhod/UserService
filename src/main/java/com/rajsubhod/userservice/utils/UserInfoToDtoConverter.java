package com.rajsubhod.userservice.utils;

import com.rajsubhod.userservice.dto.UserInfoDto;
import com.rajsubhod.userservice.entity.UserInfo;
import org.springframework.stereotype.Component;


@Component
public class UserInfoToDtoConverter {
    public UserInfoDto convertToDto(UserInfo userInfo) {
        return new UserInfoDto(
                userInfo.getUsername(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getEmail(),
                userInfo.getPhoneNumber().toString()
        );
    }
}
