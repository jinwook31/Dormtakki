package com.example.yurim.dormtakki;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;
import java.util.ArrayList;
import java.util.List;



public class LoginActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            final DBManager dbManager = new DBManager(getApplicationContext(), "MY.db", null, 1);


            //if(dbManager.check()==0){
                setContentView(R.layout.activity_login);
                // DB에 저장 될 속성을 입력받는다
                final EditText studentN = (EditText) findViewById(R.id.StudentNum);
                final EditText phoneN = (EditText) findViewById(R.id.phoneNum);



                // Insert
                Button loginbtn = (Button) findViewById(R.id.loginButton);
                loginbtn.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String id = studentN.getText().toString();
                        String tel = phoneN.getText().toString();
                        dbManager.insert("insert into MY_LIST values(null, '" + id + "', " + tel + ");");
                        Intent intent = new Intent(LoginActivity.this,Dorm1Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("name",id);
                        startActivity(intent);
                        finish();

                    }
                });

          //  } else{ Intent intent =new Intent(LoginActivity.this,Dorm1Activity.class);
          //  startActivity(intent);  finish();}
           }
    }