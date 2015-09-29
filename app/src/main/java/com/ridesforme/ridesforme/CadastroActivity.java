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

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final EditText txtLogin = (EditText) findViewById(R.id.edtCadUser);
        final EditText txtEmail = (EditText) findViewById(R.id.edtCadEmail);
        final EditText txtSenha = (EditText) findViewById(R.id.edtCadSenha);
        final Button btnCad = (Button) findViewById(R.id.btnCad);

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b;
                String login = txtLogin.getText().toString();
                String senha = txtSenha.getText().toString();
                String email = txtEmail.getText().toString();

                try {
                    b = new CadastroControllerTask().execute(login,email,senha).get();
                    if (b==true) {
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
        });
    }



    public static class CadastroControllerTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBuilder()
                        .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
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
}


