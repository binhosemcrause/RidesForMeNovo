package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ridesforme.ridesforme.adapter.CaronaAdapter;
import com.ridesforme.ridesforme.basicas.Carona;
import com.ridesforme.ridesforme.repositorios.RepositorioCarona;

import java.util.List;

public class PesquisarCaronaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ListView mListaCarona;
    private Button mBotaoFiltrarCarona;
    private static final int REQUEST_CODE = 1;
    private RepositorioCarona caronaDAO = RepositorioCarona.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_carona);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListaCarona = (ListView)findViewById(R.id.listCarona);
        mBotaoFiltrarCarona = (Button)findViewById(R.id.btnFiltrarCarona);
        mBotaoFiltrarCarona.setOnClickListener(this);
        Intent it = getIntent();
        Carona carona = (Carona)it.getSerializableExtra("carona_filtrada");
        if(carona!=null){
            List<Carona> caronas = caronaDAO.loadCaronas(carona.endereco, carona.enderecoDestino);
            CaronaAdapter adapter = new CaronaAdapter(getApplicationContext(), caronas);
            mListaCarona.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{

            Intent intent = getIntent();
            //TRATAR ERRO -------------------------------------------
           List<Carona> caronas = (List<Carona>)intent.getSerializableExtra("listaCaronas");
            //tratar erroo-------------------------------------------------
            CaronaAdapter adapter = new CaronaAdapter(getApplicationContext(), caronas);
            mListaCarona.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        
        mListaCarona.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, FilterCaronaActivity.class);
        startActivity(intent);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Carona carona = (Carona)parent.getItemAtPosition(position);
        Intent intent = new Intent(this, DetalhePesquisaCaronaActivity.class);
        intent.putExtra("carona_selecionada", carona);
        startActivity(intent);
    }
}
