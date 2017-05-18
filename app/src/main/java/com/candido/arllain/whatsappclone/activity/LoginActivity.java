package com.candido.arllain.whatsappclone.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.candido.arllain.whatsappclone.R;
import com.candido.arllain.whatsappclone.helper.Permissoes;
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
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissoes.validaPermissoes(1, this, permissoesNecessarias);

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
                String telefoneSemFormatacao = ((codPais.getText().toString() + codArea.getText().toString() + telefone.getText().toString()).replace("+", "")).replace("-", "");

                // Gerar token
                Random random = new Random();
                int numeroRandomico = random.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);

                Preferencia preferencia = new Preferencia(LoginActivity.this);
                preferencia.salvarUsuarioPreferencias(nome.getText().toString(), telefoneSemFormatacao, token);

                telefoneSemFormatacao = "5554";
                boolean enviado = enviaSMS("+" + telefoneSemFormatacao, "Código de confirmação: " + token);

//                HashMap<String, String> usuario = preferencia.getDadosUsuario();
//                Log.i("Token", "t: " + usuario.get("token"));


            }
        });
    }

    private boolean enviaSMS(String telefone, String mensagem){

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;

    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultado : grantResults) {
            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();                
            }
        }
    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int wich){
                finish();
            }

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}


