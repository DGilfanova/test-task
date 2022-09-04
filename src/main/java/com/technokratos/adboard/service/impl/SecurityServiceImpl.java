package com.technokratos.adboard.service.impl;

import java.util.Optional;

import com.technokratos.adboard.dto.request.SignInRequest;
import com.technokratos.adboard.dto.response.JwtResponse;
import com.technokratos.adboard.exception.security.UserAuthenticationException;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.UserRepository;
import com.technokratos.adboard.service.JwtTokenService;
import com.technokratos.adboard.service.SecurityService;
import com.technokratos.adboard.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.technokratos.adboard.constant.Constant.NOT_DELETED;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityServiceImpl implements SecurityService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final JwtTokenService jwtTokenService;

    @Transactional
    @Override
    public JwtResponse login(SignInRequest signInRequest) {
        Optional<User> optionalUser =  userRepository.findByEmailAndIsDeleted(
            signInRequest.getEmail(), NOT_DELETED)
                .filter(u -> passwordEncoder.matches(signInRequest.getPassword(), u.getPassword()));

        if (optionalUser.isEmpty()) {
            log.info("Failed to log in user with username {}" + signInRequest.getEmail());

            throw new UserAuthenticationException("Failed to log in user with username: "
                                                  + signInRequest.getEmail());
        }

        return jwtTokenService.generateTokenPair(
            userMapper.toResponse(optionalUser.get()));
    }
}
