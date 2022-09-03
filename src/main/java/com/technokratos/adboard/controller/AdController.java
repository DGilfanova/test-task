package com.technokratos.adboard.controller;

import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.api.AdApi;
import com.technokratos.adboard.dto.request.CreateDealRequest;
import com.technokratos.adboard.dto.request.FilterAdRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.service.AdService;
import com.technokratos.adboard.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author d.gilfanova
 */
@RequiredArgsConstructor
@RestController
public class AdController implements AdApi {

    private final AdService adService;
    private final DealService dealService;

    @Override
    public List<AdResponse> getFilteredAds(FilterAdRequest filterAdRequest) {
        return adService.getFilteredAds(filterAdRequest);
    }

    @Override
    public AdResponse getAd(UUID adId) {
        return adService.getAdById(adId);
    }

    @Override
    public DealResponse createDeal(UUID adId, CreateDealRequest newDeal) {
        return dealService.createDeal(adId, newDeal.getUserId());
    }
}
