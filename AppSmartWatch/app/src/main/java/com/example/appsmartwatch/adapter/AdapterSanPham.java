package com.example.appsmartwatch.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsmartwatch.R;
import com.example.appsmartwatch.activity.DetailProduct;
import com.example.appsmartwatch.model.SanPham;
import com.example.appsmartwatch.ultil.CheckConnect;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.ItemHolder> {
    Context context;
    ArrayList<SanPham> arrayListSanPham;

    public AdapterSanPham(Context context, ArrayList<SanPham> arrayListSanPham) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.san_pham_moi_nhat,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = arrayListSanPham.get(position);
        holder.txttensp.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá : " + decimalFormat.format(sanPham.getGiasanpham())+ "₫");
        Picasso.with(context).load(sanPham.getHinhanh())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imghinhanhsp);
    }

    @Override
    public int getItemCount() {
        return arrayListSanPham.size();
    }

    //bat' gia' tri. tren man` hinh` app
    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhanhsp;
        public TextView txttensp,txtgiasp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhanhsp = itemView.findViewById(R.id.imageviewsanpham);
            txtgiasp = itemView.findViewById(R.id.textviewgiasanpham);
            txttensp = itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailProduct.class);
                    intent.putExtra("thongtinsanpham",arrayListSanPham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnect.ShowToast_Short(context,arrayListSanPham.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }
}
