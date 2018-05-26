package com.msmgym.application.utils;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.msmgym.application.beans.NotificationBean;

import java.io.IOException;
import java.util.List;

public class NotificationManager {

    public static final int RETRY_COUNT = 3;

    public static void sendNotification(List<NotificationBean> notificationBeanList){
        for (int i = 0; i < notificationBeanList.size(); i++) {
            NotificationBean notificationBean = notificationBeanList.get(i);
            Sender sender = new Sender(Constants.GOOGLE_API_KEY);
            Message.Builder builder = new Message.Builder();
            builder.setData(notificationBean.getDataMap());
            Message message = builder.build();
            try {
                sender.send(message, notificationBean.getNotificationId(), RETRY_COUNT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
