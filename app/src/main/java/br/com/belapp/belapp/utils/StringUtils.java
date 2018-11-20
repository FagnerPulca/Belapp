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
        if(senha.length() < 6){
            return true;
        }
        return false;
    }
}
