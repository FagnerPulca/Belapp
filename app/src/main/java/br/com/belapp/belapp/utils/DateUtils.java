package br.com.belapp.belapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /**
     * Metodo que obtem a data atual do smartphone.
     * @return data no formato dd/mm/YYYY.
     */
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
     * Converte uma data para String no formato 00/00/0000
     * @param data do tipo Calendar
     * @return String com a data no formato 00/00/0000
     */
    public static String converterDataParaString(Calendar data){
        String formato = "dd/MM/yyyy";
        SimpleDateFormat formatador = new SimpleDateFormat(formato, new Locale("pt","BR"));
        return formatador.format(data.getTime());
    }
}
