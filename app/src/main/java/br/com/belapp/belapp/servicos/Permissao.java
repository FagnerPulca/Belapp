package br.com.belapp.belapp.servicos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import br.com.belapp.belapp.activities.CadastroBasicoActivity;
import br.com.belapp.belapp.activities.LoginActivity;

import static android.support.v4.content.ContextCompat.startActivity;


public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes ){

        if(Build.VERSION.SDK_INT >= 23 ){

            List<String> listaPermissoes = new ArrayList<>();

            /*Percorre as permissões passadas, verificando uma a uma
             * se já tem a permissao liberada */
            for(String permissao : permissoes ){
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if( !validaPermissao ) listaPermissoes.add(permissao);
            }

            /*Caso a lista esteja vazia, não é necessário solicitar permissão*/
            if( listaPermissoes.isEmpty() ) return true;

            String[] novasPermissoes = new String[ listaPermissoes.size() ];
            listaPermissoes.toArray( novasPermissoes );

            //Solicita permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode );

        }

        return false;

    }

    public static boolean verificarPermissaoRestritivo(Context contexto){
        FirebaseAuth autenticacao = FirebaseAuth.getInstance();
        if(autenticacao.getCurrentUser() == null){
            alertaPrecisaEntrar(contexto);
            return false;
        }
        return true;
    }

    private static void alertaPrecisaEntrar(Context contexto){
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Acesso negado");
        builder.setMessage("É necessário entrar no sistema.\n\nVocê já está cadastrado?");
        builder.setPositiveButton("SIM", (dialogInterface, i) -> {
            Intent intentAbritCadastro = new Intent(contexto , LoginActivity.class);
            startActivity(contexto,intentAbritCadastro,null);
        });
        builder.setNegativeButton("NÃO", (dialogInterface, i) -> {
            Intent intentAbritCadastro = new Intent(contexto , CadastroBasicoActivity.class);
            startActivity(contexto,intentAbritCadastro,null);
        });
        AlertDialog dialog =  builder.create();
        dialog.show();
    }

    public static boolean estaLogado(){
        FirebaseAuth autenticacao = FirebaseAuth.getInstance();
        if (autenticacao.getCurrentUser() == null) return false;
        else return true;
    }
}
