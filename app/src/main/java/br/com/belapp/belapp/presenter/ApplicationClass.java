package br.com.belapp.belapp.presenter;

import android.app.Application;

import java.util.ArrayList;

import br.com.belapp.belapp.model.Teste;

public class ApplicationClass extends Application {

    public static ArrayList<Teste> testes;

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
    }
}
