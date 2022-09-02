package com.technokratos.adboard.controller;

import java.util.UUID;

import com.technokratos.adboard.api.UserApi;
import com.technokratos.adboard.dto.request.CreateAdRequest;
import com.technokratos.adboard.dto.request.CreateUserRequest;
import com.technokratos.adboard.dto.request.UpdateAdStatusRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.dto.response.UserResponse;
import com.technokratos.adboard.service.AdService;
import com.technokratos.adboard.service.DealService;
import com.technokratos.adboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author d.gilfanova
 */
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;
    private final AdService adService;
    private final DealService dealService;

    @Override
    public UserResponse createUser(CreateUserRequest newUser) {
        return userService.createUser(newUser);
    }

    @Override
    public AdResponse createAd(CreateAdRequest newAd) {
        return adService.createAd(newAd, null);
    }

    @Override
    public AdResponse updateAdStatus(UUID adId, UpdateAdStatusRequest adStatusRequest) {
        return adService.updateActiveStatus(adId, adStatusRequest);
    }

    @Override
    public DealResponse makeDeal(UUID dealId) {
        return dealService.makeDeal(dealId);
    }
}
