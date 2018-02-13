package com.zts1993.mipush.service.impl;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import com.zts1993.mipush.service.MiPushApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MiPushImpl implements MiPushApi {

    @Autowired
    MiPushProperties miPushProperties;

    public int simpleMsg(String title, String description, String messagePayload) throws Exception {
        Constants.useOfficial();

        Sender sender = new Sender(miPushProperties.getAppSecretKey());
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(miPushProperties.getMyPackageName())
                .passThrough(0)
                .notifyType(1)
//                 .notifyId((int)System.currentTimeMillis())
//                .extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_WEB)
//                .extra(Constants.EXTRA_PARAM_WEB_URI, "http://www.xiaomi.com")
                .extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_LAUNCHER_ACTIVITY)
                .build();
        Result result = sender.broadcastAll(message, 2);
        log.info("Server response: " + "MessageId: " + result.getMessageId()
                + " ErrorCode: " + result.getErrorCode().getFullDescription()
                + " Reason: " + result.getReason());

        return result.getErrorCode().getValue();
    }

}
