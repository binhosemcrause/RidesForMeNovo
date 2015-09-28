package com.ridesforme.ridesforme.fragments;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ridesforme.ridesforme.CaronaPasso1Activity;
import com.ridesforme.ridesforme.MainActivity;
import com.ridesforme.ridesforme.R;
import com.ridesforme.ridesforme.UserSessionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MapHomeFragment extends Fragment implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener,OnMarkerDragListener {
    UserSessionManager session;
    private GoogleMap map;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private TextView endereco;
    private String pEndereco;
    private String pNumero;
    private String pCidade;

    private OnFragmentInteractionListener mListener;

    public MapHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_map_home,container,false);

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        endereco = (TextView)v.findViewById(R.id.endereco);


        session = new UserSessionManager(getActivity());
        if (session.checkLogin());

        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(UserSessionManager.KEY_NAME);
        /*TextView txtLogin = (TextView)v.findViewById(R.id.lbllogin);
        txtLogin.setText(Html.fromHtml("Name: <b>" + name + "</b>"));*/

        //GOOGLE MAPS


        SupportMapFragment m = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));
        m.getMapAsync(this);



        Button btnCarona = (Button)v.findViewById(R.id.btnCarona);
        btnCarona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("endereco", pEndereco);
                b.putString("numero", pNumero);
                b.putString("cidade", pCidade);
                Intent intent = new Intent(getActivity(), CaronaPasso1Activity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        return v;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.setMyLocationEnabled(true);
        map.setOnMarkerDragListener(this);

        LatLng myLocation;
        HashMap<String, String> location = session.getLastLocation();

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null && location.get("lat") == null) {
            myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
            Marker perth = map.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            session.createLastLocation(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));
            Log.i("1", "LOCALIZACAO ENCONTRADA / SHARED PREFERENCES NULL");

            try {
                geocoding(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (mLastLocation != null && location.get("lat") != null) {
            myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
            Marker perth = map.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            session.createLastLocation(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));
            Log.i("1", "LOCALIZACAO ENCONTRADA / SHARED PREFERENCES PREENCHIDO");

            try {
                geocoding(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (mLastLocation == null && location.get("lat") != null) {
            myLocation = new LatLng(Double.parseDouble(location.get("lat")), Double.parseDouble(location.get("lng")));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
            Marker perth = map.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            Log.i("1", "LOCALIZACAO NÃO ENCONTRADA / SHARED PREFERENCES PREENCHIDO");

            try {
                geocoding(Double.parseDouble(location.get("lat")), Double.parseDouble(location.get("lng")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            myLocation = new LatLng(-14.2392976, -53.1805017);
            Toast.makeText(getActivity(), "Localização não encontrada", Toast.LENGTH_LONG).show();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 2));
            Marker perth = map.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            Log.i("1", "LOCALIZACAO NÃO ENCONTRADA / SHARED PREFERENCES NULL");
        }
    }

    public void geocoding (Double lat, Double lng) throws IOException {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
        endereco.setText(addresses.get(0).getThoroughfare().toString() +","+ addresses.get(0).getFeatureName());
        pEndereco = addresses.get(0).getThoroughfare().toString();
        pNumero =  addresses.get(0).getFeatureName();
        pCidade =  addresses.get(0).getLocality();
        Log.i("tudo", addresses.get(0).toString());
        Log.i("endereco", addresses.get(0).getAddressLine(0).toString());
        Log.i("postalcode",addresses.get(0).getPostalCode().toString());
        Log.i("featuedabress",addresses.get(0).getFeatureName().toString());
        Log.i("locality",addresses.get(0).getLocality().toString());
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onMapReady(GoogleMap map2) {
        this.map = map2;

    }



    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(getActivity(), "movido", Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            endereco.setText(addresses.get(0).getAddressLine(0).toString());
            pEndereco = addresses.get(0).getThoroughfare().toString();
            pNumero =  addresses.get(0).getFeatureName();
            pCidade =  addresses.get(0).getLocality();
            Log.i("endereco", addresses.get(0).getAddressLine(0).toString());
            Toast.makeText(getActivity(), "movido", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
