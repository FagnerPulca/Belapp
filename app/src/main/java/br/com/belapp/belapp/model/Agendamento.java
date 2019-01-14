package br.com.belapp.belapp.model;

import java.io.Serializable;

public class Agendamento implements Serializable {

    private Servico mServico;
    private Estabelecimento mEstabelecimento;
    private String mId;
    private Profissional mProfissional;
    private String mCliente;
    private String mData;
    private String mHora;
    private String mStatus;

    public void setmId(String key) {
        this.mId = key;
    }

    public String getmId() {
        return mId;
    }

    public void setmServico(Servico mServico) {
        this.mServico = mServico;
    }

    public void setmEstabelecimento(Estabelecimento mEstabelecimento) {
        this.mEstabelecimento = mEstabelecimento;
    }

    public Estabelecimento getmEstabelecimento() {
        return mEstabelecimento;
    }

    public Profissional getmProfissional() {
        return mProfissional;
    }

    public Servico getmServico() {
        return mServico;
    }

    public void setmProfissional(Profissional mProfissional) {
        this.mProfissional = mProfissional;
    }

    public void setmCliente(String mCliente) {
        this.mCliente = mCliente;
    }

    public String getmCliente() {
        return mCliente;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }

    public String getmData() {
        return mData;
    }

    public void setmHora(String mHora) {
        this.mHora = mHora;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmHora() {
        return mHora;
    }

    public String getmStatus() {
        return mStatus;
    }

    @Override
    public boolean equals( Object obj) {
        return obj != null & obj instanceof Agendamento && this.getmId().equals(((Agendamento)obj).getmId());
    }
}
