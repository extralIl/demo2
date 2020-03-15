package com.extra.demo.util;

import java.util.Random;

public class Util {

    public static String getVerifyNumber(){
        Random random = new Random();
        String sum = "";
        for(int i=0;i<6;i++){
            sum = sum+random.nextInt(10);
        }
        return sum;
    }


}
