package com.technokratos.adboard.service;

import java.util.UUID;

import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.model.User;

/**
 * @author d.gilfanova
 */
public interface DealService {
    DealResponse createDeal(UUID adId, User authUser);
    DealResponse makeDeal(UUID dealId, User authUser);
}
