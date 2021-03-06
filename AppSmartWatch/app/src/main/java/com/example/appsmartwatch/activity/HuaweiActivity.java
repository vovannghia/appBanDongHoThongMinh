package com.example.appsmartwatch.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.appsmartwatch.adapter.AdapterHuawei;
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

public class HuaweiActivity extends AppCompatActivity {
    Toolbar toolbarhuawei;
    ListView listViewhuawei;
    AdapterHuawei adapterHuawei;
    ArrayList<SanPham> arrayListhuawei;
    int idhuawei = 0;
    int page = 1;
    View FooterView;
    boolean loading = false;
    boolean limitdata = false;
    HuaweiActivity.mhandler mhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huawei);
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
        listViewhuawei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DetailProduct.class);
                intent.putExtra("thongtinsanpham",arrayListhuawei.get(i));
                startActivity(intent);
            }
        });
        listViewhuawei.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem != 0 && loading == false &&limitdata == false){
                    loading = true;
                    HuaweiActivity.threadData threadData = new HuaweiActivity.threadData();
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
                int IDsanphamhuawei=0;
                String tensanphamhuawei="";
                String hinhanhhuawei="";
                int giasanphamhuawei=0;
                String motahuawei="";
                int IDloaisanphamhuawei=0;
                if (response!=null && response.length() != 2){
                    listViewhuawei.removeFooterView(FooterView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            IDsanphamhuawei = jsonObject.getInt("IDsanpham");
                            tensanphamhuawei = jsonObject.getString("tensanpham");
                            hinhanhhuawei = jsonObject.getString("hinhanh");
                            giasanphamhuawei = jsonObject.getInt("giasanpham");
                            motahuawei = jsonObject.getString("mota");
                            IDloaisanphamhuawei = jsonObject.getInt("IDloaisanpham");
                            arrayListhuawei.add(new SanPham(IDsanphamhuawei,tensanphamhuawei,hinhanhhuawei,giasanphamhuawei,motahuawei,IDloaisanphamhuawei));
                            adapterHuawei.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    listViewhuawei.removeFooterView(FooterView);
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
                param.put("IDloaisanpham",String.valueOf(idhuawei));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarhuawei);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarhuawei.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIDloaiSP() {
        idhuawei = getIntent().getIntExtra("IDloaisanpham",-1);
    }

    private void lienket() {
        toolbarhuawei = findViewById(R.id.toolbarhuawei);
        listViewhuawei = findViewById(R.id.listviewhuawei);
        arrayListhuawei = new ArrayList<>();
        adapterHuawei = new AdapterHuawei(getApplicationContext(),arrayListhuawei);
        listViewhuawei.setAdapter(adapterHuawei);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        FooterView = inflater.inflate(R.layout.progessbar,null);
        mhandler = new mhandler();
    }
    public class mhandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewhuawei.addFooterView(FooterView);
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