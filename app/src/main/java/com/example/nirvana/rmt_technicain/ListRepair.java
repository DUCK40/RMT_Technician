package com.example.nirvana.rmt_technicain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListRepair extends AppCompatActivity {

    private ArrayList<ClassListItems> itemArrayList;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private ListView listView; // ListView
    private boolean success = false; // boolean


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_list_repair );

        listView = (ListView) findViewById(R.id.listView); //ListView Declaration
        itemArrayList = new ArrayList<ClassListItems>(); // Arraylist Initialization

        // Calling Async Task
        SyncData orderData = new SyncData();
        orderData.execute("");
    }

    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(ListRepair.this, "Synchronising",
                    "ListView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try {
                Connection conn = ConnectDb.getConnection ();
                if (conn == null) {
                    success = false;
                } else {
                    // Change below query according to your own database.
                    String query = "SELECT * FROM `user`";

                    //อย่าลืม ใสparameter ค่าstatus และ user id ของช่าง
                    String sql ="SELECT odr.order_id,cus.ct_name, cus.ct_lastname ,cus.ct_phone ,odr.order_landmarks FROM `order` as odr , ex_technician as th,ex_customer as cus \n" +
                            " WHERE odr.order_status = 2 and odr.order_user_id = "+Login.IdTechnician+" \n" +
                            " GROUP by odr.order_id ;";

                    String sql1 = "SELECT odr.order_id,cus.ct_name, cus.ct_lastname ,cus.ct_phone ,odr.order_landmarks ,odr.order_lati,odr.order_longti \n" +
                            " FROM `order` as odr , ex_technician as th,ex_customer as cus \n" +
                            " WHERE odr.order_status = 2 and cus.ct_id = odr.order_cusid and odr.order_user_id =th.tn_id and odr.order_user_id = "+Login.IdTechnician+"\n" +
                            " GROUP by odr.order_id ;";



                    String a= "";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql1);
                    List rowValues1 = new ArrayList ();

                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next()) {
                            try {
                                rowValues1.add ( rs.getString ( "ct_name" ) );

//                                itemArrayList.add(new ClassListItems(rs.getString("ct_name"),rs.getString("ct_lastname"),rs.getString("ct_phone"), rs.getString("order_landmarks")));
                                itemArrayList.add(new ClassListItems(rs.getInt ("order_id"),rs.getString("ct_name"),rs.getString("ct_lastname"), rs.getString("ct_phone"), rs.getString("order_landmarks"), rs.getString("order_landmarks"),rs.getDouble ("order_lati"),rs.getDouble ("order_longti"),rs.getInt ("order_id")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println ("SHOWWWWWWW"+rowValues1.size ());
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter ();
                e.printStackTrace(new PrintWriter (writer));
                System.out.println ("SHOWROR"+writer.toString ());
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my ListView
        {
            progress.dismiss();
            Toast.makeText(ListRepair.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, ListRepair.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                    listView.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = String.valueOf ( itemArrayList.get(position).getName () );
                            String idc = String.valueOf ( itemArrayList.get(position).getPhone () );
                            String landmark = String.valueOf ( itemArrayList.get(position).getLandmark () );

                            Double lati = itemArrayList.get ( position ).getLati ();
                            Double longti = itemArrayList.get ( position ).getLongti ();
                            int idOrder = itemArrayList.get ( position ).getIdOrder  ();


                            System.out.println ("dasdasdsaaaa");
                            //คลิกListView แล้วเปลี่ยนหน้า
                            Intent intent = new Intent(ListRepair.this, Confirm_Repair.class);
                            intent.putExtra("name", item);
                            intent.putExtra("tel", idc);
                            intent.putExtra("lati", lati);
                            intent.putExtra("longti", longti);
                            intent.putExtra("landmark", landmark);
                            intent.putExtra("idOrder", idOrder);


                            startActivity(intent);
//                            Toast.makeText ( getApplicationContext (), "ทำการบันทึกข้อมูลเรียบร้อย"+item+" "+idc, Toast.LENGTH_SHORT ).show ();

                        }
                    } );
                } catch (Exception ex) {

                }

            }
        }
    }


    public class MyAppAdapter extends BaseAdapter         //has a class viewholder which holds
    {
        public class ViewHolder {
            TextView textName;
            ImageView imageView;
            TextView textTel;
            TextView textLandMark;
        }

        public List<ClassListItems> parkingList;

        public Context context;
        ArrayList<ClassListItems> arraylist;

        private MyAppAdapter(List<ClassListItems> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<ClassListItems>();
            arraylist.addAll(parkingList);
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) // inflating the layout and initializing widgets
        {

            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_content, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textName = (TextView) rowView.findViewById(R.id.textName);
                viewHolder.textTel = (TextView) rowView.findViewById(R.id.texttel);
                viewHolder.textLandMark = (TextView) rowView.findViewById(R.id.textLandMark);
// viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.textName.setText(parkingList.get(position).getName() + "");
            viewHolder.textTel.setText(parkingList.get(position).getPhone () + "");
            viewHolder.textLandMark.setText(parkingList.get(position).getLandmark () + "");
//            Picasso.with(context).load(parkingList.get(position).getImg()).into(viewHolder.imageView);

            return rowView;
        }
    }

}
