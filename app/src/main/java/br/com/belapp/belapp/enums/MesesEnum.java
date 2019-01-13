package br.com.belapp.belapp.enums;

import java.util.ArrayList;

public enum MesesEnum {
    TodosMeses("Todos os me.", -1),
    Janeiro("Janeiro", 1),
    Fevereiro("Fevereiro", 2),
    Marco("Março", 3),
    Abril("Abril", 4),
    Maio("Maio", 5),
    Junho("Junho", 6),
    Julho("Março", 7),
    Agosto("Agosto",8 ),
    Setembro("Setembro", 9),
    Outubro("Outubro", 10),
    Novembro("Novembro", 11),
    Dezembro("Dezembro", 12);

    private int valor;
    private String mes;
    MesesEnum(String mes, int valor){
        this.valor = valor;
        this.mes = mes;
    }

    public int getValor(){
        return this.valor;
    }

    public String getMes(){
        return this.mes;
    }

    public static String getMes(int valor){
        for(MesesEnum e : values()){
            if (e.getValor() == valor){
                return e.getMes();
            }
        }
        return "";
    }

    public static int getMesIndex(int valor){
        String avaliacao = getMes(valor);
        for(String e : getListaMeses()){
            if (e.equalsIgnoreCase(avaliacao)){
                return getListaMeses().indexOf(e);
            }
        }
        return -1;
    }

    public static int getValor(String avaliacao){
        for(MesesEnum e : values()){
            if (e.getMes().equalsIgnoreCase(avaliacao)){
                return e.getValor();
            }
        }
        return 0;
    }

    public static ArrayList<String> getListaMeses(){
        ArrayList<String> lista = new ArrayList<>();
        for(MesesEnum e: values()){
            lista.add(e.getMes());
        }
        return lista;
    }
}