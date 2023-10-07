package com.example.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        return simpleDateFormat.format(date);
    }
    public static double doiNhietDo(double doK){
        double doC = doK - 273.15;
        return  Math.round(doC*10/10) ;
    }
}
