package com.example.appsmartwatch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appsmartwatch.R;
import com.example.appsmartwatch.adapter.AdapterCarts;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listViewcart;
    TextView textViewnotif;
    static TextView textViewtotal;
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
        catchonitemevent();
        eventbutton();
    }
    // bat' su. kien. tiep' tuc. mua hang`
    private void eventbutton() {
        buttonctnshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // bat su. kien. thanh toan' gio? hang`
    private void catchonitemevent() {
        listViewcart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Confirm Delete Product!!!");
                builder.setMessage("Are you sure you want to delete this product ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (MainActivity.cartArrayList.size()<=0){
                            textViewnotif.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.cartArrayList.remove(position);
                            adapterCarts.notifyDataSetChanged();
                            eventcart();
                            if (MainActivity.cartArrayList.size()<=0){
                                textViewnotif.setVisibility(View.VISIBLE);
                            }else {
                                textViewnotif.setVisibility(View.INVISIBLE);
                                adapterCarts.notifyDataSetChanged();
                                eventcart();
                            }
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        adapterCarts.notifyDataSetChanged();
                        eventcart();
                    }
                });
                builder.show();
                return true;
            }
        });
    }




    public static void eventcart() {
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