package com.hongluomeng.service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.hongluomeng.common.Private;

public class PushService extends BaseService {

    public void send(String content, String object_id, String user_id) {
        ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(Private.JPUSH_MASTER_SECRET, Private.JPUSH_APP_KEY, null, config);

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(content)
                                .setBadge(1)
                                .addExtra("id", object_id)
                                .addExtra("type", "TOPIC")
                                .build())
                        .build())
                .setAudience(Audience.alias(user_id))
                .setMessage(Message.content(content))
                .build();

        try {
            PushResult result = jpushClient.sendPush(payload);

            System.out.println(result);
        } catch (APIConnectionException e) {
            System.out.println(e.toString());
        } catch (APIRequestException e) {
            System.out.println(e.toString());
        }
    }

}
