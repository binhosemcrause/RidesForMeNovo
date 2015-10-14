package com.ridesforme.ridesforme;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ridesforme.ridesforme.adapter.CaronaAdapter;
import com.ridesforme.ridesforme.basicas.Carona;
import com.ridesforme.ridesforme.repositorios.RepositorioCarona;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

public class PesquisarCaronaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mListaCarona;
    private Button mBotaoFiltrarCarona;
    private static final int REQUEST_CODE = 1;
    private RepositorioCarona caronaDAO = RepositorioCarona.getSingleton();



    ArrayList<String> Caronas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_carona);

        //LOGICA MARCOS

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListaCarona = (ListView) findViewById(R.id.listCarona);
        mBotaoFiltrarCarona = (Button) findViewById(R.id.btnFiltrarCarona);
        mBotaoFiltrarCarona.setOnClickListener(this);
        Intent it = getIntent();
        Carona carona = (Carona) it.getSerializableExtra("carona_filtrada");
        if (carona != null) {
            List<Carona> caronas = caronaDAO.loadCaronas(carona.RuaOrigem, carona.RuaDestino);
            CaronaAdapter adapter = new CaronaAdapter(getApplicationContext(), caronas);
            mListaCarona.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Intent intent = getIntent();
            List<Carona> caronas = (List<Carona>) intent.getSerializableExtra("listaCaronas");
            CaronaAdapter adapter = new CaronaAdapter(getApplicationContext(), caronas);
            //mListaCarona.setAdapter(adapter);
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
        Carona carona = (Carona) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, DetalhePesquisaCaronaActivity.class);
        intent.putExtra("carona_selecionada", carona);
        startActivity(intent);
    }

    /**
     * Created by Robson on 10/10/2015.
     */
    class ListaCaronas extends AsyncTask<Void, Void, Carona> {

        @Override
        protected Carona doInBackground(Void... params) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("").build();

            return null;
        }
    }
}
