package com.example.appsmartwatch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appsmartwatch.R;
import com.example.appsmartwatch.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLoaiSP extends BaseAdapter {
    ArrayList<LoaiSP> arrayListLoaiSP;
    // truyền vào màn hình
    Context context;

    public AdapterLoaiSP(ArrayList<LoaiSP> arrayListLoaiSP, Context context) {
        this.arrayListLoaiSP = arrayListLoaiSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaiSP.size();
        //đưa ra những giá trị trong array
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaiSP.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i ;
    }

    public class ViewHolder{
        TextView textViewloaiSP;
        ImageView imageViewloaiSP;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            //lấy layout ra
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listviewloaisp,null);
            viewHolder.textViewloaiSP = view.findViewById(R.id.textviewloaisp);
            viewHolder.imageViewloaiSP = view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSP loaiSP = (LoaiSP) getItem(i);
        viewHolder.textViewloaiSP.setText(loaiSP.getTenloaiSP());
        Picasso.with(context).load(loaiSP.getHinhanhloaiSP())
                .placeholder(R.drawable.menu)
                .error(R.drawable.error)
                .into(viewHolder.imageViewloaiSP);
        return view;
    }
}
