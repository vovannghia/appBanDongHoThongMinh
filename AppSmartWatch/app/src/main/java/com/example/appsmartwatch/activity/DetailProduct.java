package com.example.appsmartwatch.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appsmartwatch.R;
import com.example.appsmartwatch.model.Cart;
import com.example.appsmartwatch.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailProduct extends AppCompatActivity {
    Toolbar toolbardetail;
    ImageView imageViewdetail;
    TextView textViewnameproduct,textViewpriceproduct,textViewdescproduct;
    Spinner spinner;
    Button button;
    int IDsanpham = 0;
    String namedetail="";
    String img ="";
    int pricedetail=0;
    String descdetail="";
    int IDloaisanpham =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        lienket();
        actiontoolbar();
        getDetailproduct();
        catcheventspinner();
        eventbutton();
    }
    // set icon trên thanh toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menucart,menu);
        return true;
    }

    @Override
    // bắt nút icon trên màn hình Cart
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void eventbutton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.cartArrayList.size() >0){
                    int quantitys = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exist = false;
                    for (int i = 0; i< MainActivity.cartArrayList.size(); i++){
                        if (MainActivity.cartArrayList.get(i).getIdSP() ==IDsanpham){
                            MainActivity.cartArrayList.get(i).setQuantity(MainActivity.cartArrayList.get(i).getQuantity() + quantitys);
                            if (MainActivity.cartArrayList.get(i).getQuantity() >=10){
                                MainActivity.cartArrayList.get(i).setQuantity(10);
                            }
                            MainActivity.cartArrayList.get(i).setGiaSP(pricedetail*MainActivity.cartArrayList.get(i).getQuantity());
                            exist = true;
                        }
                    }
                    if (exist == false){
                        int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                        long newprice = quantity*pricedetail;
                        MainActivity.cartArrayList.add(new Cart(IDsanpham,namedetail,newprice,img,quantity));
                    }
                }else{
                    int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                    long newprice = quantity*pricedetail;
                    MainActivity.cartArrayList.add(new Cart(IDsanpham,namedetail,newprice,img,quantity));
                }
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void catcheventspinner() {
        Integer[] quantity = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,quantity);
        spinner.setAdapter(arrayAdapter);
    }

    private void getDetailproduct() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        IDsanpham = sanPham.getIDsanpham();
        namedetail = sanPham.getTensanpham();
        img = sanPham.getHinhanh();
        pricedetail = sanPham.getGiasanpham();
        descdetail = sanPham.getMota();
        IDloaisanpham = sanPham.getIDloaisanpham();
        textViewnameproduct.setText(namedetail);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewpriceproduct.setText("Giá : " + decimalFormat.format(pricedetail)+ "₫");
        textViewdescproduct.setText(descdetail);
        Picasso.with(getApplicationContext()).load((img))
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imageViewdetail);
    }

    private void actiontoolbar() {
        setSupportActionBar(toolbardetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void lienket() {
        toolbardetail = findViewById(R.id.toolbardetailproduct);
        imageViewdetail = findViewById(R.id.imageviewdetailproduct);
        textViewnameproduct = findViewById(R.id.texviewdetailproductname);
        textViewpriceproduct = findViewById(R.id.texviewdetailproductprice);
        textViewdescproduct = findViewById(R.id.textviewdescdetailproduct);
        spinner = findViewById(R.id.spinners);
        button = findViewById(R.id.buttonbuy);
    }
}