package com.example.nirvana.rmt_technicain;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {

    static Connection conn;

    //private static final String DB_URL = "jdbc:mysql://DATABASE_IP/DATABASE_NAME";
    private static final String DB_URL = "jdbc:mysql://103.58.148.189/oktsxyz_duck"; //"jdbc:mysql://DATABASE_IP/DATABASE_NAME";
    private static final String USER = "oktsxyz_duck";
    private static final String PASS = "duck123";






    @SuppressLint("NewApi")
    public  static Connection getConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder ()
                .permitAll ().build ();
        StrictMode.setThreadPolicy ( policy );


        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL+"?useUnicode=true&characterEncoding=utf-8", USER, PASS);

//            conn = DriverManager.getConnection(DB_URL,USER, PASS); //Connection Obj

            if (conn != null){
                System.out.println ("00000OK"+conn.toString ());

            }else if(conn == null){
                System.out.println ("11111OK"+conn.toString ());
            }


        } catch (SQLException se) {
            Log.e ( "ERRO1", se.getMessage () );
        } catch (ClassNotFoundException e) {
            Log.e ( "ERRO2", e.getMessage () );
        } catch (Exception e) {
            Log.e ( "ERRO3", e.getMessage () );
        }

        return conn;
    }
}
