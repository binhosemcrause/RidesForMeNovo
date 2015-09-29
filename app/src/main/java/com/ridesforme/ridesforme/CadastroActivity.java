package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity {

    EditText txtLogin;
    EditText txtEmail;
    EditText txtSenha;
    EditText txtSenha2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtLogin = (EditText) findViewById(R.id.edtCadUser);
        txtEmail = (EditText) findViewById(R.id.edtCadEmail);
        txtSenha = (EditText) findViewById(R.id.edtCadSenha);
        txtSenha2 = (EditText) findViewById(R.id.edtCadConfirmSenha);

        final Button btnCad = (Button) findViewById(R.id.btnCad);

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b;
                String login = txtLogin.getText().toString();
                String senha = txtSenha.getText().toString();
                String email = txtEmail.getText().toString();
                if (isDadosValidos()) {
                    try {
                        b = new CadastroControllerTask().execute(login, email, senha).get();
                        if (b == true) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplication(), "Usuario ou Email já cadastrados", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }



    public static class CadastroControllerTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBuilder()
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("login",params[0])
                        .addFormDataPart("email",params[1])
                        .addFormDataPart("senha",params[2])
                        .build();
                Request request = new Request.Builder()
                        //teste login servidor casa felipe
                        .url("http://179.182.98.29:8080/rpg/usuario/cadastrarUsuario")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    response.body().close();
                    Log.v("a", responseString);
                    return Boolean.parseBoolean(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean isDadosValidos(){
        boolean validado = true;



        if (verificarEspacoBranco(txtLogin.getText().toString())) {
            txtLogin.setError("Não pode conter espaços em branco.");
            txtLogin.requestFocus();
            validado = false;
        }
        if (verificarEspacoBranco(txtSenha.getText().toString())) {
            txtSenha.setError("Não pode conter espaços em branco.");
            txtSenha.requestFocus();
            validado = false;
        }
        if (verificarEspacoBranco(txtEmail.getText().toString())) {
            txtEmail.setError("Não pode conter espaços em branco.");
            txtEmail.requestFocus();
            validado = false;
        }
        if (!verificarEmail(txtEmail.getText().toString())) {
            txtEmail.setError("Email Inválido.");
            txtEmail.requestFocus();
            validado = false;
        }


        if(txtLogin.getText().toString().equals("")){
            txtLogin.setError(getString(R.string.alert_campo_obrigatorio));
            txtLogin.requestFocus();
            validado = false;
        }
        if(txtEmail.getText().toString().equals("")){
            txtEmail.setError(getString(R.string.alert_campo_obrigatorio));
            txtEmail.requestFocus();
            validado = false;
        }
        if(txtSenha.getText().toString().equals("")){
            txtSenha.setError(getString(R.string.alert_campo_obrigatorio));
            txtSenha.requestFocus();
            validado = false;
        }


        if (!txtSenha.getText().toString().equals(txtSenha2.getText().toString())) {
            txtSenha.setError("Senhas não são iguais");
            txtSenha.requestFocus();
            validado = false;
        }

        return validado;
    }

    public Boolean verificarEspacoBranco(String s) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(s);
        boolean found = matcher.find();
        return found;
    }

    public boolean verificarEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}


