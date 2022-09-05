package com.technokratos.adboard.constant;

import java.util.UUID;

import com.technokratos.adboard.dto.enums.Role;
import com.technokratos.adboard.model.User;

/**
 * @author d.gilfanova
 */
public class TestConstant {

    public final static String TEST_AD_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d30";
    public final static String TEST_USER_AD_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d31";
    public final static String DEAL_AD_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d32";
    public final static String NON_EXIST_AD_ID = "f94e6c06-2ad6-4587-9860-1f65ed307111";
    public final static String SPECIAL_AD_TITLE = "special ad";
    public final static String TIMESTAMP = "2022-09-04T04:00:33.397Z";

    public final static String TEST_USER_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d40";
    public final static User TEST_USER = User.builder()
        .id(UUID.fromString(TEST_USER_ID))
        .email("user@mail.ru")
        .password("Abc123++")
        .role(Role.USER)
        .build();

    public final static String NEW_USER_EMAIL = "new.user@mail.ru";
    public final static String REPEAT_USER_EMAIL = "repeat.user@mail.ru";
}
