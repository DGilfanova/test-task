package com.technokratos.adboard.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.exception.AdNotFoundException;
import com.technokratos.adboard.exception.DealNotFoundException;
import com.technokratos.adboard.exception.UserNotFoundException;
import com.technokratos.adboard.exception.UserUnavailableOperationException;
import com.technokratos.adboard.model.Advertisement;
import com.technokratos.adboard.model.Deal;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.AdRepository;
import com.technokratos.adboard.repository.DealRepository;
import com.technokratos.adboard.repository.UserRepository;
import com.technokratos.adboard.service.DealService;
import com.technokratos.adboard.utils.mapper.DealMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author d.gilfanova
 */
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;

    private final DealMapper dealMapper;

    @Transactional
    @Override
    public DealResponse createDeal(UUID adId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Advertisement advertisement = adRepository.findById(adId)
            .orElseThrow(AdNotFoundException::new);

        if (user.getId().equals(advertisement.getUser().getId())) {
            throw new UserUnavailableOperationException("User can't make a deal with himself");
        }

        Deal newDeal = Deal.builder()
            .user(user)
            .advertisement(advertisement)
            .created(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        return dealMapper.toResponse(
            dealRepository.save(newDeal)
        );
    }

    @Transactional
    @Override
    public DealResponse makeDeal(UUID dealId) {
        //check access

        Deal deal = dealRepository.findById(dealId).orElseThrow(DealNotFoundException::new);

        deal.setIsCompleted(true);

        Advertisement advertisement = deal.getAdvertisement();
        advertisement.setIsActive(false);
        adRepository.save(advertisement);

        return dealMapper.toResponse(
            dealRepository.save(deal)
        );
    }
}
