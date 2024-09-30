package com.rajsubhod.userservice.service;

import com.rajsubhod.userservice.entity.UserInfo;
import com.rajsubhod.userservice.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserInfoRepository userInfoRepository;


    public void saveOrUpdateUserInfo(UserInfo userInfo) {
        Optional<UserInfo> userDetails = userInfoRepository.findByUserId(userInfo.getUserId());
        if(userDetails.isPresent()){
            UserInfo updatedUserInfo = userDetails.get();
            updatedUserInfo.setFirstName(userInfo.getFirstName());
            updatedUserInfo.setLastName(userInfo.getLastName());
            updatedUserInfo.setPhoneNumber(userInfo.getPhoneNumber());
            updatedUserInfo.setPassword(userInfo.getPassword());
            userInfoRepository.save(updatedUserInfo);
        } else {
            userInfoRepository.save(userInfo);
        }
    }

    public UserInfo getUserInfo(String userId) {
        return userInfoRepository.findByUserId(userId).orElseThrow(()->
                new RuntimeException("User not found with userId: "+userId));
    }

    public List<UserInfo> getUserInfo() {
        return userInfoRepository.findAll();
    }
}
