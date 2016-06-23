package com.example.yurim.dormtakki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView listview;
    private String[] items ={"개인 정보 변경"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_setting);

            ArrayAdapter<String> Adapter;
            Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);

            listview =(ListView)findViewById(R.id.settingmenulist);
            listview.setAdapter(Adapter);
            listview.setOnItemClickListener(this);
           }

    @Override
    public void onItemClick(AdapterView<?> adapterView,View view,int i,long l){
        String list=items[i];
        Intent intent =new Intent(SettingActivity.this,LogoutActivity.class);
        Bundle bundle =getIntent().getExtras();
        String text = bundle.getString("name");
        intent.putExtra("name",text);
        intent.putExtra("arr_text",list);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    }

