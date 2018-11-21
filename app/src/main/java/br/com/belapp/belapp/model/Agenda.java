package br.com.belapp.belapp.model;

public class Agenda {

    //Aqui atributos e metodos da classe
    private String mId;
    private String data;
    private Cliente cliente;
    private Estabelecimento estabelecimento;
    private Profissional profissional;
    private Servico servico;
    private String status;

    public Agenda(String mId, String data, Cliente cliente, Estabelecimento estabelecimento, Profissional profissional, Servico servico, String status) {
        this.mId = mId;
        this.data = data;
        this.cliente = cliente;
        this.estabelecimento = estabelecimento;
        this.profissional = profissional;
        this.servico = servico;
        this.status = status;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
