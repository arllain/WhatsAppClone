package com.candido.arllain.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.candido.arllain.whatsappclone.R;
import com.candido.arllain.whatsappclone.helper.Preferencia;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codValidacao;
    private Button validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codValidacao = (EditText) findViewById(R.id.editCodValidacao);
        validar = (Button) findViewById(R.id.btValidar);
        SimpleMaskFormatter simpleMaskCodValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(codValidacao, simpleMaskCodValidacao);
        codValidacao.addTextChangedListener(maskTextWatcher);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencia preferencia = new Preferencia(ValidadorActivity.this);
                HashMap<String,String> usuario = preferencia.getDadosUsuario();
                String tokenGerado = usuario.get("token");
                String tokenDigitado = codValidacao.getText().toString();
                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "Token validado.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "Token não validado.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
