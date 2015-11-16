package com.ridesforme.ridesforme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.ridesforme.ridesforme.basicas.Carona;
import com.ridesforme.ridesforme.util.DataUtil;
import com.ridesforme.ridesforme.util.NotificationUtils;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    static final int DATE_DIALOG_ID = 0;

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


        dataOrigem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

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
        carona.setDataHoraSaidaIda(DataUtil.stringToDate(dataOrigem.getText().toString()));
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

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes, dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String data = String.valueOf(dayOfMonth) + " /" + String.valueOf(monthOfYear + 1) + " /" + String.valueOf(year);
            Toast.makeText(getApplicationContext(), "DATA = " + data, Toast.LENGTH_SHORT).show();
        }
    };





}
