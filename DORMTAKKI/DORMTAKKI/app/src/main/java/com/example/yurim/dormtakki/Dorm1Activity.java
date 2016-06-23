package com.example.yurim.dormtakki;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Dorm1Activity extends ActionBarActivity {
    MainDialog alramDialog;
    Toast mToast;
    Button alrambtn;
    Button waitingbtn;
    Button canclebtn;
    String myJSON;
    private TimerTask mTask;
    private Timer mTimer;
    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "ID";
    private static final String TAG_STATE = "state";
    JSONArray ja = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x00CCFF));
        getSupportActionBar().setCustomView(R.layout.actionbar);
        setContentView(R.layout.activity_dorm1);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //로딩먼저 일어나도록
        alrambtn = (Button) findViewById(R.id.alramButton);
        alrambtn.setOnClickListener(listener);
        waitingbtn = (Button) findViewById(R.id.waitingButton);
        waitingbtn.setOnClickListener(listener);
        canclebtn = (Button) findViewById(R.id.cancleButton);
        canclebtn.setOnClickListener(listener);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //서버 Exception 방지를 위해 사용
        mTask = new TimerTask() {
            @Override
            public void run() {
                getData("http://182.230.243.11/getData.php");
             //   Log.e("ds","d");
            }
        };
        mTimer =new Timer();
        mTimer.schedule(mTask,0000,5000);
    }
    @Override
    protected void onDestroy(){
        mTimer.cancel();
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_button) {
          //  Toast.makeText(this, "로그아웃이 화면이동해서 했던건가???", Toast.LENGTH_SHORT).show();
          //  return true;
            final DBManager dbManager = new DBManager(getApplicationContext(), "MY.db", null, 1);
            final int now = dbManager.now();
            Intent intent = new Intent(Dorm1Activity.this,SettingActivity.class);
            Bundle bundle =getIntent().getExtras();
            int text = now;
            intent.putExtra("name",text);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }




Button.OnClickListener listener=new Button.OnClickListener(){
    public void onClick(View v) {
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.first);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.second);

        switch (v.getId()) {
            case R.id.alramButton:
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                break;

            case R.id.waitingButton:
                //layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                break;
            case R.id.cancleButton:
                alramDialog = new MainDialog();
                alramDialog.show(getFragmentManager(), "MYTAG");
                break;
        }
    }
    };
    public static class MainDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(
                    getActivity());
            LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
            mBuilder.setView(mLayoutInflater
                    .inflate(R.layout.dialog_main, null));
            mBuilder.setTitle("알림받기를\n취소하시겠습니까?");
            return mBuilder.create();
        }

    }

    public void ONCLICK_DIALOG(View v) {
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.first);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.second);
        switch (v.getId()) {
            case R.id.button1:
                makeToast("알림을 끕니다");
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
                alramDialog.dismiss();
                break;
            case R.id.button2:
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
                makeToast("취소하셨습니다");
                alramDialog.dismiss();
                break;
        }

    }

    public void makeToast(String msg){
        mToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }


    protected void showList(){
        try {
            JSONObject root = new JSONObject(myJSON);
            ja = root.getJSONArray(TAG_RESULTS);

            for(int i=0;i<ja.length();i++){
                JSONObject c = ja.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String state = c.getString(TAG_STATE);
                Drawable drawable1 = getResources().getDrawable(R.drawable.laundryon);
                Drawable drawable2 = getResources().getDrawable(R.drawable.laundryoff);
                Drawable drawable3 = getResources().getDrawable(R.drawable.dryon);
                Drawable drawable4 = getResources().getDrawable(R.drawable.dryoff);
                HashMap<String,String> persons = new HashMap<String,String>();

                if(id.equals("1")){
                    ImageView imageView=(ImageView)findViewById(R.id.dorm1);
                    if(state.equals("1")){
                        imageView.setImageDrawable(drawable1);
                    }else{
                        imageView.setImageDrawable(drawable2);
                    }
                }else  if(id.equals("2")){
                    ImageView imageView=(ImageView)findViewById(R.id.dorm2);
                    if(state.equals("1")){
                        imageView.setImageDrawable(drawable1);
                    }else{
                        imageView.setImageDrawable(drawable2);
                    }
                } else  if(id.equals("3")){
                    ImageView imageView=(ImageView)findViewById(R.id.dorm3);
                    if(state.equals("1")){
                        imageView.setImageDrawable(drawable1);
                    }else{
                        imageView.setImageDrawable(drawable2);
                    }
                } else  if(id.equals("4")){
                    ImageView imageView=(ImageView)findViewById(R.id.dorm4);
                    if(state.equals("1")){
                        imageView.setImageDrawable(drawable1);
                    }else{
                        imageView.setImageDrawable(drawable2);
                    }
                } else  if(id.equals("5")){
                    ImageView imageView=(ImageView)findViewById(R.id.dorm5);
                    if(state.equals("1")){
                        imageView.setImageDrawable(drawable1);
                    }else{
                        imageView.setImageDrawable(drawable2);
                    }
                } else  if(id.equals("8")){
                    ImageView imageView=(ImageView)findViewById(R.id.dorm6);
                    if(state.equals("1")){
                        imageView.setImageDrawable(drawable1);
                    }else{
                        imageView.setImageDrawable(drawable2);
                    }
                } else  if(id.equals("9")){
                    ImageView imageView=(ImageView)findViewById(R.id.dorm7);
                    if(state.equals("1")){
                        imageView.setImageDrawable(drawable1);
                    }else{
                        imageView.setImageDrawable(drawable2);
                    }
                } //else  if(id.equals("")){
                    ImageView imageView1=(ImageView)findViewById(R.id.dorm8);
                    //if(state.equals("1")){
                        imageView1.setImageDrawable(drawable4);
                    //}else{
                     //   imageView.setImageDrawable(drawable4);
                   // }
                //} else  if(id.equals("9")){
                    ImageView imageView2=(ImageView)findViewById(R.id.dorm9);
                  //  if(state.equals("1")){
                        imageView2.setImageDrawable(drawable4);
                   // }else{
                     //   imageView.setImageDrawable(drawable4);
                   // }
               // }



                // persons.put(TAG_ID,id);
                // persons.put(TAG_STATE,state);

                //  personList.add(persons);

            }
            // Log.e("personList : ",personList.toString());
            //  ListAdapter adapter = new SimpleAdapter(
            //          MainActivity.this, personList, R.layout.list_item,
            //          new String[]{TAG_ID,TAG_STATE},
            //          new int[]{R.id.val, R.id.time}
            // );

            //   list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result){
                Log.e("result : ",result +"");
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}