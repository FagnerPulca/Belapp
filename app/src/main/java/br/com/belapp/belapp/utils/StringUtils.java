package br.com.belapp.belapp.utils;

import android.text.TextUtils;

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
}
