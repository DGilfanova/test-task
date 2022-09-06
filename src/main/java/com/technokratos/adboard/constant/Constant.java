package com.technokratos.adboard.constant;

import javax.swing.plaf.PanelUI;

/**
 * @author d.gilfanova
 */
public class Constant {

    public final static long MINIO_DEFAULT_OBJECT_SIZE = -1;
    public final static long MINIO_DEFAULT_PART_SIZE = 10485760;
    public final static String MINIO_BUCKET_NAME = "adboardfile";

    public final static Boolean NOT_DELETED = Boolean.FALSE;
    public final static Boolean ACTIVE = Boolean.TRUE;

    public final static String BEARER = "Bearer ";
    public final static String ROLE = "ROLE";

    public final static int MESSAGE_PART_START_INDEX = 0;
    public final static int MESSAGE_PART_END_INDEX = 10;
    public final static String  MESSAGE_PART_END = "...";

    public static final String WEBSOCKET_ENDPOINT = "/ws";
    public static final String MESSAGE_CONTROLLER_ENDPOINT = "/api/v1/app";
    public static final String SEND_MESSAGE_ENDPOINT = MESSAGE_CONTROLLER_ENDPOINT + "/chat";
    public static final String DESTINATION_PREFIX = "/user";
    public static final String MESSAGE_DESTINATION = "/messages/queue";
}
