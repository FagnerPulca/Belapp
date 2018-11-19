package br.com.belapp.belapp.presenter;

import android.app.Application;

import java.util.ArrayList;

import br.com.belapp.belapp.DAO.EstabelecimentoDAO;
import br.com.belapp.belapp.model.Agenda;
import br.com.belapp.belapp.model.Endereco;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Profissional;
import br.com.belapp.belapp.model.Promocoes;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.model.Teste;

public class ApplicationClass extends Application {

    public static ArrayList<Teste> testes;
    public static ArrayList<Estabelecimento> estabelecimentos;
    public static ArrayList<Endereco> enderecos;
    public static ArrayList<Servico> servicos;
    public static ArrayList<Profissional> profissionais;

    public static ArrayList<Servico> subServicos1;
    public static ArrayList<Servico> subServicos2;
    public static ArrayList<Servico> subServicos3;

    public static ArrayList<Profissional> subProfissionais1;
    public static ArrayList<Profissional> subProfissionais2;
    public static ArrayList<Profissional> subProfissionais3;

    public static ArrayList<String> avaliacoes;
    public static ArrayList<Agenda> agenda;
    public static ArrayList<Promocoes> promocoes;

    @Override
    public void onCreate() {
        super.onCreate();

        testes = new ArrayList<Teste>();
        testes.add(new Teste("00","Salao Beauty", "Barba"));
        testes.add(new Teste("01","Salao Great", "Cabelo"));
        testes.add(new Teste("02","Salao Beauty", "Barba"));
        testes.add(new Teste("03","Salao Great", "Olho"));
        testes.add(new Teste("04","Salao Beauty", "Unha"));
        testes.add(new Teste("05","Salao Great", "Olho"));

        profissionais = new ArrayList<Profissional>();
        profissionais.add(new Profissional("01","João", "ABC", "Sim"));
        profissionais.add(new Profissional("02","José", "ABC", "Não"));
        profissionais.add(new Profissional("03","Maria", "ABC", "Não"));
        profissionais.add(new Profissional("04","Fulano", "ABC", "Sim"));

        servicos = new ArrayList<Servico>();
        servicos.add(new Servico("01", "Corte simples", 10.00, "30",profissionais.get(0), "Cabelo"));
        servicos.add(new Servico("02", "Corte channel", 15.00, "30",profissionais.get(1), "Cabelo"));
        servicos.add(new Servico("03", "Alisamento", 20.00, "1:00",profissionais.get(2), "Cabelo"));
        servicos.add(new Servico("04", "Pintura", 16.00, "30",profissionais.get(3), "Unha"));

        enderecos = new ArrayList<Endereco>();
        enderecos.add(new Endereco("Rua X", "11", "Boa Vista", "Garanhuns", "", "55292-000", "L123456"));
        enderecos.add(new Endereco("Rua H", "15", "Boa Vista", "Garanhuns", "", "55292-000", "L123456"));
        enderecos.add(new Endereco("Rua C", "120", "Boa Viagem", "Recife", "", "55292-000", "L123456"));

        subServicos1 = new ArrayList<Servico>(); //serviços do Salao Beauty
        subServicos1.add(servicos.get(0));
        subServicos1.add(servicos.get(1));

        subServicos2 = new ArrayList<Servico>(); //serviços do Salão Great
        subServicos2.add(servicos.get(2));

        subServicos3 = new ArrayList<Servico>(); //serviços do Salão Maximum
        subServicos3.add(servicos.get(3));

        subProfissionais1 = new ArrayList<Profissional>(); //funcionários do Salão Beauty
        subProfissionais1.add(profissionais.get(0));
        subProfissionais1.add(profissionais.get(1));

        subProfissionais2 = new ArrayList<Profissional>(); //funcionários do Salão Great
        subProfissionais2.add(profissionais.get(2));

        subProfissionais3 = new ArrayList<Profissional>(); //funcionários do Salão Maximum
        subProfissionais3.add(profissionais.get(3));

        estabelecimentos = new ArrayList<Estabelecimento>();
        estabelecimentos.add(new Estabelecimento("Salão Beauty", "58964712", "Sua beleza em 1º lugar", "5", "Gal",
                enderecos.get(0), "123", -9, -37, subServicos1, agenda, promocoes, subProfissionais1, avaliacoes));
        estabelecimentos.add(new Estabelecimento("Salão Great", "58966574", "Sua beleza é prioridade", "5", "Gal",
                enderecos.get(1), "123", -8.5, -37, subServicos2, agenda, promocoes, subProfissionais2, avaliacoes));
        estabelecimentos.add(new Estabelecimento("Salão Maximum", "58964712", "ABC", "5", "Gal",
                enderecos.get(2), "123", -9, -39, subServicos3, agenda, promocoes, subProfissionais3, avaliacoes));
    }

    private double calculaDistancia(double lat1, double lng1, double lat2, double lng2) {
        //double earthRadius = 3958.75;//miles
        double earthRadius = 6371;//kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist * 1000; //em metros
    }
}
