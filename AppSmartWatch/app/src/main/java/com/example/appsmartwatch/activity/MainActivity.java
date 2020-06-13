package com.example.appsmartwatch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.appsmartwatch.model.LoaiSP;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lienket();
        if (CheckConnect.haveNetworkConnection(getApplicationContext())){
            actionbar();
            actionviewflipper();
            GetdataLoaiSP();
        }else{
            CheckConnect.ShowToast_Short(getApplicationContext(),"Check your internet connection");
            finish();
        }
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
    }
}