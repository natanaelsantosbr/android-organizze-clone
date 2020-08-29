package com.cursoandroid.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual() {
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(date);
        return dataString;
    }

}
