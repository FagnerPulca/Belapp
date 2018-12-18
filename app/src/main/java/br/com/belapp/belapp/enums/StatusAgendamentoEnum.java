package br.com.belapp.belapp.enums;

public enum StatusAgendamentoEnum {

    AGENDADO("Agendado", 1),
    CANCELADO("Cancelado", 2);

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
}
