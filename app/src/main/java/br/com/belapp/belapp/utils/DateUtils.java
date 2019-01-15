package br.com.belapp.belapp.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
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

    /**
     * Verifica se data é segunda .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeSegunda(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }

    /**
     * Verifica se data é terça .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeTerca(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    }

    /**
     * Verifica se data é quarta .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeQuarta(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
    }

    /**
     * Verifica se data é Quinta .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeQuinta(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
    }

    /**
     * Verifica se data é sexta .
     *
     * @param   data        Um objeto Calendar
     * @return  boolean
     */
    public static boolean checarSeSexta(Calendar data){
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
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

     /* Este Método retorna o dia da semana referente a data informado no
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

    /**
     * @param data no formato DD/MM/YYYY a ser verificada
     * @return true se é data futura
     */
    public static boolean isDataFutura(String data){
        return converterDataEmCalendar(data).after(converterDataEmCalendar(getDataAtual()));
    }

    /**
     * @param data no formato DD/MM/YYYY a ser verificada
     * @return true se é data presente
     */
    public static boolean isDataPresente(String data){
        return getDiferencaEntreDuasDatasEspecificas(getDataAtual(), data) == 0;
    }

    /**
     * @param dias a serem somados na data
     * @param data base no formado DD/MM/YYYY
     * @return Data atualizada data + dias no formado DD/MM/YYYY
     */
    public static String getSomaDiasComDataEspecifica(int dias, String data) {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        String[] componentesData = data.split("/");
        c.set(Integer.parseInt(componentesData[2]), Integer.parseInt(componentesData[1]) - 1, Integer.parseInt(componentesData[0]) + dias);
        return sd.format(c.getTime());
    }

    /**
     * Gera uma data com base na data atual
     * @return uma data no formato  DD/MM/YYYY
     */
    public static String gerarData(){
        int numero = (int) (Math.random() * 10) + 1;
        return getSomaDiasComDataEspecifica(numero, getDataAtual());
    }

    public static String gerarDataValida(Collection<Integer> diasValidos){
        String data = gerarData();
        boolean find = false;
        while (!find){
            if(diasValidos.contains(getDiaDaSemanaEmData(data))){
                find = true;
            }
        }
        return data;
    }
    /**
     *
     * @param diasInvalidos onde a data não pode estar
     * @return uma data que não pertença aos diasInvalidos
     */
    public static String gerarDataInvalida(Collection<Integer> diasInvalidos){
        String data = "";
        boolean find = false;
        if(diasInvalidos.size() == 7){
            return getSomaDiasComDataEspecifica(-1, getDataAtual());
        }
        while (!find){
            data = gerarData();
            if(!diasInvalidos.contains(getDiaDaSemanaEmData(data))){
                find = true;
            }
        }
        return data;
    }


    /**
     * @param dias a serem subtraídos da data base
     * @param data base no formado DD/MM/YYYY
     * @return Data atualizada data - dias no formado DD/MM/YYYY
     */
    public static String getSubtracaoDiasComDataEspecifica(int dias, String data) {

        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        String[] componentesData = data.split("/");
        c.set(Integer.parseInt(componentesData[2]), Integer.parseInt(componentesData[1]) - 1, Integer.parseInt(componentesData[0]) - dias);
        return sd.format(c.getTime());

    }


    public static int getDiferencaEntreDuasDatasEspecificas(String data1, String data2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        String[] componenetesData1 = data1.split("/");
        String[] componenetesData2 = data2.split("/");
        c1.set(
                Integer.parseInt(componenetesData1[2]),
                Integer.parseInt(componenetesData1[1]) - 1,
                Integer.parseInt(componenetesData1[0]));
        c2.set(
                Integer.parseInt(componenetesData2[2]),
                Integer.parseInt(componenetesData2[1]) - 1,
                Integer.parseInt(componenetesData2[0]));

        double milles = c2.getTimeInMillis() - c1.getTimeInMillis();
        double seconds = milles / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;

        return new Double(Math.floor(days)).intValue();
    }

    /**
     * @param data no formato dd/mm/yyyy
     * @param mes código do mês
     * @return true se a data pertence ao mês
     */
    public static boolean checarSeDataPertenceAoMes(String data, int mes){
        return (Integer.parseInt(data.split("/")[1]) == mes);
    }







}
