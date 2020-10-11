package com.example.nirvana.rmt_technicain;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Confirm_Repair extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    static final int REQUREST_LOCATION = 1;
    LocationManager locationManager;
    private GoogleMap mMap;
  public static   Double sendlati;
    public static   Double sendlongti;

    String sendname;
    String sendtel;
    String sendmark;

    TextView txtname;
    TextView txttel;
    TextView txtmark;
    TextView namerubberfont;
    TextView namerubberback;


    int getIdOrder = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_confirm__repair );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder ().permitAll ().build ();
        StrictMode.setThreadPolicy ( policy );
        init ();

        Bundle bundle = getIntent().getExtras();

        String name = bundle.getString("name");
        String tel = bundle.getString("tel");
        Double lati = bundle.getDouble("lati");
        Double longti = bundle.getDouble ("longti");
        String landmark = bundle.getString("landmark");
        int idOrder = bundle.getInt("idOrder");
        getIdOrder =idOrder;

        sendlati = lati;
        sendlongti=longti;

        sendname =name;
        sendtel =tel;
        sendmark = landmark;

        txtname.setText ( sendname );
        txttel.setText (sendtel );
        txtmark.setText ( sendmark );
        findOrderDetail();

        locationManager = (LocationManager) getSystemService ( Context.LOCATION_SERVICE );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );






    }
    void findOrderDetail(){
        try {
            Connection conn = ConnectDb.getConnection ();

            String sql = "SELECT detail_order_id, detail_order_price FROM `detail_order` WHERE order_id = '"+getIdOrder+"'  \n" +
                    "ORDER BY `detail_order`.`detail_order_id` ASC LIMIT 1";

            String sql1 ="SELECT detail_order_id, detail_order_price FROM `detail_order` WHERE order_id = '"+getIdOrder+"'  \n" +
                    "ORDER BY `detail_order`.`detail_order_id` DESC LIMIT 1";

            String a= "";
            Statement stmt = conn.createStatement();
            Statement stmt1 = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSet rs1 = stmt1.executeQuery(sql1);
            String priceFont = null;
            String priceBack = null;

            if (rs.next ()){
                priceFont =rs.getString ( "detail_order_price" );
            }
            if (rs1.next ()){
                priceBack =rs.getString ( "detail_order_price" );

            }

            if (priceFont.length () > 1){
                namerubberfont.setText ( priceFont );
            }else {
                namerubberfont.setText ( "-" );
            }
            if (priceBack.length () > 1){
                namerubberback.setText ( priceBack );
            }else {
                namerubberback.setText ( "-" );
            }




        }catch (Exception e){

        }
    }
    void init(){
        txtname =(TextView)findViewById ( R.id.txtname1 );
        txttel =(TextView)findViewById ( R.id.txttel1 );
        txtmark =(TextView)findViewById ( R.id.txtmark1 );
        namerubberfont = (TextView)findViewById ( R.id.namerubberfont );
        namerubberback = (TextView)findViewById ( R.id.namerubberback );
        Button btn = (Button)findViewById ( R.id.button1 );
        Button btn1 = (Button)findViewById ( R.id.button2 );

        btn1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity ( new Intent ( Confirm_Repair.this, ListRepair.class ) );

            }
        } );


        btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                updateStatus();
//                Intent intent = new Intent(Confirm_Repair.this, Detail_Repair.class);
//                intent.putExtra("name", sendname);
//                intent.putExtra("tel", sendtel);
//                intent.putExtra("lati", sendlati);
//                intent.putExtra("longti", sendlongti);
//                intent.putExtra("landmark", sendmark);
//                intent.putExtra("rubberF",namerubberfont.getText ().toString ());
//                intent.putExtra("rubberB",namerubberback.getText ().toString ());
//
//
//                startActivity(intent);
                startActivity ( new Intent ( Confirm_Repair.this, MainActivity.class ) );
                Toast.makeText ( getApplicationContext (), "ทำการซ่อมเสร็จสิ้น", Toast.LENGTH_SHORT ).show ();

            }
        } );


//        namerubberfont.setText ( "1234" );



    }

    void updateStatus(){
        try {
            Connection conn = ConnectDb.getConnection (); //Connection Object


            Statement mStmt1 = conn.createStatement ();
//

            String sql = "UPDATE `order` SET `order_status` = '3' WHERE `order`.`order_id` = '"+getIdOrder+"';";
            System.out.println ("opopopopasdwa");



            mStmt1.executeUpdate ( sql );
            ResultSet rs=mStmt1.getGeneratedKeys();

            List rowValues1 = new ArrayList ();
            int result=0;
            if (rs.next ()){
                result=rs.getInt ( 1 );
                rowValues1.add ( rs.getInt(1) );
            }


        }catch (Exception e){

        }

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        System.out.println ( "Helloll onConnected" );

    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println ( "Helloll onConnectionSuspended" );

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println ( "Helloll onConnectionFailed" );

    }
    @Override
    public void onClick(View view) {

    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION ) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_COARSE_LOCATION )
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions ( this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUREST_LOCATION );
        } else {
            Location location = locationManager.getLastKnownLocation ( LocationManager.NETWORK_PROVIDER );

            if (location != null) {
                double latti = location.getLatitude ();
                double longti = location.getLongitude ();


//

            }



        }



    }
}
