package com.example.appsmartwatch.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appsmartwatch.R;
import com.example.appsmartwatch.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterApple extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListApple;

    public AdapterApple(Context context, ArrayList<SanPham> arrayListApple) {
        this.context = context;
        this.arrayListApple = arrayListApple;
    }

    @Override
    // trả về số dòng của list
    public int getCount() {
        return arrayListApple.size();
    }

    @Override
    //auto dc goi. trong list
    public Object getItem(int i) {
        return arrayListApple.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView textViewapple,textViewgiaapple,textViewmotaapple;
        public ImageView imageViewapple;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            //LayoutInflater inflater = LayoutInflater.from(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.list_apple,null);
            viewHolder.textViewapple = view.findViewById(R.id.textviewapple);
            viewHolder.textViewgiaapple = view.findViewById(R.id.textviewgiaapple);
            viewHolder.textViewmotaapple = view.findViewById(R.id.textviewmotaapple);
            viewHolder.imageViewapple = view.findViewById(R.id.imageviewapple);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.textViewapple.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewgiaapple.setText("Giá : " + decimalFormat.format(sanPham.getGiasanpham())+ "₫");
        viewHolder.textViewmotaapple.setMaxLines(2);
        viewHolder.textViewmotaapple.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textViewmotaapple.setText(sanPham.getMota());
        Picasso.with(context).load(sanPham.getHinhanh())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageViewapple);
        return view;
    }
}
