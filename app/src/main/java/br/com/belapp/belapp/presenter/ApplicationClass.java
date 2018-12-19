package br.com.belapp.belapp.presenter;

import android.app.Application;

public class ApplicationClass extends Application {


    /*public Estabelecimento estabelecimento1;
    public Estabelecimento estabelecimento2;
    public Estabelecimento estabelecimento3;
    public Estabelecimento estabelecimento4;
    public Estabelecimento estabelecimento5;
    public Estabelecimento estabelecimento6;*/

    @Override
    public void onCreate() {
        super.onCreate();

        /*estabelecimento1 = new Estabelecimento("-LS54ly9L9y6RsIuOGid", "Salão Beauty", "Descrição do salão Beauty", -8.899168, -36.490116, "Dr. Tomé Cavalcante", "186", "Santo Antônio", "Garanhuns", "","55293-440");
        estabelecimento2 = new Estabelecimento("-LS5qQlPYFyFBCY4E0Uz", "Salão Great", "Descrição do salão Great", -8.896258, -36.492095, "R. da Esperança", "215", "Santo Antônio", "Garanhuns", "","55293-160");
        estabelecimento3 = new Estabelecimento("-LS6-9l4lhGqiPxvZBFH", "Salão Maximum", "Descrição do salão Maximum", -8.895739, -36.494552, "Praça Dom Pedro II", "160", "Boa Vista", "Garanhuns", "","55292-625");
        estabelecimento4 = new Estabelecimento("-LSCFl_m-CKuH18ghfmb", "Salão do Danilo", "Descrição do salão do Danillo", -8.892464, -36.498532, "R. Maj. Antônio Pedrosa", "164", " Aluísio Pinto", "Garanhuns", "","55292-145");
        estabelecimento5 = new Estabelecimento("-LSHO0nkF9gGRlEBhypJ", "Salao do Fagner", "Descrição do salão do Fagner", -8.891617, -36.487891, "Av. Agamenon Magalhães", "616", "Santo Antônio", "Garanhuns", "","55293-290");
        estabelecimento6 = new Estabelecimento("-LSHO7lWb_XYNoeN0zCk", "Imperador Darth Vader", "O salão do império", -8.052062, -34.884475, "R. dos Palmares", "170", "Santo Amaro", "Recife", "","74970-240");

        EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO();
        estabelecimentoDAO.save(estabelecimento1);*/

        /*
        * private String mEid;
    private String mNome;
    private String mDescricao;
    private double mLatitude;
    private double mLongitude;
    private String mRua;
    private String mNumero;
    private String mBairro;
    private String mCidade;
    private String mComplemento;
    private String mCep;*/
    }

    public static double calculaDistancia(double lat1, double lng1, double lat2, double lng2) {
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

        return dist; //Km
    }


}
