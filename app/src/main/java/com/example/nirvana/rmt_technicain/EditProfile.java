package com.example.nirvana.rmt_technicain;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EditProfile extends AppCompatActivity {

    EditText edtname;
    EditText edtlastname;
    EditText edtphone;
    EditText edtpasswordBefore;
    EditText edtpasswordAfter;
    EditText edtpasswordAfterAgin;

    Button btnOk;
    Button btnCancel;

    String passBefor;
    String passAfter;
    String passAfterAgin;

    Boolean statusPassAfter =false;
    Boolean statusPassBefor=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_profile );

        init ();
        findDataUser();

    }

    void init(){
        edtname =(EditText)findViewById ( R.id.edtname );
        edtlastname = (EditText)findViewById ( R.id.edtlastname );
        edtphone = (EditText)findViewById ( R.id.edtphone );
        edtpasswordBefore = (EditText)findViewById ( R.id.edtpasswordBefore );
        edtpasswordAfter = (EditText)findViewById ( R.id.edtpasswordAfter );
        edtpasswordAfterAgin = (EditText)findViewById ( R.id.edtpasswordAfterAgin );


        btnOk =(Button)findViewById ( R.id.btnok ) ;
        btnCancel=(Button)findViewById ( R.id.btncancle ) ;

        btnOk.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                checkDataUser ();
            }
        } );

    }

    void checkDataUser(){
        if (edtpasswordBefore.length () >= 1) {
            String checkpassBefor;
            checkpassBefor=edtpasswordBefore.getText ().toString ();

            System.out.println ("EDTTTTTT"+checkpassBefor);

            //ตรวจสอบรหัสผ่านเก่าถุกต้องหรือไม่
            if (checkpassBefor.equals ( passBefor ) ){
//                statusPassBefor =true;
                if (edtpasswordAfter.length () >=1){
                    statusPassBefor =true;
                }else {
                    statusPassBefor =false;
                    Toast.makeText ( getApplicationContext (), "กรุณากรอกรหัสใหม่ให้ครบถ้วน", Toast.LENGTH_SHORT ).show ();

                }
                if (edtpasswordAfterAgin.length () >=1){
                    statusPassBefor =true;
                }else {
                    statusPassBefor =false;
                    Toast.makeText ( getApplicationContext (), "กรุณากรอกรหัสใหม่ให้ครบถ้วน", Toast.LENGTH_SHORT ).show ();

                }
            }else {
                //แสดง Toast
                Toast.makeText ( getApplicationContext (), "รหัสเก่าของคุณไม่ถูกต้อง", Toast.LENGTH_SHORT ).show ();

            }
        }else if(edtpasswordBefore.length () <= 0){
            statusPassBefor=false;
        }

        String getpassAfter;
        String getpassAfterAgin;
        getpassAfter= edtpasswordAfter.getText ().toString ();
        getpassAfterAgin=edtpasswordAfterAgin.getText ().toString ();

        if (getpassAfter.equals ( getpassAfterAgin )){
            statusPassAfter=true;
        }else {
            statusPassAfter=false;
            Toast.makeText ( getApplicationContext (), "รหัสใหม่ของคุณไม่ตรง", Toast.LENGTH_SHORT ).show ();

        }

        if (statusPassAfter==true && statusPassBefor ==true){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(EditProfile.this);

            builder.setMessage("คุณต้องการแก้ไชข้อมูลใช่หรือไม่");
            builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    EditDataPass();
//                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        //ตรวจสอบว่า มีการกรอกข้อมูลรหัสผ่านไหม
        if (edtpasswordBefore.length ()<=0 && edtpasswordAfterAgin.length () <=0 && edtpasswordAfter.length () <=0){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(EditProfile.this);

            builder.setMessage("คุณต้องการแก้ไชข้อมูลใช่หรือไม่");
            builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    EditDataUser();

                }
            });
            builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }


    void EditDataPass(){

        try {
            Connection conn = ConnectDb.getConnection (); //Connection Object

            Statement mStmt1 = conn.createStatement ();

//            Login.idCus;

            String sql ="UPDATE `ex_technician` SET `tn_name`='"+edtname.getText ().toString ()+"',`tn_lastname`='"+edtlastname.getText ().toString ()+"',`tn_pass`='"+edtpasswordAfterAgin.getText ().toString ()+"',`tn_phone`='"+edtphone.getText ().toString ()+"' WHERE tn_id = '"+Login.IdTechnician+"'\n";


            mStmt1.executeUpdate ( sql );
            ResultSet rs=mStmt1.getGeneratedKeys();
            rs.close ();


            mStmt1.close ();
            startActivity ( new Intent ( EditProfile.this, Profile.class ) );


        }catch (Exception e){

        }

    }

    void EditDataUser(){
        try {
            Connection conn = ConnectDb.getConnection (); //Connection Object

            Statement mStmt1 = conn.createStatement ();

//            Login.idCus;

            String sql ="UPDATE `ex_technician` SET `tn_name`='"+edtname.getText ().toString ()+"',`tn_lastname`='"+edtlastname.getText ().toString ()+"',`tn_phone`='"+edtphone.getText ().toString ()+"' WHERE `tn_id` = '"+Login.IdTechnician+"'";
//            "+Login.idCus+"'
            mStmt1.executeUpdate ( sql );
            ResultSet rs=mStmt1.getGeneratedKeys();
            rs.close ();




            startActivity ( new Intent ( EditProfile.this, Profile.class ) );


            mStmt1.close ();

        }catch (Exception e){

        }


    }


    void findDataUser(){

        try {
            Connection conn = ConnectDb.getConnection (); //Connection Object

            Statement mStmt1 = conn.createStatement ();

//            Login.idCus;

            String sql ="SELECT `tn_name`,`tn_lastname`,`tn_userid`,`tn_pass`,`tn_phone` \n" +
                    "FROM `ex_technician` WHERE `tn_id` = '"+Login.IdTechnician+"'";
//            "+Login.idCus+"'
            ResultSet rs1 = mStmt1.executeQuery ( sql );



            if (rs1.next ()){
                passBefor=rs1.getString ( "tn_pass" );
                edtname.setText (  rs1.getString ( "tn_name" ));
                edtlastname.setText ( rs1.getString ( "tn_lastname" ) );
                edtphone.setText ( rs1.getString ( "tn_phone" ) );

            }
            rs1.close ();
            mStmt1.close ();

        }catch (Exception e){

        }
    }


}
