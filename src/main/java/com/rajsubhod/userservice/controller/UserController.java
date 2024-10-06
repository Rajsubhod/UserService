package com.rajsubhod.userservice.controller;

import com.rajsubhod.userservice.dto.UserInfoDto;
import com.rajsubhod.userservice.entity.UserInfo;
import com.rajsubhod.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/api")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    /**
     * <p>
     * This method is used to get all the users
     * </p>
     * Accessible by only admin
     * @return List of users
     */
    @GetMapping("/v1/")
    public ResponseEntity<?> getUser(){
        logger.info("Fetching all users");
        return new ResponseEntity<>(userService.getUserInfo(), HttpStatus.OK);
    }

    /**
     * <p>
     * This method is used to save the user
     * </p>
     * Accessible by only admin
     * </p>
     * @param userInfo
     * @return
     */

    @PostMapping("/v1/")
    public ResponseEntity<?> saveUser(@RequestBody UserInfo userInfo){
        logger.info("Saving user: {}", userInfo);
        userService.saveUserInfo(userInfo);
        return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);
    }

    /**
     * <p>
     * This method is used to save a list of users
     * </p>
     * Accessible by only admin
     * </p>
     * @param userInfos
     * @return
     */
    @PostMapping("/v1/list")
    public ResponseEntity<?> saveUsers(@RequestBody List<UserInfo> userInfos) {
        logger.info("Saving list of users: {}", userInfos.toString());
        userInfos.forEach(userService::saveUserInfo);
        return new ResponseEntity<>("Users saved successfully", HttpStatus.CREATED);
    }

    /**
     * <p>
     * This method is used to delete the user
     * </p>
     * Accessible by only admin
     * </p>
     * @param userId
     * @return
     */

    @DeleteMapping("/v1/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        logger.info("Deleting user with ID: {}", userId);
        userService.deleteUserInfo(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    /**
     * <p>
     * This method is used to get the user by userId
     * </p>
     * Accessible by admin for any users and user for self
     * </p>
     * @param userId
     * @param request
     * @return UserInfoDto
     */

    @GetMapping({"/v1/","/v1/{userId}"})
    public ResponseEntity<?> getUserById(@PathVariable(required = false) String userId, HttpServletRequest request){
        userId = request.getHeader("userId");
        if(userId == null || userId.isEmpty()){
            logger.warn("User ID is required but not provided");
            return new ResponseEntity<>("User Id is required", HttpStatus.BAD_REQUEST);
        }
        logger.info("Fetching user with ID: {}", userId);
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }

    /**
     * <p>
     * This method is used to update the user
     * </p>
     * Accessible by only admin
     * </p>
     * @param userId
     * @param userInfoDto
     * @param requestUserId
     * @return UserInfoDto
     */

    @PutMapping({"/vi/","/v1/{userId}"})
    public ResponseEntity<?> updateUser(@PathVariable(required = false) String userId, @RequestBody UserInfoDto userInfoDto, @RequestHeader("userId") String requestUserId){
        if(userId == null || userId.isEmpty()){
            userId = requestUserId;
        }
        if(userId == null || userId.isEmpty()){
            logger.warn("User ID is required but not provided");
            return new ResponseEntity<>("User Id is required", HttpStatus.BAD_REQUEST);
        }
        logger.info("Updating user with ID: {}", userId);
        userService.updateUserInfo(userId, userInfoDto);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
}
