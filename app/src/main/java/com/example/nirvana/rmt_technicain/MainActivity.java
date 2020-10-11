package com.example.nirvana.rmt_technicain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1;
//    Button btn2;
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        System.out.println ("SHOWWWWWW222"+Login.IdTechnician);
        init ();
        try {
            Connection conn = ConnectDb.getConnection ();
            if (conn != null){
                System.out.println ("Coonected succes");
            }else {
                System.out.println ("Connect ERREO");
            }
        }catch (Exception e){

        }
    }

    void init(){
        btn1 = (Button)findViewById ( R.id.button1 );
//        btn2 = (Button)findViewById ( R.id.button2);
        btn3 = (Button)findViewById ( R.id.button3);
        btn1.setOnClickListener ( this );
//        btn2.setOnClickListener ( this );
        btn3.setOnClickListener ( this );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.button1:
                startActivity ( new Intent ( MainActivity.this, ListRepair.class ) );

                break;
//            case R.id.button2:
//                startActivity ( new Intent ( MainActivity.this, Detail_Repair.class ) );
//
//                break;
            case R.id.button3:
                startActivity ( new Intent ( MainActivity.this, Profile.class ) );

                break;
        }
    }
}
