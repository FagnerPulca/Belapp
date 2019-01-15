package br.com.belapp.belapp.enums;

import java.util.Calendar;

public enum DiasSemanaEnum {
    DOMINGO("Domingo", Calendar.SUNDAY),
    SEGUNDA("Segunda", Calendar.MONDAY),
    TERCA("Terça", Calendar.TUESDAY),
    QUARTA("Quarta", Calendar.WEDNESDAY),
    QUINTA("Quinta", Calendar.THURSDAY),
    SEXTA("Sexta", Calendar.FRIDAY),
    SABADO("Sábado", Calendar.SATURDAY);

    private int codigo;
    private String dia;

    DiasSemanaEnum(String dia, int codigo) {
        this.codigo = codigo;
        this.dia = dia;
    }

    public int getCodigo(){
        return this.codigo;
    }

    public String getDia(){
        return this.dia;
    }

    public static String getDia(int codigo){
        for(DiasSemanaEnum e : values()){
            if (e.getCodigo() == codigo){
                return e.getDia();
            }
        }
        return "";
    }

    public static int getCodigo(String status){
        for(DiasSemanaEnum e : values()){
            if (e.getDia().equalsIgnoreCase(status)){
                return e.getCodigo();
            }
        }
        return 0;
    }
}
