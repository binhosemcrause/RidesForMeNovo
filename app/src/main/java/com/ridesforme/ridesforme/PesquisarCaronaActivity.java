package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ridesforme.ridesforme.adapter.CaronaAdapter;
import com.ridesforme.ridesforme.basicas.Carona;
import com.ridesforme.ridesforme.repositorios.RepositorioCarona;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PesquisarCaronaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mListViewCarona;
    private Button mBotaoFiltrarCarona;
    private static final int REQUEST_CODE = 1;
    private RepositorioCarona caronaDAO = RepositorioCarona.getSingleton();
    private List<Carona> mCaronas;
    private ListarCaronasTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_carona);

        //LOGICA MARCOS

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListViewCarona = (ListView) findViewById(R.id.listCarona);
        mBotaoFiltrarCarona = (Button) findViewById(R.id.btnFiltrarCarona);
        mBotaoFiltrarCarona.setOnClickListener(this);
        mCaronas = new ArrayList<>();
        mTask = new ListarCaronasTask();
        mTask.execute();
        mListViewCarona.setOnItemClickListener(this);

    }

    @Override
    public void onClick (View v){
        Intent intent = new Intent(this, FilterCaronaActivity.class);
        startActivity(intent);

    }

    @Override
    public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
        Carona carona = (Carona) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, DetalhePesquisaCaronaActivity.class);
        intent.putExtra("carona_selecionada", carona);
        startActivity(intent);
    }

    public class ListarCaronasTask extends AsyncTask<Void, Void, List<Carona>> {
        @Override
        protected List<Carona> doInBackground(Void... params) {
            String url;
            OkHttpClient client = new OkHttpClient();
            Request request;

            try {
                url = "http://179.181.41.70:8080/rpg/carona/getAllCarona";
                request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                String jsonStr = response.body().string();

                JSONArray jsonarray = new JSONArray(jsonStr);

                for(int i=0; i<jsonarray.length(); i++){
                    JSONObject caronaJSON  = jsonarray.getJSONObject(i);

                    Integer caronaId = caronaJSON.getInt("CaronaID");
                    Integer usuarioId = caronaJSON.getInt("UsuarioID");
                    String estadoOrigem = caronaJSON.getString("EstadoOrigem");
                    String cidadeOrigem = caronaJSON.getString("CidadeOrigem");
                    String bairroOrigem = caronaJSON.getString("BairroOrigem");
                    String ruaOrigem = caronaJSON.getString("RuaOrigem");
                    String estadoDestino = caronaJSON.getString("EstadoDestino");
                    String cidadeDestino = caronaJSON.getString("CidadeDestino");
                    String bairroDestino = caronaJSON.getString("BairroDestino");
                    String ruaDestino = caronaJSON.getString("RuaDestino");
//                  Integer valor = caronaJSON.getInt("Valor");
                    String descricaoCarona = caronaJSON.getString("DescricaoCarona");
                    Integer tipoVeiculo = caronaJSON.getInt("TipoVeiculo");
                    String descricaoVeiculo = caronaJSON.getString("DescricaoVeiculo");
                    Integer vagas = caronaJSON.getInt("Vagas");
                    Integer tipoTrajeto = caronaJSON.getInt("TipoTrajeto");
                    Integer tipoOferta = caronaJSON.getInt("TipoOferta");
//                  Integer dataHoraSaidaIda = caronaJSON.getInt("DataHoraSaidaIda");
//                  Integer dataHoraSaidaVolta = caronaJSON.getInt("DataHoraSaidaVolta");
                    String diaDaSemana = caronaJSON.getString("DiaDaSemana");
//                  Integer status = caronaJSON.getInt("Status");
//                  Integer classificacao = caronaJSON.getInt("Classificacao");

                    Carona carona = new Carona();

                    carona.setCaronaId(caronaId);
                    carona.setUsuarioID(usuarioId);
                    carona.setEstadoOrigem(estadoOrigem);
                    carona.setCidadeOrigem(cidadeOrigem);
                    carona.setBairroOrigem(bairroOrigem);
                    carona.setEstadoDestino(estadoDestino);
                    carona.setCidadeDestino(cidadeDestino);
                    carona.setBairroDestino(bairroDestino);
//                  carona.setValor(valor.toString());
                    carona.setDescricaoCarona(descricaoCarona);
                    carona.setTipoVeiculo(tipoVeiculo.toString());
                    carona.setDescricaoVeiculo(descricaoVeiculo);
                    carona.setVagas(vagas.toString());
                    carona.setTipoTrajeto(tipoTrajeto.toString());
                    carona.setTipoOferta(tipoOferta.toString());
//                  carona.setDataHoraSaidaIda(new Date(dataHoraSaidaIda));
//                  carona.setDataHoraSaidaVolta(new Date(dataHoraSaidaVolta));
                    carona.setDiaDaSemana(diaDaSemana);
//                  carona.setStatus(status.toString());
//                  carona.setClassificacao(classificacao);
                    carona.setRuaOrigem(ruaOrigem);
                    carona.setRuaDestino(ruaDestino);

                    mCaronas.add(carona);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return mCaronas;
        }

        @Override
        protected void onPostExecute(List<Carona> caronas) {
            super.onPostExecute(caronas);
            mListViewCarona.setAdapter(new CaronaAdapter(PesquisarCaronaActivity.this, mCaronas));

        }
    }
}
