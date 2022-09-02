package com.technokratos.adboard.service;

import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.dto.request.CreateAdRequest;
import com.technokratos.adboard.dto.request.UpdateAdStatusRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.model.User;

/**
 * @author d.gilfanova
 */
public interface AdService {
    List<AdResponse> getAllAds();
    AdResponse getAdById(UUID adId);
    AdResponse createAd(CreateAdRequest newAd, User authUser);
    AdResponse updateActiveStatus(UUID adId, UpdateAdStatusRequest adStatusRequest);
}
