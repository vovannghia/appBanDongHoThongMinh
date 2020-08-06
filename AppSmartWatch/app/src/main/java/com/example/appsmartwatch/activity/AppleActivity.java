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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsmartwatch.R;
import com.example.appsmartwatch.adapter.AdapterApple;
import com.example.appsmartwatch.model.SanPham;
import com.example.appsmartwatch.ultil.CheckConnect;
import com.example.appsmartwatch.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppleActivity extends AppCompatActivity {
    Toolbar toolbarapple;
    ListView listViewapple;
    AdapterApple adapterApple;
    ArrayList<SanPham> arrayListapple;
    int idapple = 0;
    int page = 1;
    View FooterView;
    boolean loading = false;
    boolean limitdata = false;
    mhandler mhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple);
        lienket();
        if (CheckConnect.haveNetworkConnection(getApplicationContext())){
            getIDloaiSP();
            actionToolbar();
            getdata(page);
            loadmoredata();
        }else {
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
    // hàm lấy thêm dữ liệu
    private void loadmoredata() {
        listViewapple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DetailProduct.class);
                intent.putExtra("thongtinsanpham",arrayListapple.get(i));
                startActivity(intent);
            }
        });
        listViewapple.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem != 0 && loading == false && limitdata == false){
                    loading = true;
                    threadData threadData = new threadData();
                    threadData.start();
                }
            }
        });
    }
    // hàm lấy loại sản phẩm
    private void getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String path = Server.pathapple+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,path,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int IDsanphamapple=0;
                String tensanphamapple="";
                String hinhanhapple="";
                int giasanphamapple=0;
                String motaapple="";
                int IDloaisanphamapple=0;
                if (response!=null && response.length() != 2){
                    listViewapple.removeFooterView(FooterView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            IDsanphamapple = jsonObject.getInt("IDsanpham");
                            tensanphamapple = jsonObject.getString("tensanpham");
                            hinhanhapple = jsonObject.getString("hinhanh");
                            giasanphamapple = jsonObject.getInt("giasanpham");
                            motaapple = jsonObject.getString("mota");
                            IDloaisanphamapple = jsonObject.getInt("IDloaisanpham");
                            arrayListapple.add(new SanPham(IDsanphamapple,tensanphamapple,hinhanhapple,giasanphamapple,motaapple,IDloaisanphamapple));
                            adapterApple.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    listViewapple.removeFooterView(FooterView);
                    CheckConnect.ShowToast_Short(getApplicationContext(),"No more data");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> param= new HashMap<String, String>();
                param.put("IDloaisanpham",String.valueOf(idapple));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarapple);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarapple.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        }

    private void getIDloaiSP() {
        idapple = getIntent().getIntExtra("IDloaisanpham",-1);
        Log.d("giatriloaisanphamapple",idapple+"");
    }
    // ánh xạ trong android hay bắt sự kiện trong android
    private void lienket() {
        toolbarapple = findViewById(R.id.toolbarapple);
        listViewapple = findViewById(R.id.listviewapple);
        arrayListapple = new ArrayList<>();
        adapterApple = new AdapterApple(getApplicationContext(),arrayListapple);
        listViewapple.setAdapter(adapterApple);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        FooterView = inflater.inflate(R.layout.progessbar,null);
        mhandler = new mhandler();
    }
    public class mhandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewapple.addFooterView(FooterView);
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