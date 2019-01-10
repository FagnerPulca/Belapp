package br.com.belapp.belapp.model;

import java.io.Serializable;

import br.com.belapp.belapp.enums.DiasSemanaEnum;

public class HorarioAtendimento implements Serializable {

    private int mAbertura;
    private int mFechamento;
    private int mDiaFuncionamento;

    public int getmAbertura() {
        return mAbertura;
    }

    /**
     * @return código do dia do enum DiasSemanaEnum
     */
    public int getmDiaFuncionamento() {
        return mDiaFuncionamento;
    }

    /**
     * @param cod do dia
     * @return o nome do dia da semana
     */
    public String getmDiaFuncionamento(int cod) {
        return DiasSemanaEnum.getDia(cod);
    }

    /**
     * @return horário de fechamento em minutos
     */
    public int getmFechamento() {
        return mFechamento;
    }

    /**
     * @param mAbertura horário de abertura em minutos
     */
    public void setmAbertura(int mAbertura) {
        this.mAbertura = mAbertura;
    }

    /**
     *
     * @param codigo do dia de funcionamento, disponível em DiasSemanaEnum
     */
    public void setmDiaFuncionamento(int codigo) {
        this.mDiaFuncionamento = codigo;
    }

    /**
     * @param mFechamento horário de fechamento em minutos
     */
    public void setmFechamento(int mFechamento) {
        this.mFechamento = mFechamento;
    }

}
