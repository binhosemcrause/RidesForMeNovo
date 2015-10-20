package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.ridesforme.ridesforme.basicas.Carona;
import com.ridesforme.ridesforme.util.NotificationUtils;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FilterCaronaActivity extends AppCompatActivity implements View.OnClickListener{


    Button mBotaoPesquisar;
    EditText bairroOridem;
    EditText  bairroDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_carona);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bairroOridem = (EditText) findViewById(R.id.bairroOridem);
        bairroDestino = (EditText) findViewById(R.id.bairroDestino);
        mBotaoPesquisar = (Button)findViewById(R.id.btnPesquisarCarona);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBotaoPesquisar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Carona carona = new Carona();
        NotificationUtils.criarNotificacao(getApplicationContext(), "teste", 1);
        carona.setBairroOrigem(bairroOridem.getText().toString());
        carona.setBairroDestino(bairroDestino.getText().toString());
        Intent it = new Intent(this, PesquisarCaronaActivity.class);
        it.putExtra("filtro","true");
        it.putExtra("carona_filtrada", carona);
        startActivity(it);
    }
}
