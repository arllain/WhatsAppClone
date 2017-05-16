package com.candido.arllain.whatsappclone.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by arllain on 13/05/17.
 */

public class Preferencia {

    public static final String NOME = "nome";
    public static final String TELEFONE = "token";
    public static final String TOKEN = "token";
    private Context contexto;
    private SharedPreferences preferencias;
    private final String NOME_ARQUIVO = "preferenciass";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    public Preferencia(Context context){
        this.contexto = context;
        preferencias = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferencias.edit();


    }

    public void salvarUsuarioPreferencias(String nome, String telefone, String token){
        editor.putString(NOME, nome);
        editor.putString(TELEFONE, telefone);
        editor.putString(TOKEN, token);
        editor.commit();
    }


    public HashMap<String,String> getDadosUsuario(){
        HashMap<String,String> dadosUsuario = new HashMap<>();
        dadosUsuario.put(NOME,preferencias.getString(NOME, null));
        dadosUsuario.put(TELEFONE,preferencias.getString(TELEFONE, null));
        dadosUsuario.put(TOKEN,preferencias.getString(TOKEN, null));
        return dadosUsuario;
    }

}

