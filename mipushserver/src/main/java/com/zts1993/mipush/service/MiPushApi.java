package com.zts1993.mipush.service;

public interface MiPushApi {

    int simpleMsg(String title, String description, String messagePayload) throws Exception;

}
