package com.learn.utils;

import java.util.Random;

public class PhoneRandomUtil {
    public static String getRandomPhone(){
        Random random = new Random();
        String phonePrefix = "156";
        for(int i = 0;i < 8;i++){
            int num = random.nextInt(9);
            phonePrefix = phonePrefix + num;
        }
        return phonePrefix;
    }
    public static String getUnRegisterPhone(){
        String phone = "";
        while(true){
            phone = getRandomPhone();
            Object result = SqlUtils.scarlarHandler("select count(*) from member where mobile_phone ="+phone);
            if((Long)result == 0){
                break;
            }else {
                continue;
            }
        }
        return phone;
    }
}
