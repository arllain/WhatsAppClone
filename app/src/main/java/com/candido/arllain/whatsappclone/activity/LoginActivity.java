package com.candido.arllain.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.candido.arllain.whatsappclone.R;
import com.candido.arllain.whatsappclone.helper.Preferencia;
import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText codPais;
    private EditText codArea;
    private EditText telefone;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = (EditText) findViewById(R.id.editNome);
        codPais = (EditText) findViewById(R.id.editCodPais);
        codArea = (EditText) findViewById(R.id.editCodArea);
        telefone = (EditText) findViewById(R.id.editTelefone);
        cadastrar = (Button) findViewById(R.id.btCadastrar);

        SimpleMaskFormatter smfCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter smfCodArea = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher maskCodPais = new MaskTextWatcher(codPais, smfCodPais);
        codPais.addTextChangedListener(maskCodPais);

        MaskTextWatcher maskCodArea = new MaskTextWatcher(codArea, smfCodArea);
        codArea.addTextChangedListener(maskCodArea);

        MaskTextWatcher maskTel = new MaskTextWatcher(telefone, smfTelefone);
        telefone.addTextChangedListener(maskTel);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefoneSemFormatacao = ((codPais.getText().toString() + codArea.getText().toString() + telefone.getText().toString()).replace("+","")).replace("-","");

                // Gerar token
                Random random = new Random();
                int numeroRandomico = random.nextInt(9999-1000) + 1000;
                String token = String.valueOf(numeroRandomico);

                Preferencia preferencia = new Preferencia(LoginActivity.this);
                preferencia.salvarUsuarioPreferencias(nome.getText().toString(),telefoneSemFormatacao, token);

                HashMap<String,String> usuario = preferencia.getDadosUsuario();

                Log.i("Token", "t: " + usuario.get("token"));
            }
        });
    }
}
