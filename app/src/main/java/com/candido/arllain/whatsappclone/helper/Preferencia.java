package com.candido.arllain.whatsappclone.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arllain on 13/05/17.
 */

public class Preferencia {

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
}

