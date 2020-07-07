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
    public View getView(int position, View view, ViewGroup viewGroup) {
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
        return view;
    }
}
