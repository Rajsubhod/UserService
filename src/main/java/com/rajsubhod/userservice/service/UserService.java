package com.rajsubhod.userservice.service;

import com.rajsubhod.userservice.dto.UserInfoDto;
import com.rajsubhod.userservice.entity.UserInfo;
import com.rajsubhod.userservice.repository.UserInfoRepository;
import com.rajsubhod.userservice.utils.UserInfoToDtoConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserInfoRepository userInfoRepository;
    private final UserInfoToDtoConverter userInfoToDtoConverter;


    /**
     * Save user info
     * @param userInfo
     */
    public void saveUserInfo(UserInfo userInfo) {
        logger.info("Saving user info");
        userInfoRepository.save(userInfo);
    }

    /**
     * Update user info
     * @param userId
     * @param userInfoDto
     */
    public void updateUserInfo(String userId, UserInfoDto userInfoDto) {
        logger.info("Updating user info");
        Optional<UserInfo> userDetails = userInfoRepository.findByUserId(userId);
        if(userDetails.isPresent()){
            UserInfo updatedUserInfo = userDetails.get();
            updatedUserInfo.setFirstName(userInfoDto.getFirstName());
            updatedUserInfo.setLastName(userInfoDto.getLastName());
            updatedUserInfo.setPhoneNumber(Long.valueOf(userInfoDto.getPhoneNumber()));
            userInfoRepository.save(updatedUserInfo);
        } else {
            logger.error("User not found");
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Get user info
     * @param userId
     * @return user info
     */
    public UserInfoDto getUserInfo(String userId) {
        logger.info("Getting user info");
        return userInfoRepository.findByUserId(userId).map(userInfoToDtoConverter::convertToDto).orElseThrow(()->{
            logger.error("User not found");
            return new RuntimeException("User not found");
        });
    }

    /**
     * Get all user info
     * @return list of user info
     */
    public List<UserInfo> getUserInfo() {
        logger.info("Getting all user info");
        return userInfoRepository.findAll();
    }

    /**
     * Delete user info
     * @param userId
     */
    public void deleteUserInfo(String userId) {
        logger.info("Deleting user info");
        userInfoRepository.deleteById(userId);
    }
}
