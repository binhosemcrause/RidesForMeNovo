package com.ridesforme.ridesforme;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import com.ridesforme.ridesforme.basicas.Carona;
import com.ridesforme.ridesforme.directions.DirectionsJSONParser;
import com.ridesforme.ridesforme.repositorios.RepositorioCarona;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CaronaPasso2Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private GoogleMap map;
    public String EstadoOrigem;
    public String CidadeOrigem;
    public String BairroOrigem;
    public String RuaOrigem;
    public String EstadoDestino;
    public String CidadeDestino;
    public String BairroDestino;
    public String RuaDestino;
    public String numeroOrigem;
    public String numeroDestino;


    LatLng ltZoom;
    ArrayList<LatLng> markerPoints;
    List<Carona> mCaronas = new ArrayList<>();
    Button mBotaoSolicitarCarona;
    RepositorioCarona repCarona = RepositorioCarona.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carona_passo2);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();


        EstadoOrigem = params.getString("EstadoOrigem");
        CidadeOrigem = params.getString("CidadeOrigem");
        BairroOrigem = params.getString("BairroOrigem");
        RuaOrigem = params.getString("RuaOrigem");
        numeroOrigem = params.getString("numeroOrigem");

        EstadoDestino = params.getString("EstadoDestino");
        CidadeDestino = params.getString("CidadeDestino");
        BairroDestino = params.getString("BairroDestino");
        RuaDestino = params.getString("RuaDestino");
        numeroDestino = params.getString("numeroDestino");


        mBotaoSolicitarCarona = (Button)findViewById(R.id.btnSolicitarCarona);
        mBotaoSolicitarCarona.setOnClickListener(this);

        //GOOGGLE MAPS

        markerPoints = new ArrayList<LatLng>();



        String url = getDirectionsUrl(RuaOrigem+","+numeroOrigem+","+BairroOrigem+","+CidadeOrigem, RuaDestino+","+numeroDestino+","+BairroOrigem+","+CidadeDestino);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);


    }

    private String getDirectionsUrl(String origin,String dest){

        // Origin of route
        String str_origin = "origin="+origin;

        // Destination of route
        String str_dest = "destination="+dest;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        String api = "&key=AIzaSyANiGw1pshxnNfrkE4e3W_XMoQi9am0ANg";

        // Output format
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+api;
        url = url.replaceAll("\\s+","");

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while dowl", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        Carona carona = new Carona();
        carona.RuaOrigem = RuaOrigem;
        carona.CidadeOrigem = CidadeOrigem;
        carona.BairroOrigem = BairroOrigem;
        carona.RuaDestino = RuaDestino;
        carona.CidadeDestino = CidadeDestino;
        carona.BairroDestino = BairroDestino;

        repCarona.cadastrarCarona(carona);
        mCaronas = repCarona.loadCaronas();
        Intent intent = new Intent(this, PesquisarCaronaActivity.class);
        intent.putExtra("listaCaronas", (Serializable)mCaronas);
        startActivity(intent);
        Log.i("Carona Cadastrada", mCaronas.toString()+ mCaronas.size());

    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();

                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            try {
                map.addPolyline(lineOptions);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 15));
                Marker perth = map.addMarker(new MarkerOptions()
                        .position(points.get(0))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                Marker perth2 = map.addMarker(new MarkerOptions()
                        .position(points.get(points.size()-1))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error Localização", Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carona_passo2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.setMyLocationEnabled(true);

    }
}
