package br.com.belapp.belapp.utils;

import android.text.TextUtils;

import java.util.Locale;

public class StringUtils {

    private StringUtils(){}

    public static boolean isEmailValido(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isVerificarSenhaInvalida(String senha){
        boolean retorno = false;
        if(senha.length() < 6){
            retorno = true;
        }
        return retorno;
    }

    public static String gerarEmail(){
        int numero = (int) (Math.random() * 10000) + 1;
        return String.format(Locale.getDefault(), "teste%d@teste.com", numero);
    }

    public static String getDinheiro(double valor){
        return String.format(Locale.getDefault(),"R$ %.2f", valor);
    }

}
