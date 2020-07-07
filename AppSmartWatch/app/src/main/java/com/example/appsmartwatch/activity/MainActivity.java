package com.example.appsmartwatch.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsmartwatch.R;
import com.example.appsmartwatch.adapter.AdapterLoaiSP;
import com.example.appsmartwatch.adapter.AdapterSanPham;
import com.example.appsmartwatch.model.Cart;
import com.example.appsmartwatch.model.LoaiSP;
import com.example.appsmartwatch.model.SanPham;
import com.example.appsmartwatch.ultil.CheckConnect;
import com.example.appsmartwatch.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recycler;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> arrayListloaiSP;
    AdapterLoaiSP adapterLoaiSP;
    int ID = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";
    ArrayList<SanPham> arrayListSanPham;
    AdapterSanPham adapterSanPham;
    public static ArrayList<Cart> cartArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lienket();
        if (CheckConnect.haveNetworkConnection(getApplicationContext())){
            actionbar();
            actionviewflipper();
            GetdataLoaiSP();
            GetdataSPmoinhat();
            GetdataListView();
        }else{
            CheckConnect.ShowToast_Short(getApplicationContext(),"Check your internet connection");
            finish();
        }
    }

    @Override
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

    private void GetdataListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0 :
                            if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else{
                                CheckConnect.ShowToast_Short(getApplicationContext(),"Check you internet connection");
                            }
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    case 1 :
                            if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                                Intent intent = new Intent(MainActivity.this,AppleActivity.class);
                                intent.putExtra("IDloaisanpham",arrayListloaiSP.get(i).getID());
                                startActivity(intent);
                            }else{
                                CheckConnect.ShowToast_Short(getApplicationContext(),"Check you internet connection");
                            }
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    case 2 :
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,SamSungActivity.class);
                            intent.putExtra("IDloaisanpham",arrayListloaiSP.get(i).getID());
                            startActivity(intent);
                        }else{
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Check you internet connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3 :
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,HuaweiActivity.class);
                            intent.putExtra("IDloaisanpham",arrayListloaiSP.get(i).getID());
                            startActivity(intent);
                        }else{
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Check you internet connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4 :
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ContactActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Check you internet connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5 :
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,InfomationActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Check you internet connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetdataSPmoinhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathSPmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    int IDsanpham = 0;
                    String tensanpham = "";
                    String hinhanh = "";
                    Integer giasanpham = 0;
                    String mota = "";
                    int IDloaisanpham = 0;
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            IDsanpham = jsonObject.getInt("IDsanpham");
                            tensanpham = jsonObject.getString("tensanpham");
                            hinhanh = jsonObject.getString("hinhanh");
                            giasanpham = jsonObject.getInt("giasanpham");
                            mota = jsonObject.getString("mota");
                            IDloaisanpham = jsonObject.getInt("IDloaisanpham");
                            arrayListSanPham.add(new SanPham(IDsanpham,tensanpham,hinhanh,giasanpham,mota,IDloaisanpham));
                            adapterSanPham.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetdataLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i = 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("IDloaisanpham");
                            tenloaisp = jsonObject.getString("tenloaisanpham");
                            hinhanhloaisp = jsonObject.getString("hinhanh");
                            arrayListloaiSP.add(new LoaiSP(ID,tenloaisp,hinhanhloaisp));
                            adapterLoaiSP.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } 
                    }
                    arrayListloaiSP.add(4, new LoaiSP(0,"Liện Hệ","https://raovat999.com/wp-content/uploads/2019/05/contact-image-icon-14.png"));
                    arrayListloaiSP.add(5, new LoaiSP(0,"Thông Tin","https://www.freeiconspng.com/uploads/information-icon-29.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnect.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionviewflipper() {
        ArrayList<String> mangads = new ArrayList<>();
        mangads.add("https://ontimefashion.files.wordpress.com/2019/09/smart-watch-banner.gif?w=332");
        mangads.add("https://didongviet.vn/blog/wp-content/uploads/2019/06/apple-watch-banner-2.jpg");
        mangads.add("https://i.ytimg.com/vi/NF_buAgb25c/maxresdefault.jpg");
        for(int i=0;i<mangads.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangads.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        //hàm chạy trong bao lâu
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideinright);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideoutright);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void actionbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void lienket(){
        toolbar = findViewById(R.id.toolbarmanhinhchu);
        viewFlipper = findViewById(R.id.viewflippermanhinhchu);
        recycler = findViewById(R.id.recyclerviewmanhinhchu);
        navigationView = findViewById(R.id.navigationviewmanhinhchu);
        listView = findViewById(R.id.listviewmanhinhchu);
        drawerLayout = findViewById(R.id.drawerlayoutmanhinhchu);
        arrayListloaiSP = new ArrayList<>();
        arrayListloaiSP.add(0,new LoaiSP(0,"Trang Chủ","https://i7.pngguru.com/preview/907/970/515/computer-icons-home-house-home-thumbnail.jpg"));
        adapterLoaiSP = new  AdapterLoaiSP(arrayListloaiSP,getApplicationContext());
        listView.setAdapter(adapterLoaiSP);
        arrayListSanPham = new ArrayList<>();
        adapterSanPham = new AdapterSanPham(getApplicationContext(),arrayListSanPham);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recycler.setAdapter(adapterSanPham);
        //very important
        if (cartArrayList != null){

        }else{
            cartArrayList = new ArrayList<>();
        }
    }
}