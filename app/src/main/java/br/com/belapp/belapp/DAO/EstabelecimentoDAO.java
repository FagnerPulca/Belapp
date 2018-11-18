package br.com.belapp.belapp.DAO;

import java.util.ArrayList;

import br.com.belapp.belapp.model.Agenda;
import br.com.belapp.belapp.model.Endereco;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Profissional;
import br.com.belapp.belapp.model.Promocoes;
import br.com.belapp.belapp.model.Servico;

public class EstabelecimentoDAO {
    public EstabelecimentoDAO() {
    }

    public void inserirEstabelecimento(){
        Endereco endereco = new Endereco("Av Santo Antonio", "150", "Centro","Garanhuns","","55333-000",
                "L1234560");

        String end = endereco.addEndereco(endereco);

        ArrayList<Servico> servicos = new ArrayList<>();
        ArrayList<Agenda> agenda = new ArrayList<>();
        ArrayList<Promocoes> promocoes = new ArrayList<>();
        ArrayList<Profissional> profissionais = new ArrayList<>();
        ArrayList<String> avaliacoes = new ArrayList<>();

        Estabelecimento est = new Estabelecimento();
        est.addEstabelecimento("Salão Glamour", "8755555555", "Tudo que você precisa em um só lugar ", "5",
                "foto.pnj", end, "L123456",servicos, agenda, promocoes, profissionais,avaliacoes);


    }
}
