package com.example.yurim.dormtakki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LogoutActivity extends Activity{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final DBManager dbManager = new DBManager(getApplicationContext(), "MY.db", null, 1);
             setContentView(R.layout.activity_logout);

            final EditText schoolN = (EditText) findViewById(R.id.new1item);
            final EditText dongN = (EditText) findViewById(R.id.new2item);
            final EditText snumN = (EditText) findViewById(R.id.new3item);
            final EditText telN = (EditText) findViewById(R.id.new4item);


            // Insert
            Button logoutbtn = (Button) findViewById(R.id.logoutButton);
            logoutbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String id = snumN.getText().toString();
                    String tel = telN.getText().toString();
                    Bundle bundle =getIntent().getExtras();
                    String text = bundle.getString("name");
                    dbManager.delete("delete from MY_LIST where name = '" + text + "';");
                    dbManager.insert("insert into MY_LIST values(null, '" + id + "', " + tel + ");");
                    Intent intent = new Intent(LogoutActivity.this,Dorm1Activity.class);
                    intent.putExtra("name",id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            });

           }


    }

