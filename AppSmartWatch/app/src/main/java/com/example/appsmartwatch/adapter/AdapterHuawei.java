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

public class AdapterHuawei extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListHuawei;

    public AdapterHuawei(Context context, ArrayList<SanPham> arrayListHuawei) {
        this.context = context;
        this.arrayListHuawei = arrayListHuawei;
    }

    @Override
    //getCount: get ve` so' lg. dong` tran ban?ve~ context
    public int getCount() {
        return arrayListHuawei.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListHuawei.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView textviewhuawei,textviewgiahuawei,textviewmotahuawei;
        public ImageView imageviewhuawei;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.list_huawei,null);
            viewHolder.textviewhuawei = view.findViewById(R.id.textviewhuawei);
            viewHolder.textviewgiahuawei = view.findViewById(R.id.textviewgiahuawei);
            viewHolder.textviewmotahuawei = view.findViewById(R.id.textviewmotahuawei);
            viewHolder.imageviewhuawei = view.findViewById(R.id.imageviewhuawei);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.textviewhuawei.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textviewgiahuawei.setText("Giá : " + decimalFormat.format(sanPham.getGiasanpham())+ "₫");
        viewHolder.textviewmotahuawei.setMaxLines(2);
        viewHolder.textviewmotahuawei.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textviewmotahuawei.setText(sanPham.getMota());
        Picasso.with(context).load(sanPham.getHinhanh())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageviewhuawei);
        return view;
    }
}
