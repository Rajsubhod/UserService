package com.rajsubhod.userservice.controller;

import com.rajsubhod.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/")
    public ResponseEntity<?> getUser(){
        return new ResponseEntity<>(userService.getUserInfo(), HttpStatus.OK);
    }
}
