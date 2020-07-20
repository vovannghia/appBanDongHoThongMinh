package com.example.appsmartwatch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appsmartwatch.R;
import com.example.appsmartwatch.activity.CartActivity;
import com.example.appsmartwatch.activity.MainActivity;
import com.example.appsmartwatch.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterCarts extends BaseAdapter {
    Context context;
    ArrayList<Cart> carts;

    public AdapterCarts(Context context, ArrayList<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int position) {
        return carts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtnamecart,txtpricecart;
        public ImageView imgcart;
        public Button buttonam,buttonquantity,buttoncong;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_cart,null);
            viewHolder.txtnamecart = view.findViewById(R.id.txtnamecart);
            viewHolder.txtpricecart = view.findViewById(R.id.txtpricecart);
            viewHolder.imgcart = view.findViewById(R.id.imgcart);
            viewHolder.buttonam = view.findViewById(R.id.buttonam);
            viewHolder.buttonquantity = view.findViewById(R.id.buttonquantity);
            viewHolder.buttoncong = view.findViewById(R.id.buttoncong);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.txtnamecart.setText(cart.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtpricecart.setText(decimalFormat.format(cart.getGiaSP())+ "₫");
        Picasso.with(context).load(cart.getHinhanh())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgcart);
        viewHolder.buttonquantity.setText(cart.getQuantity()+ "");

        int quantity = Integer.parseInt(viewHolder.buttonquantity.getText().toString());
        if (quantity>=10){
            viewHolder.buttoncong.setVisibility(View.INVISIBLE);
            viewHolder.buttonam.setVisibility(View.VISIBLE);
        }else
            if (quantity<=1){
                viewHolder.buttonam.setVisibility(View.INVISIBLE);
            }else
                if (quantity>=1){
                    viewHolder.buttonam.setVisibility(View.VISIBLE);
                    viewHolder.buttoncong.setVisibility(View.VISIBLE);
                }


        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.buttoncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.buttonquantity.getText().toString())+1;
                int slhientai = MainActivity.cartArrayList.get(position).getQuantity();
                long giahientai = MainActivity.cartArrayList.get(position).getGiaSP();
                MainActivity.cartArrayList.get(position).setQuantity(slmoinhat);
                long giamoinhat = (giahientai*slmoinhat)/ slhientai;
                MainActivity.cartArrayList.get(position).setGiaSP(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtpricecart.setText(decimalFormat.format(giamoinhat)+ "₫");
                CartActivity.eventcart();
                if (slmoinhat>9){
                    finalViewHolder.buttoncong.setVisibility(View.INVISIBLE);
                    finalViewHolder.buttonam.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonquantity.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.buttonam.setVisibility(View.VISIBLE);
                    finalViewHolder.buttoncong.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonquantity.setText(String.valueOf(slmoinhat));
                }

            }
        });

        viewHolder.buttonam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.buttonquantity.getText().toString())-1;
                int slhientai = MainActivity.cartArrayList.get(position).getQuantity();
                long giahientai = MainActivity.cartArrayList.get(position).getGiaSP();
                MainActivity.cartArrayList.get(position).setQuantity(slmoinhat);
                long giamoinhat = (giahientai*slmoinhat)/ slhientai;
                MainActivity.cartArrayList.get(position).setGiaSP(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtpricecart.setText(decimalFormat.format(giamoinhat)+ "₫");
                CartActivity.eventcart();
                if (slmoinhat<2){
                    finalViewHolder.buttonam.setVisibility(View.INVISIBLE);
                    finalViewHolder.buttoncong.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonquantity.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.buttonam.setVisibility(View.VISIBLE);
                    finalViewHolder.buttoncong.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonquantity.setText(String.valueOf(slmoinhat));
                }
            }
        });


        return view;
    }
}
