package com.technokratos.adboard.constant;

import java.util.UUID;

import com.technokratos.adboard.dto.enums.Role;
import com.technokratos.adboard.model.User;

/**
 * @author d.gilfanova
 */
public class TestConstant {

    public final static String FIRST_AD_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d31";
    public final static String SECOND_AD_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d32";
    public final static String THIRD_AD_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d33";
    public final static String NON_EXIST_AD_ID = "f94e6c06-2ad6-4587-9860-1f65ed307111";

    public final static String FIRST_AD_TITLE = "ad1";

    public final static String NEW_AD_TITLE = "New ad";
    public final static String NEW_AD_CONTENT = "New ad content";

    public final static String FIRST_DEAL_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d11";
    public final static String SECOND_DEAL_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d12";

    public final static String FIRST_CHAT_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d51";
    public final static String SECOND_CHAT_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d52";

    public final static String NEW_USER_EMAIL = "new.user@mail.ru";
    public final static String FIRST_USER_EMAIL = "user1@mail.ru";
    public final static String SECOND_USER_EMAIL = "user2@mail.ru";
    public final static String FIRST_USER_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d41";
    public final static String SECOND_USER_ID = "f94e6c06-2ad6-4587-9860-1f65ed307d42";
    public final static User FIRST_USER = User.builder()
        .id(UUID.fromString(FIRST_USER_ID))
        .email(FIRST_USER_EMAIL)
        .password("Abc123++")
        .role(Role.USER)
        .build();

    public final static Boolean ACTIVE = true;
    public final static Boolean NOT_ACTIVE = false;
    public final static String TIMESTAMP = "2022-09-04T04:00:33.397Z";
    public final static Integer TIMEOUT = 10;

    public final static String INVALID_REFRESH_TOKEN = "f94e6c06-2ad6-4587-9860-1f65ed307d12";
    public final static String EXPIRED_REFRESH_TOKEN = "f94e6c06-2ad6-4587-9860-1f65ed307d11";
}
