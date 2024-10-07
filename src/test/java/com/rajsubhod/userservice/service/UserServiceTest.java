package com.rajsubhod.userservice.service;

import com.rajsubhod.userservice.dto.UserInfoDto;
import com.rajsubhod.userservice.entity.UserInfo;
import com.rajsubhod.userservice.repository.UserInfoRepository;
import com.rajsubhod.userservice.utils.UserInfoToDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private UserInfoToDtoConverter userInfoToDtoConverter;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUserInfo() {
        UserInfo userInfo = new UserInfo();
        userService.saveUserInfo(userInfo);
        verify(userInfoRepository, times(1)).save(userInfo);
    }

    @Test
    void testUpdateUserInfo() {
        String userId = "1";
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setFirstName("John");
        userInfoDto.setLastName("Doe");
        userInfoDto.setPhoneNumber("1234567890");

        UserInfo userInfo = new UserInfo();
        when(userInfoRepository.findByUserId(userId)).thenReturn(Optional.of(userInfo));

        userService.updateUserInfo(userId, userInfoDto);

        assertEquals("John", userInfo.getFirstName());
        assertEquals("Doe", userInfo.getLastName());
        assertEquals(1234567890L, userInfo.getPhoneNumber());
        verify(userInfoRepository, times(1)).save(userInfo);
    }

    @Test
    void testGetUserInfo() {
        String userId = "1";
        UserInfo userInfo = new UserInfo();
        UserInfoDto userInfoDto = new UserInfoDto();

        when(userInfoRepository.findByUserId(userId)).thenReturn(Optional.of(userInfo));
        when(userInfoToDtoConverter.convertToDto(userInfo)).thenReturn(userInfoDto);

        UserInfoDto result = userService.getUserInfo(userId);

        assertNotNull(result);
        verify(userInfoRepository, times(1)).findByUserId(userId);
        verify(userInfoToDtoConverter, times(1)).convertToDto(userInfo);
    }

    @Test
    void testGetUserInfo_UserNotFound() {
        String userId = "1";
        when(userInfoRepository.findByUserId(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserInfo(userId));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetAllUserInfo() {
        userService.getUserInfo();
        verify(userInfoRepository, times(1)).findAll();
    }

    @Test
    void testDeleteUserInfo() {
        String userId = "1";
        userService.deleteUserInfo(userId);
        verify(userInfoRepository, times(1)).deleteById(userId);
    }
}