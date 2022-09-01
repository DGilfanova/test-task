package com.technokratos.adboard.utils.mapper;

import java.util.List;

import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.model.Advertisement;
import org.mapstruct.Mapper;

/**
 * @author d.gilfanova
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, FileMapper.class})
public interface AdMapper {

    AdResponse toResponse(Advertisement advertisement);
    List<AdResponse> toListResponse(List<Advertisement> advertisements);
}
