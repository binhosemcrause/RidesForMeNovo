package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.ridesforme.ridesforme.basicas.Carona;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FilterCaronaActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mTxtEnderecoOrigem;
    EditText mTxtEnderecoDestino;
    Button mBotaoPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_carona);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTxtEnderecoOrigem = (EditText)findViewById(R.id.edtOrigem);
        mTxtEnderecoDestino = (EditText)findViewById(R.id.edtDestino);
        mBotaoPesquisar = (Button)findViewById(R.id.btnPesquisarCarona);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBotaoPesquisar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Carona carona = new Carona();
        carona.endereco = mTxtEnderecoOrigem.getText().toString();
        carona.enderecoDestino = mTxtEnderecoDestino.getText().toString();
        Intent it = new Intent(this, PesquisarCaronaActivity.class);
        it.putExtra("carona_filtrada", carona);
        startActivity(it);
    }
}
