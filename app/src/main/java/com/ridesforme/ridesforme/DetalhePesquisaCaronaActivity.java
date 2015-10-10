package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ridesforme.ridesforme.basicas.Carona;

import org.w3c.dom.Text;

public class DetalhePesquisaCaronaActivity extends AppCompatActivity {

    TextView mTxtEnderecoOrigem;
    TextView mTxtCidadeOrigem;
    TextView mTxtBairroOrigem;
    TextView mTxtNumeroOrigem;
    TextView mTxtEnderecoDestino;
    TextView mTxtCidadeDestino;
    TextView mTxtBairroDestino;
    TextView mTxtNumeroDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_pesquisa_carona);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTxtEnderecoOrigem = (TextView)findViewById(R.id.txtEnderecoOrigem);
        mTxtCidadeOrigem = (TextView)findViewById(R.id.txtCidadeOrigem);
        mTxtBairroOrigem = (TextView)findViewById(R.id.txtBairroOrigem);
        mTxtNumeroOrigem = (TextView)findViewById(R.id.txtNumeroOrigem);
        mTxtEnderecoDestino = (TextView)findViewById(R.id.txtEnderecoDestino);
        mTxtCidadeDestino = (TextView)findViewById(R.id.txtCidadeDestino);
        mTxtBairroDestino = (TextView)findViewById(R.id.txtBairroDestino);
        mTxtNumeroDestino = (TextView)findViewById(R.id.txtNumeroDestino);

        Intent it = getIntent();
        Carona carona = (Carona)it.getSerializableExtra("carona_selecionada");

        if(carona != null){
          /* mTxtEnderecoOrigem.setText(carona.endereco);
           mTxtCidadeOrigem.setText(carona.cidade);
           mTxtBairroOrigem.setText(carona.bairro);
           mTxtNumeroOrigem.setText(carona.numero);
           mTxtEnderecoDestino.setText(carona.enderecoDestino);
           mTxtCidadeDestino.setText(carona.cidadeDestino);
           mTxtBairroDestino.setText(carona.bairroDestino);*/
//           mTxtNumeroDestino.setText(carona.numeroDestino);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
