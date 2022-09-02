package com.technokratos.adboard.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.dto.request.CreateAdRequest;
import com.technokratos.adboard.dto.request.UpdateAdStatusRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.exception.AdNotFoundException;
import com.technokratos.adboard.exception.UserNotFoundException;
import com.technokratos.adboard.model.Advertisement;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.AdRepository;
import com.technokratos.adboard.repository.UserRepository;
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
    private final UserRepository userRepository;

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

    @Override
    public AdResponse createAd(CreateAdRequest newAd, User authUser) {
        //while we don't have security
        User user = userRepository.findById(newAd.getUserId())
            .orElseThrow(UserNotFoundException::new);
        //check role (after adding security)

        //save photos

        Advertisement advertisement = Advertisement.builder()
            .title(newAd.getTitle())
            .content(newAd.getContent())
            .user(user)
            .isActive(newAd.getIsActive())
            .created(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        return adMapper.toResponse(
            adRepository.save(advertisement)
        );
    }

    @Override
    public AdResponse updateActiveStatus(UUID adId, UpdateAdStatusRequest adStatusRequest) {
        //check user access (after adding security)

        Advertisement advertisement = adRepository.findById(adId)
            .orElseThrow(AdNotFoundException::new);

        advertisement.setIsActive(adStatusRequest.getIsActive());

        return adMapper.toResponse(
            adRepository.save(advertisement)
        );
    }
}
