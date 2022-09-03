package com.technokratos.adboard.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.dto.enums.FileType;
import com.technokratos.adboard.dto.request.CreateAdRequest;
import com.technokratos.adboard.dto.request.FilterAdRequest;
import com.technokratos.adboard.dto.request.UpdateAdStatusRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.exception.AdNotFoundException;
import com.technokratos.adboard.exception.UserNotFoundException;
import com.technokratos.adboard.model.Advertisement;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.AdRepository;
import com.technokratos.adboard.repository.UserRepository;
import com.technokratos.adboard.service.AdService;
import com.technokratos.adboard.service.FileService;
import com.technokratos.adboard.specification.AdSpecification;
import com.technokratos.adboard.utils.mapper.AdMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.technokratos.adboard.constant.Constant.ACTIVE;
import static com.technokratos.adboard.constant.Constant.NOT_DELETED;

/**
 * @author d.gilfanova
 */
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final FileService fileService;

    private final AdRepository adRepository;
    private final UserRepository userRepository;

    private final AdMapper adMapper;

    private final AdSpecification adSpecification;

    @Override
    public List<AdResponse> getFilteredAds(FilterAdRequest filterAdRequest) {
        return adMapper.toListResponse(
            adRepository.findAll(adSpecification.getApartments(filterAdRequest))
        );
    }

    @Override
    public AdResponse getAdById(UUID adId) {
        return adMapper.toResponse(
            adRepository.findByIdAndIsActiveAndIsDeleted(adId, ACTIVE, NOT_DELETED)
                .orElseThrow(AdNotFoundException::new)
        );
    }

    @Transactional
    @Override
    public AdResponse createAd(CreateAdRequest newAd, User authUser) {
        //while we don't have security
        User user = userRepository.findByIdAndIsDeleted(newAd.getUserId(), NOT_DELETED)
            .orElseThrow(UserNotFoundException::new);
        //check role (after adding security)

        Advertisement advertisement = Advertisement.builder()
            .title(newAd.getTitle())
            .content(newAd.getContent())
            .user(user)
            .photos(
                fileService.uploadFiles(newAd.getPhotos(), FileType.PHOTO)
            )
            .isActive(newAd.getIsActive())
            .created(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        return adMapper.toResponse(
            adRepository.save(advertisement)
        );
    }

    @Transactional
    @Override
    public AdResponse updateActiveStatus(UUID adId, UpdateAdStatusRequest adStatusRequest) {
        //check user access (after adding security)

        Advertisement advertisement = adRepository.findByIdAndIsDeleted(adId, NOT_DELETED)
            .orElseThrow(AdNotFoundException::new);

        advertisement.setIsActive(adStatusRequest.getIsActive());

        return adMapper.toResponse(
            adRepository.save(advertisement)
        );
    }
}
