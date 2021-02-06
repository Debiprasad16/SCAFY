package com.example.scafy;

import java.util.Calendar;

public class scafyFunctions {
    public static String wishes(){
        String string = "";
        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        if(time >= 0 && time < 12){
            string = "Good Morning Debeeprasad!";
        }else if(time >= 12 && time < 16){
            string = "Good Afternoon Debeeprasad!";
        }else if(time >= 16 && time < 20){
            string = "Good Evening Debeeprasad!";
        }else if(time >= 20 && time < 22){
            string = "Good Night Debeeprasad!";
        }else if(time >= 22 && time < 24){
            string = "Debeeprasad, It's too late. You need to Sleep.";
        }
        return string;
    }
}
