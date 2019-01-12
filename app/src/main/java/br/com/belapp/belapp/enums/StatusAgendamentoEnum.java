package br.com.belapp.belapp.enums;

import java.util.ArrayList;

public enum StatusAgendamentoEnum {

    AGENDADO("Serviços Agendados", 1),
    CANCELADO("Serviços Concluídos", 2),
    TODOS("Todos", 0);

    private int codigo;
    private String status;

    StatusAgendamentoEnum(String status, int codigo) {
        this.codigo = codigo;
        this.status = status;
    }

    public int getCodigo(){
        return this.codigo;
    }

    public String getStatus(){
        return this.status;
    }

    public static String getStatus(int codigo){
        for(StatusAgendamentoEnum e : values()){
            if (e.getCodigo() == codigo){
                return e.getStatus();
            }
        }
        return "";
    }

    public static int getCodigo(String status){
        for(StatusAgendamentoEnum e : values()){
            if (e.getStatus().equalsIgnoreCase(status)){
                return e.getCodigo();
            }
        }
        return 0;
    }

    public static ArrayList<String> getListaStatus(){
        ArrayList<String> lista = new ArrayList<>();
        for(StatusAgendamentoEnum e: values()){
            lista.add(e.getStatus());
        }
        return lista;
    }
}
