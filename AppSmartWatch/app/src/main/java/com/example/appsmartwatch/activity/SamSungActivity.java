package com.example.appsmartwatch.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
            loadmoredata();
        }else{
            CheckConnect.ShowToast_Short(getApplicationContext(),"Check your internet connection");
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menucart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadmoredata() {
        listViewsamsung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //cau lenh. chuyen? man` hinh`
                Intent intent = new Intent(getApplicationContext(),DetailProduct.class);
                intent.putExtra("thongtinsanpham",arrayListsamsung.get(i));
                startActivity(intent);
            }
        });
        listViewsamsung.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem != 0 && loading == false &&limitdata == false){
                    loading = true;
                    SamSungActivity.threadData threadData = new SamSungActivity.threadData();
                    threadData.start();
                }
            }
        });
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
                    CheckConnect.ShowToast_Short(getApplicationContext(),"No more data");
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
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        FooterView = inflater.inflate(R.layout.progessbar,null);
        mhandler = new mhandler();
    }
    //phan bo? job cho thread
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
    //chia ra nhiu` luong`
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