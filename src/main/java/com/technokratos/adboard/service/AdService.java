package com.technokratos.adboard.service;

import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.dto.response.AdResponse;

/**
 * @author d.gilfanova
 */
public interface AdService {
    List<AdResponse> getAllAds();
    AdResponse getAdById(UUID adId);
}
