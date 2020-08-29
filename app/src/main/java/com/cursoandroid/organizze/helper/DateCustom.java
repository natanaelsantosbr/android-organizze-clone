package com.cursoandroid.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual() {
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(date);
        return dataString;
    }

    public static String mesAnoDataEscolhida(String data) {

        String retornoData[] = data.split("/");

        String dia = retornoData[0]; //dia
        String mes = retornoData[1]; //mes
        String ano = retornoData[2]; //ano;

        return  mes + ano;
    }
}
