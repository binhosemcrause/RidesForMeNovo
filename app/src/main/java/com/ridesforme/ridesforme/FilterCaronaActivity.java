package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.ridesforme.ridesforme.basicas.Carona;
import com.ridesforme.ridesforme.util.NotificationUtils;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class FilterCaronaActivity extends AppCompatActivity implements View.OnClickListener{


    Button mBotaoPesquisar;
    EditText bairroOridem;
    EditText  bairroDestino;
    EditText dataOrigem;
    EditText horaOrigem;
    RadioButton caronaGratis;
    RadioButton caronasPagas;
    RadioButton todasAsCaronas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_carona);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bairroOridem = (EditText) findViewById(R.id.bairroOridem);
        bairroDestino = (EditText) findViewById(R.id.bairroDestino);
        mBotaoPesquisar = (Button)findViewById(R.id.btnPesquisarCarona);
        dataOrigem = (EditText)findViewById(R.id.edtFiltroDataSaida);
        horaOrigem = (EditText)findViewById(R.id.edtFiltroHoraSaida);
        caronaGratis = (RadioButton)findViewById(R.id.rdbFree);
        caronasPagas = (RadioButton)findViewById(R.id.rdbPagas);
        todasAsCaronas = (RadioButton)findViewById(R.id.rdbAll);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBotaoPesquisar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Carona carona = new Carona();
        NotificationUtils.criarNotificacao(getApplicationContext(), "teste", 1);
        carona.setBairroOrigem(bairroOridem.getText().toString());
        carona.setBairroDestino(bairroDestino.getText().toString());
       // carona.setDataHoraSaidaIda(dataOrigem.getText().toString());
        //carona.setHoraOrigem(horaOrigem.getText().toString());

        if (!dataOrigem.getText().toString().equals(null) || !dataOrigem.getText().toString().equals("") && !horaOrigem.getText().toString().equals("") || !horaOrigem.getText().toString().equals(null)) {
            Log.i("entrou datahora", "datahora");
            Intent it = new Intent(this, PesquisarCaronaActivity.class);
            it.putExtra("filtro","data");
            it.putExtra("carona_filtrada", carona);
            startActivity(it);
        } else if (!dataOrigem.getText().toString().equals(null) || !dataOrigem.getText().toString().equals("") && horaOrigem.getText().toString().equals("") || horaOrigem.getText().toString().equals(null)){
            Log.i("entrou data", "data");
            Intent it = new Intent(this, PesquisarCaronaActivity.class);
            it.putExtra("filtro","data");
            it.putExtra("carona_filtrada", carona);
            startActivity(it);
        } else {
            Log.i("entrou bairro", "bairro");
            Intent it = new Intent(this, PesquisarCaronaActivity.class);
            it.putExtra("filtro","bairro");
            it.putExtra("carona_filtrada", carona);
            startActivity(it);
        }


    }
}
