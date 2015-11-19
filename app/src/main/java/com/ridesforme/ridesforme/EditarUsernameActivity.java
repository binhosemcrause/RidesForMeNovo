package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ridesforme.ridesforme.basicas.Usuario;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class EditarUsernameActivity extends AppCompatActivity {
    Usuario mUsuario;
    Button mBotaoOk;
    Button mBotaoCancelar;
    EditText mEditTextUsername;
    EditarUsuarioTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_username);

        mBotaoOk = (Button) findViewById(R.id.editar_username_botao_ok);
        mBotaoCancelar = (Button )findViewById(R.id.editar_username_botao_cancelar);
        mEditTextUsername = (EditText) findViewById((R.id.editar_username_editText));

        mUsuario = (Usuario) getIntent().getSerializableExtra("usuario");

        Log.i("usuario", "EditarUsernameActivity: " + mUsuario.toString());

        mEditTextUsername.setText(mUsuario.getNome());

        mBotaoOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = gson.toJson(mUsuario);
                Log.i("json", json);
                mUsuario.setNome(String.valueOf(mEditTextUsername.getText()));
                mTask = new EditarUsuarioTask();
                mTask.execute(json);
            }
        });

        mBotaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public class EditarUsuarioTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Intent intent = new Intent(EditarUsernameActivity.this, PerfilActivity.class);
            intent.putExtra("usuario", mUsuario);
            startActivity(intent);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBuilder()
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("usuario",params[0])
                        .build();

                Request request = new Request.Builder()
                        .url("http://ridesforme.no-ip.info:8080/rpg/usuario/atualizarUsuario")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    response.body().close();
                    Log.v("a", responseString);

                    return  Boolean.parseBoolean(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("erro", "Erro ao atualizar o usuario");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
