package com.msmgym.application.services;

import com.google.gson.Gson;
import com.msmgym.application.beans.Email;
import com.msmgym.application.beans.NotificationBean;
import com.msmgym.application.utils.Constants;
import com.msmgym.application.utils.Mail;
import com.msmgym.application.utils.NotificationManager;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class EmailService {

    @Autowired
    private Mail mail;

    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    NotificationManager notificationManager = new NotificationManager();

    public void sendEmail(Email email){
        mail.sendMail(new String[]{email.getEmail()}, Constants.FROM_EMAIL, Constants.REPLY_TO_EMAIL, email.getSubject(), email.getText(), "", "");
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void gym_cron() {
        String today = date.format(Calendar.getInstance().getTime());
        List<NotificationBean> notificationBeanList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> httpMessageConverterList = new ArrayList<>();
        httpMessageConverterList.add(new FormHttpMessageConverter());
        httpMessageConverterList.add(new StringHttpMessageConverter());
        httpMessageConverterList.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(httpMessageConverterList);
        Gson gson = new Gson();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost/index/get_gym_owner_reminder_data", map, String.class);
        if (HttpStatus.OK == response.getStatusCode()) {
            JSONArray jsonArray = new JSONArray(response.getBody());
            for (int i = 0; i < jsonArray.length(); i++) {
                String notificationId = jsonArray.getJSONObject(i).getString("notification_id");
                int count = jsonArray.getJSONObject(i).getInt("count");
                if(count > 0) {
                    NotificationBean notificationBean = new NotificationBean();
                    notificationBean.setNotificationId(notificationId);
                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("content", count + " members have their fees pending today");
                    dataMap.put("title", "MySportsMeet Gym");
                    notificationBean.setDataMap(dataMap);
                    notificationBeanList.add(notificationBean);
                }
            }
            notificationManager.sendNotification(notificationBeanList);
        } else {
            //Notify admin
        }
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void academy_cron() {
        String today = date.format(Calendar.getInstance().getTime());
        List<NotificationBean> notificationBeanList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> httpMessageConverterList = new ArrayList<>();
        httpMessageConverterList.add(new FormHttpMessageConverter());
        httpMessageConverterList.add(new StringHttpMessageConverter());
        httpMessageConverterList.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(httpMessageConverterList);
        Gson gson = new Gson();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost/index/get_academy_owner_reminder_data", map, String.class);
        if (HttpStatus.OK == response.getStatusCode()) {
            JSONArray jsonArray = new JSONArray(response.getBody());
            for (int i = 0; i < jsonArray.length(); i++) {
                String notificationId = jsonArray.getJSONObject(i).getString("notification_id");
                int count = jsonArray.getJSONObject(i).getInt("count");
                if(count > 0) {
                    NotificationBean notificationBean = new NotificationBean();
                    notificationBean.setNotificationId(notificationId);
                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("content", count + " members have their fees pending today");
                    dataMap.put("title", "MySportsMeet Academy");
                    notificationBean.setDataMap(dataMap);
                    notificationBeanList.add(notificationBean);
                }
            }
            notificationManager.sendNotification(notificationBeanList);
        } else {
            //Notify admin
        }
    }

}