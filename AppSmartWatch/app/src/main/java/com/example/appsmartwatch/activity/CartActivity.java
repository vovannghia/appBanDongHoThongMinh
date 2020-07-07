package com.example.appsmartwatch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appsmartwatch.R;
import com.example.appsmartwatch.adapter.AdapterCarts;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listViewcart;
    TextView textViewnotif;
    TextView textViewtotal;
    Button buttoncheckout,buttonctnshopping;
    Toolbar toolbarcart;
    AdapterCarts adapterCarts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        lienket();
        actionbar();
        checkdata();
        eventcart();
    }

    private void eventcart() {
        long total = 0;
        for (int i=0;i<MainActivity.cartArrayList.size();i++){
            total +=MainActivity.cartArrayList.get(i).getGiaSP();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewtotal.setText(decimalFormat.format(total)+ "₫");
    }

    private void checkdata() {
        if (MainActivity.cartArrayList.size() <=0){
            adapterCarts.notifyDataSetChanged();
            textViewnotif.setVisibility(View.VISIBLE);
            listViewcart.setVisibility(View.INVISIBLE);
        }else{
            adapterCarts.notifyDataSetChanged();
            textViewnotif.setVisibility(View.INVISIBLE);
            listViewcart.setVisibility(View.VISIBLE);
        }
    }

    private void actionbar() {
        setSupportActionBar(toolbarcart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void lienket() {
        listViewcart = findViewById(R.id.listviewcart);
        textViewnotif = findViewById(R.id.textviewcart);
        textViewtotal = findViewById(R.id.txtvalues);
        buttoncheckout = findViewById(R.id.buttontop);
        buttonctnshopping = findViewById(R.id.buttonbottom);
        toolbarcart = findViewById(R.id.toolbarcart);
        adapterCarts = new AdapterCarts(CartActivity.this,MainActivity.cartArrayList);
        listViewcart.setAdapter(adapterCarts);

    }
}