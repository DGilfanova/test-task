package com.technokratos.adboard.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.technokratos.adboard.dto.request.CreateUserRequest;
import com.technokratos.adboard.dto.response.UserResponse;
import com.technokratos.adboard.exception.UserUnavailableOperationException;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.UserRepository;
import com.technokratos.adboard.service.UserService;
import com.technokratos.adboard.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.technokratos.adboard.constant.Constant.NOT_DELETED;

/**
 * @author d.gilfanova
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest newUserInfo) {
        if (userRepository.findByEmailAndIsDeleted(newUserInfo.getEmail().toLowerCase(), NOT_DELETED)
            .isPresent()) {
            log.info("User with email {} already exist", newUserInfo.getEmail());

            throw new UserUnavailableOperationException("User exist with email "
                                                        + newUserInfo.getEmail());
        }

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
