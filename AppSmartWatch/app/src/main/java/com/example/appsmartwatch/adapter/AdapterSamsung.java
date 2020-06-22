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

public class AdapterSamsung extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListSamsung;

    public AdapterSamsung(Context context, ArrayList<SanPham> arrayListSamsung) {
        this.context = context;
        this.arrayListSamsung = arrayListSamsung;
    }

    @Override
    //getCount: get ve` so' lg. dong` tran ban?ve~ context
    public int getCount() {
        return arrayListSamsung.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListSamsung.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView textviewsamsung,textviewgiasamsung,textviewmotasamsung;
        public ImageView imageviewsamsung;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.list_samsung,null);
            viewHolder.textviewsamsung = view.findViewById(R.id.textviewsamsung);
            viewHolder.textviewgiasamsung = view.findViewById(R.id.textviewgiasamsung);
            viewHolder.textviewmotasamsung = view.findViewById(R.id.textviewmotasamsung);
            viewHolder.imageviewsamsung = view.findViewById(R.id.imageviewsamsung);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.textviewsamsung.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textviewgiasamsung.setText("Giá : " + decimalFormat.format(sanPham.getGiasanpham())+ "₫");
        viewHolder.textviewmotasamsung.setMaxLines(2);
        viewHolder.textviewmotasamsung.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textviewmotasamsung.setText(sanPham.getMota());
        Picasso.with(context).load(sanPham.getHinhanh())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageviewsamsung);
        return view;
    }
}
