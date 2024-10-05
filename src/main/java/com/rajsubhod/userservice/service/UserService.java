package com.rajsubhod.userservice.service;

import com.rajsubhod.userservice.dto.UserInfoDto;
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


    public void saveUserInfo(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    public void updateUserInfo(String userId, UserInfoDto userInfoDto) {
        Optional<UserInfo> userDetails = userInfoRepository.findByUserId(userId);
        if(userDetails.isPresent()){
            UserInfo updatedUserInfo = userDetails.get();
            updatedUserInfo.setFirstName(userInfoDto.getFirstName());
            updatedUserInfo.setLastName(userInfoDto.getLastName());
            updatedUserInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
            userInfoRepository.save(updatedUserInfo);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private UserInfoDto convertToDto(UserInfo userInfo) {
        return new UserInfoDto(
                userInfo.getUsername(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getEmail(),
                userInfo.getPhoneNumber()
        );
    }

    public UserInfoDto getUserInfo(String userId) {

        return userInfoRepository.findByUserId(userId).map(this::convertToDto).orElseThrow(()-> new RuntimeException("User not found"));
    }

    public List<UserInfo> getUserInfo() {
        return userInfoRepository.findAll();
    }

}
