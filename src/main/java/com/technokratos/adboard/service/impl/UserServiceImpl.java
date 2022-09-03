package com.technokratos.adboard.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.technokratos.adboard.dto.request.CreateUserRequest;
import com.technokratos.adboard.dto.response.UserResponse;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.UserRepository;
import com.technokratos.adboard.service.UserService;
import com.technokratos.adboard.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author d.gilfanova
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest newUserInfo) {
        User newUser = User.builder()
            .email(newUserInfo.getEmail().toLowerCase())
            .password(
                passwordEncoder.encode(newUserInfo.getPassword()))
            .role(newUserInfo.getRole())
            .created(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        return userMapper.toResponse(
            userRepository.save(newUser)
        );
    }
}
