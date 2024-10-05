package com.rajsubhod.userservice.controller;

import com.rajsubhod.userservice.dto.UserInfoDto;
import com.rajsubhod.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/")
    public ResponseEntity<?> getUser(){
        return new ResponseEntity<>(userService.getUserInfo(), HttpStatus.OK);
    }

    @GetMapping("/v1/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }

    @PutMapping("/v1/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserInfoDto userInfoDto){
        userService.updateUserInfo(userId, userInfoDto);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
}
