package com.msmgym.application.beans;

import java.util.HashMap;

/**
 * Created by akash.mercer on 16-Jun-16.
 */
public class NotificationBean {

    private String notificationId;

    private HashMap<String, String> dataMap = new HashMap<>();

    public NotificationBean(){

    }

    public HashMap<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
