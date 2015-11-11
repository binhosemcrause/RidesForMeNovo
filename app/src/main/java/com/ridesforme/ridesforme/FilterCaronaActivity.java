package com.ridesforme.ridesforme;

import android.app.Dialog;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterCaronaActivity extends AppCompatActivity implements View.OnClickListener {


    Button mBotaoPesquisar;
    EditText bairroOrigem;
    EditText bairroDestino;
    EditText dataOrigem;
    EditText horaOrigem;
    RadioButton caronaGratis;
    RadioButton caronasPagas;
    RadioButton todasAsCaronas;
    RadioGroup radioGroup;
    View radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_carona);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bairroOrigem = (EditText) findViewById(R.id.bairroOridem);
        bairroDestino = (EditText) findViewById(R.id.bairroDestino);
        mBotaoPesquisar = (Button) findViewById(R.id.btnPesquisarCarona);
        dataOrigem = (EditText) findViewById(R.id.edtFiltroDataSaida);
        horaOrigem = (EditText) findViewById(R.id.edtFiltroHoraSaida);
        caronaGratis = (RadioButton) findViewById(R.id.rdbFree);
        caronasPagas = (RadioButton) findViewById(R.id.rdbPagas);
        todasAsCaronas = (RadioButton) findViewById(R.id.rdbAll);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBotaoPesquisar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        radioButton = radioGroup.findViewById(radioButtonID);

        Carona carona = new Carona();
        NotificationUtils.criarNotificacao(getApplicationContext(), "teste", 1);
        carona.setBairroOrigem(bairroOrigem.getText().toString());
        carona.setBairroDestino(bairroDestino.getText().toString());
        // carona.setDataHoraSaidaIda(dataOrigem.getText().toString());
        //carona.setHoraOrigem(horaOrigem.getText().toString());

        Log.i("teste", Integer.toString(radioGroup.indexOfChild(radioButton)));

        if (isDadosValidos()) {
            if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    dataOrigem.getText().toString().trim().length() > 0 &&
                    horaOrigem.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 0) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroDataHoraGratis");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    dataOrigem.getText().toString().trim().length() > 0 &&
                    horaOrigem.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 1) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroDataHoraPaga");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    dataOrigem.getText().toString().trim().length() > 0 &&
                    horaOrigem.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 2) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroDataHoraTudo");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    dataOrigem.getText().toString().trim().length() > 0 &&
                    horaOrigem.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 2) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroDataHoraTudo");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    dataOrigem.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 0) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroDataGratis");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    dataOrigem.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 1) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroDataPaga");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    dataOrigem.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 2) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroDataTudo");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 0) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroGratis");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 1) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroPaga");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            } else if (bairroOrigem.getText().toString().trim().length() > 0 &&
                    bairroDestino.getText().toString().trim().length() > 0 &&
                    radioGroup.indexOfChild(radioButton) == 2) {
                Intent it = new Intent(this, PesquisarCaronaActivity.class);
                it.putExtra("filtro", "bairroTudo");
                it.putExtra("carona_filtrada", carona);
                startActivity(it);
            }
        }else {
            Toast.makeText(getApplicationContext(),"Campos obrigadorios", Toast.LENGTH_SHORT).show();
        }



        /*if (!dataOrigem.getText().toString().equals(null) || !dataOrigem.getText().toString().equals("") && !horaOrigem.getText().toString().equals("") || !horaOrigem.getText().toString().equals(null)) {
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

            if (bairroOrigem.getText().toString().trim().length() >0 && bairroDestino.getText().toString().trim().length() >0) {
            Intent it = new Intent(this, PesquisarCaronaActivity.class);
            it.putExtra("filtro","bairro");
            it.putExtra("carona_filtrada", carona);
            startActivity(it);
        }

        }*/


    }

    private boolean isDadosValidos() {
        boolean validado = true;

        if(bairroOrigem.getText().toString().equals("")){
            bairroOrigem.setError(getString(R.string.alert_campo_obrigatorio));
            bairroOrigem.requestFocus();
            validado = false;
        }
        if(bairroDestino.getText().toString().equals("")){
            bairroDestino.setError(getString(R.string.alert_campo_obrigatorio));
            bairroDestino.requestFocus();
            validado = false;
        }
        if(radioGroup.indexOfChild(radioButton) == -1) {
            Toast.makeText(getApplicationContext(),"Selecione uma opção!",Toast.LENGTH_SHORT).show();
        }
        return validado;
    }

    public Boolean verificarEspacoBranco(String s) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(s);
        boolean found = matcher.find();
        return found;
    }


}
