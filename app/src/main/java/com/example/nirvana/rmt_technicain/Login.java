package com.example.nirvana.rmt_technicain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    EditText editLogin;
    EditText editPass;
    Button button;

    public static int IdTechnician;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        init ();
        checkUser ();

    }
    void init(){
         editLogin = (EditText) findViewById ( R.id.login );
         editPass = (EditText) findViewById ( R.id.password );
         button = (Button) findViewById ( R.id.button );
    }

    void checkUser(){
        button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                try {
                    Connection conn = ConnectDb.getConnection (); //Connection Object
                    PreparedStatement pre = null;
                    Statement stmt = null;

                    if (editLogin.getText ().length () == 0) {
                        Toast.makeText ( getBaseContext (), "กรุณากรอก Username", Toast.LENGTH_LONG ).show ();
                    } else if (editPass.getText ().length () == 0) {
                        Toast.makeText ( getBaseContext (), "กรุณากรอก Password", Toast.LENGTH_LONG ).show ();
                    } else {


                        stmt = conn.createStatement ();
                        String SQL = "SELECT * FROM `ex_technician` \n" +
                                "WHERE tn_userid = '" + editLogin.getText ().toString () + "' AND  tn_pass = '" + editPass.getText ().toString () + "' ";   // WHERE Name = '" + user + "'


                        Statement mStmt = conn.createStatement ();
                        ResultSet rs = mStmt.executeQuery ( SQL );


                        List rowValues1 = new ArrayList ();


                        while (rs.next ()) {
                            IdTechnician = rs.getInt ( "tn_id" );
                            rowValues1.add ( rs.getInt ( "tn_id" ) );
//                            idCus = rs.getString ( "ct_id" );
//                            oderRepair.setIdCus ( rs.getString ( "ct_id" ) );
                        }

                        if (rowValues1.size () < 1) {

                            Toast.makeText ( getApplicationContext (), "คุณยังไม่ได้สมัครการใช้งาน", Toast.LENGTH_SHORT ).show ();

                        } else if (rowValues1.size () == 1) {

                            startActivity ( new Intent ( Login.this, MainActivity.class ) );

                        }


                    }
                } catch (Exception e) {

                }
            }
        } );

    }
}
