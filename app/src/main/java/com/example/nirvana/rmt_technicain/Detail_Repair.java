package com.example.nirvana.rmt_technicain;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Detail_Repair extends AppCompatActivity implements  OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    static final int REQUREST_LOCATION = 1;
    LocationManager locationManager;
    private GoogleMap mMap;
    Double sendlati;
    Double sendlongti;
    String getname;
    String gettal;
    String getlandmark;
    String getrubberf;
    String getrubberb;


    TextView txtname;
    TextView txttel;
    TextView txtmark;
    TextView namerubberfont;
    TextView namerubberback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_detail__repair );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder ().permitAll ().build ();
        StrictMode.setThreadPolicy ( policy );
        init ();


        locationManager = (LocationManager) getSystemService ( Context.LOCATION_SERVICE );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );
        Bundle bundle = getIntent().getExtras();
try {


    String name = bundle.getString("name");
    String tel = bundle.getString("tel");

    String landmark = bundle.getString("landmark");
    String rubberF = bundle.getString ("rubberF");
    String rubberB = bundle.getString ("rubberB");
//    Double lati = bundle.getDouble("lati");
//    Double longti = bundle.getDouble ("longti");

    getname =name;
    gettal=tel;
    getlandmark=landmark;
    getrubberf=rubberF;
    getrubberb=rubberB;



}catch (Exception e){

}

        Double lati = bundle.getDouble("lati");
        Double longti = bundle.getDouble ("longti");
        sendlati=lati;
        sendlongti=longti;


        txtname.setText ( getname );
        txttel.setText ( gettal);
        txtmark.setText ( getlandmark );
        namerubberfont.setText ( getrubberf );
        namerubberback.setText ( getrubberb );




    }
    void init (){

        txtname =(TextView)findViewById ( R.id.txtname1 );
        txttel =(TextView)findViewById ( R.id.txttel1 );
        txtmark =(TextView)findViewById ( R.id.txtmark1 );
        namerubberfont = (TextView)findViewById ( R.id.namerubberfont );
        namerubberback = (TextView)findViewById ( R.id.namerubberback );
        Button btn = (Button)findViewById ( R.id.btnBack );

        btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity ( new Intent ( Detail_Repair.this, MainActivity.class ) );
            }
        } );

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String tel = bundle.getString("tel");

        String landmark = bundle.getString("landmark");
        String rubberF = bundle.getString ("rubberF");
        String rubberB = bundle.getString ("rubberB");
        Double lati = bundle.getDouble("lati");
        Double longti = bundle.getDouble ("longti");


        sendlati=lati;
        sendlongti=longti;

        getname =name;
        gettal=tel;
        getlandmark=landmark;
        getrubberf=rubberF;
        getrubberb=rubberB;
        super.onStop ();
    }

    @Override
    protected void onPause() {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String tel = bundle.getString("tel");

        String landmark = bundle.getString("landmark");
        String rubberF = bundle.getString ("rubberF");
        String rubberB = bundle.getString ("rubberB");
        Double lati = bundle.getDouble("lati");
        Double longti = bundle.getDouble ("longti");


        sendlati=lati;
        sendlongti=longti;

        getname =name;
        gettal=tel;
        getlandmark=landmark;
        getrubberf=rubberF;
        getrubberb=rubberB;
        super.onPause ();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        String country =null;

        System.out.println ( "Helloll" );

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng ( sendlati, sendlongti );
        mMap.addMarker ( new MarkerOptions ().position ( sydney ).title ( "Marker in Sydney" ) );

        mMap.moveCamera ( CameraUpdateFactory.newLatLng ( sydney ) );
        mMap.animateCamera ( CameraUpdateFactory.zoomTo ( 15.0f ) );

    }
}
