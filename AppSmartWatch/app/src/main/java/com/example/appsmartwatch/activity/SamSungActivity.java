package com.example.appsmartwatch.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsmartwatch.R;
import com.example.appsmartwatch.adapter.AdapterApple;
import com.example.appsmartwatch.adapter.AdapterSamsung;
import com.example.appsmartwatch.model.SanPham;
import com.example.appsmartwatch.ultil.CheckConnect;
import com.example.appsmartwatch.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SamSungActivity extends AppCompatActivity {
    Toolbar toolbarsamsung;
    ListView listViewsamsung;
    AdapterSamsung adapterSamsung;
    ArrayList<SanPham> arrayListsamsung;
    int idsamsung = 0;
    int page = 1;
    View FooterView;
    boolean loading = false;
    boolean limitdata = false;
    SamSungActivity.mhandler mhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sam_sung);
        lienket();
        if (CheckConnect.haveNetworkConnection(getApplicationContext())){
            getIDloaiSP();
            actionToolbar();
            getdata(page);
        }else{
            CheckConnect.ShowToast_Short(getApplicationContext(),"Check your internet connection");
            finish();
        }
    }

    private void getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String path = Server.pathapple+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,path,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int IDsanphamsamsung=0;
                String tensanphamsamsung="";
                String hinhanhsamsung="";
                int giasanphamsamsung=0;
                String motasamsung="";
                int IDloaisanphamsamsung=0;
                if (response!=null && response.length() != 2){
                    listViewsamsung.removeFooterView(FooterView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            IDsanphamsamsung = jsonObject.getInt("IDsanpham");
                            tensanphamsamsung = jsonObject.getString("tensanpham");
                            hinhanhsamsung = jsonObject.getString("hinhanh");
                            giasanphamsamsung = jsonObject.getInt("giasanpham");
                            motasamsung = jsonObject.getString("mota");
                            IDloaisanphamsamsung = jsonObject.getInt("IDloaisanpham");
                            arrayListsamsung.add(new SanPham(IDsanphamsamsung,tensanphamsamsung,hinhanhsamsung,giasanphamsamsung,motasamsung,IDloaisanphamsamsung));
                            adapterSamsung.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    listViewsamsung.removeFooterView(FooterView);
                    CheckConnect.ShowToast_Short(getApplicationContext(),"No more data!!!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param= new HashMap<String, String>();
                param.put("IDloaisanpham",String.valueOf(idsamsung));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarsamsung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarsamsung.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIDloaiSP() {
        idsamsung = getIntent().getIntExtra("IDloaisanpham",-1);
    }

    private void lienket() {
        toolbarsamsung = findViewById(R.id.toolbarsamsung);
        listViewsamsung = findViewById(R.id.listviewsamsung);
        arrayListsamsung = new ArrayList<>();
        adapterSamsung = new AdapterSamsung(getApplicationContext(),arrayListsamsung);
        listViewsamsung.setAdapter(adapterSamsung);
    }
    public class mhandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewsamsung.addFooterView(FooterView);
                    break;
                case 1:
                    getdata(++page);
                    loading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class threadData extends Thread{
        @Override
        public void run() {
            mhandler.sendEmptyMessage(0);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //obtainmsg: pt lienket cac thread vs mhandler
            Message message = mhandler.obtainMessage(1);
            mhandler.sendMessage(message);
            super.run();
        }
    }
}