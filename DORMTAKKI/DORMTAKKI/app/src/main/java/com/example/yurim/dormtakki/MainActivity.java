package com.example.yurim.dormtakki;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;

public class MainActivity extends Activity{

    private String selectUniv = null;
    private String selectBuild = null;
    Button continuebtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DBManager dbManager = new DBManager(getApplicationContext(), "MY.db", null, 1);



        if(dbManager.check()==0){
            startActivity(new Intent(this, SplashActivity.class));
        setContentView(R.layout.activity_main);
        //로딩먼저 일어나도록


        continuebtn = (Button) findViewById(R.id.continuebtn);
        continuebtn.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
             Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //attach the listener to the spinner
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        //Dynamically generate a spinner data
        createSpinnerDropDown();
        } else{ Intent intent =new Intent(MainActivity.this,Dorm1Activity.class);
            startActivity(intent);
            finish();
        }
    }



    //Add animals into spinner dynamically
    private void createSpinnerDropDown() {

        //get reference to the spinner from the XML layout
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        //Array list of animals to display in the spinner
        List<String> list = new ArrayList<String>();
        list.add("13동");
        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        spinner.setAdapter(dataAdapter);
        //attach the listener to the spinner
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String selectedItem = parent.getItemAtPosition(pos).toString();

            //check which spinner triggered the listener
            switch (parent.getId()) {
                //학교
                case R.id.spinner:
                    if(selectUniv != null){
                        Toast.makeText(parent.getContext(), selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    selectUniv = selectedItem;
                    break;
              //동수
                case R.id.spinner1:
                    if(selectBuild != null){
                        Toast.makeText(parent.getContext(),selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    selectBuild = selectedItem;
                    break;
            }


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }
}
