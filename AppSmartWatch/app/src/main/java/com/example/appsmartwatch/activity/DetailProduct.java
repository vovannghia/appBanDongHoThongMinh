package com.example.appsmartwatch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appsmartwatch.R;
import com.example.appsmartwatch.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailProduct extends AppCompatActivity {
    Toolbar toolbardetail;
    ImageView imageViewdetail;
    TextView textViewnameproduct,textViewpriceproduct,textViewdescproduct;
    Spinner spinner;
    Button button;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        lienket();
        actiontoolbar();
        getDetailproduct();
        catcheventspinner();
    }

    private void catcheventspinner() {
        Integer[] quantity = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,quantity);
        spinner.setAdapter(arrayAdapter);
    }

    private void getDetailproduct() {
        int IDsanpham = 0;
        String namedetail="";
        String img ="";
        int pricedetail=0;
        String descdetail="";
        int IDloaisanpham =0;
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