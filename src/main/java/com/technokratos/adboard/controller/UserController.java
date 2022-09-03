package com.technokratos.adboard.controller;

import java.util.UUID;

import com.technokratos.adboard.api.UserApi;
import com.technokratos.adboard.dto.request.CreateAdRequest;
import com.technokratos.adboard.dto.request.UpdateAdStatusRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.security.details.UserDetailsImpl;
import com.technokratos.adboard.service.AdService;
import com.technokratos.adboard.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author d.gilfanova
 */
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi<UserDetailsImpl> {

    private final AdService adService;
    private final DealService dealService;

    @Override
    public AdResponse createAd(CreateAdRequest newAd,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adService.createAd(newAd, userDetails.getUser());
    }

    @Override
    public AdResponse updateAdStatus(UUID adId, UpdateAdStatusRequest adStatusRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adService.updateActiveStatus(adId, adStatusRequest, userDetails.getUser());
    }

    @Override
    public DealResponse makeDeal(UUID dealId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return dealService.makeDeal(dealId, userDetails.getUser());
    }
}
