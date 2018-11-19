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
