package br.com.belapp.belapp.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /**
     * Metodo que obtem a data atual do smartphone.
     * @return data no formato dd/mm/YYYY.
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDataAtual(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    /**
     * Verifica se data á sábado .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeSabado(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }


    /**
     * Verifica se data e domingo .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeDomingo(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public static String getFormatoHora(int hora, int minutos){
        String sh = String.valueOf(hora);
        String sm = String.valueOf(minutos);
        if(sh.length() == 1){
            sh = sh.replaceAll(sh, "0"+sh);
        }
        if(sm.length() == 1){
           sm = sm.replaceAll(sm, "0"+sm);
        }

        return String.format(Locale.getDefault(), "%s:%s", sh, sm);
    }

    /**
     * Converte horario para padrao em minutos
     * @param hora no formato HH:MM
     * @return horario em minutos
     */
    public static int converterHoraEmMinutos(String hora){
        int horaEmMinutos = Integer.parseInt(hora.split(":")[0])*60;
        int minutosDaHora = Integer.parseInt(hora.split(":")[1]);
        return horaEmMinutos+minutosDaHora;
    }

    /**
     * Este Método retorna o dia da semana referente a data informado no
     * parâmetro
     *
     * 1º dia da semana retorna 1; Ultimo dias da semana retorna 7;

     * @param data data no formato formato DD/MM/YYYY
     * @return dia da semana referente a data informada
     */
    public static int getDiaDaSemanaEmData(String data) {
        return converterDataEmCalendar(data).get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Este Método converte uma data no formato DD/MM/YYYY no formato de data
     * calendar.
     *
     * @param data string data no formato DD/MM/YYYY
     * @return data no formato Calendar
     */
    private static Calendar converterDataEmCalendar(String data) {
        Calendar c = Calendar.getInstance();
        String[] d = data.split("/");
        c.set(Integer.parseInt(d[2]), Integer.parseInt(d[1]) - 1, Integer.parseInt(d[0]));
        return c;
    }

}
