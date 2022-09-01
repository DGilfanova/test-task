package com.technokratos.adboard.utils.mapper;

import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.model.Deal;
import org.mapstruct.Mapper;

/**
 * @author d.gilfanova
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AdMapper.class})
public interface DealMapper {

    DealResponse toResponse(Deal deal);
}
