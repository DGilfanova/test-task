package com.technokratos.adboard.service;

import java.util.UUID;

import com.technokratos.adboard.dto.response.DealResponse;

/**
 * @author d.gilfanova
 */
public interface DealService {
    DealResponse createDeal(UUID adId, UUID userId);
    DealResponse makeDeal(UUID dealId);
}
