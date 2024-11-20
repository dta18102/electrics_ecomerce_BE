package com.example.medimateserver.service;


import com.example.medimateserver.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<UserDto> findAll();
    List<UserDto> findWithPageAndSize(int page, int size);
    UserDto findById(Integer id);
    UserDto findByPhone(String phone);
    UserDto findByEmail(String email);
    UserDto save(UserDto UserDto);
    UserDto update(Integer id, UserDto UserDto);
    void deleteById(Integer id);
}