package com.technokratos.adboard.utils.mapper;

import com.technokratos.adboard.dto.response.UserResponse;
import com.technokratos.adboard.model.User;
import org.mapstruct.Mapper;

/**
 * @author d.gilfanova
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
}
