package com.example.nirvana.rmt_technicain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    TextView txtname;
    TextView txtlastname;
    TextView txttel;

    Button btnedt;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_profile );
        init ();
        findDataUser();

//        Login.IdTechnician;
    }

    void init(){
        txtname =(TextView)findViewById ( R.id.txtname );
        txtlastname =(TextView)findViewById ( R.id.txtlastname );
        txttel =(TextView)findViewById ( R.id.txttel );

        btnedt = (Button)findViewById ( R.id.btnedt );
        btnback = (Button)findViewById ( R.id.btnback );

        btnedt.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity ( new Intent ( Profile.this, EditProfile.class ) );

            }
        } );

        btnback.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity ( new Intent ( Profile.this, MainActivity.class ) );

            }
        } );


    }


    void findDataUser(){

        try {
            Connection conn = ConnectDb.getConnection (); //Connection Object

            Statement mStmt1 = conn.createStatement ();

//            Login.idCus;

            String sql ="SELECT tn_id,tn_name,tn_lastname,tn_userid,tn_pass,tn_phone \n" +
                    "FROM `ex_technician` WHERE tn_id = '"+Login.IdTechnician+"'";
            ResultSet rs1 = mStmt1.executeQuery ( sql );

            List rowValues1 = new ArrayList ();

            if (rs1.next ()){
                txtname.setText (  rs1.getString ( "tn_name" ));
                txtlastname.setText ( rs1.getString ( "tn_lastname" ) );
                txttel.setText ( rs1.getString ( "tn_phone" ) );

            }

        }catch (Exception e){

        }
    }
}
