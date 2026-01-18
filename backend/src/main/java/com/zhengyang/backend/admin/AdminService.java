package com.zhengyang.backend.admin;

import com.zhengyang.backend.user.dto.UserResponse;
import com.zhengyang.backend.user.UserEntity;
import com.zhengyang.backend.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    
    @Autowired
    private UserRepository userRepository;
    
    // get all users
    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        
        return users.stream()
                .map(user -> new UserResponse(
                    user.getUsername(),
                    user.getEmail(),
                    user.isAdmin()
                ))
                .collect(Collectors.toList());
    }
}
