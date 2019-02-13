package com.zl.swmonk.zonghe.base.util;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getDateString(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }
}
