package com.technokratos.adboard.service.impl;

import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.exception.AdNotFoundException;
import com.technokratos.adboard.repository.AdRepository;
import com.technokratos.adboard.service.AdService;
import com.technokratos.adboard.utils.mapper.AdMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author d.gilfanova
 */
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;

    private final AdMapper adMapper;

    @Override
    public List<AdResponse> getAllAds() {
        return adMapper.toListResponse(
            adRepository.findAll()
        );
    }

    @Override
    public AdResponse getAdById(UUID adId) {
        return adMapper.toResponse(
            adRepository.findById(adId).orElseThrow(AdNotFoundException::new)
        );
    }
}
