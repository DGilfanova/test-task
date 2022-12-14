package com.technokratos.adboard.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.exception.AdNotFoundException;
import com.technokratos.adboard.exception.DealNotFoundException;
import com.technokratos.adboard.exception.UserUnavailableOperationException;
import com.technokratos.adboard.model.Advertisement;
import com.technokratos.adboard.model.Deal;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.AdRepository;
import com.technokratos.adboard.repository.DealRepository;
import com.technokratos.adboard.service.DealService;
import com.technokratos.adboard.utils.mapper.DealMapper;
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
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final AdRepository adRepository;

    private final DealMapper dealMapper;

    @Override
    @Transactional
    public DealResponse createDeal(UUID adId, User authUser) {
        Advertisement advertisement = adRepository
            .findByIdAndIsActiveAndIsDeleted(adId, ACTIVE, NOT_DELETED)
            .orElseThrow(AdNotFoundException::new);

        boolean isDealAlreadyExist = dealRepository
            .findByUserIdAndAdvertisementId(authUser.getId(), advertisement.getId())
            .stream()
            .anyMatch((d) -> !d.getIsDeleted());
        if (isDealAlreadyExist) {
            throw new UserUnavailableOperationException("User already make deal");
        }

        if (authUser.getId().equals(advertisement.getUser().getId())) {
            throw new UserUnavailableOperationException("User can't make a deal with himself");
        }

        Deal newDeal = Deal.builder()
            .user(authUser)
            .advertisement(advertisement)
            .created(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        return dealMapper.toResponse(
            dealRepository.save(newDeal)
        );
    }

    @Override
    @Transactional
    public DealResponse makeDeal(UUID dealId, User authUser) {
        Deal deal = dealRepository.findByIdAndIsDeleted(dealId, NOT_DELETED)
            .orElseThrow(DealNotFoundException::new);

        if (!deal.getAdvertisement().getUser().equals(authUser)) {
            throw new UserUnavailableOperationException("User isn't ad's owner to make this deal");
        }

        Advertisement advertisement = deal.getAdvertisement();

        if (!advertisement.getIsActive() || advertisement.getIsDeleted()) {
            throw new UserUnavailableOperationException("AD don't available anymore.");
        }

        advertisement.setIsActive(false);
        adRepository.save(advertisement);

        deal.setIsCompleted(true);

        return dealMapper.toResponse(
            dealRepository.save(deal)
        );
    }
}
