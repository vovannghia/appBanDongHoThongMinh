package com.example.appsmartwatch.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int IDsanpham;
    public String tensanpham;
    public String hinhanh;
    public Integer giasanpham;
    public String mota;
    public int IDloaisanpham;

    public SanPham(int IDsanpham, String tensanpham, String hinhanh, Integer giasanpham, String mota, int IDloaisanpham) {
        this.IDsanpham = IDsanpham;
        this.tensanpham = tensanpham;
        this.hinhanh = hinhanh;
        this.giasanpham = giasanpham;
        this.mota = mota;
        this.IDloaisanpham = IDloaisanpham;
    }

    public int getIDsanpham() {
        return IDsanpham;
    }

    public void setIDsanpham(int IDsanpham) {
        this.IDsanpham = IDsanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Integer getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(Integer giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getIDloaisanpham() {
        return IDloaisanpham;
    }

    public void setIDloaisanpham(int IDloaisanpham) {
        this.IDloaisanpham = IDloaisanpham;
    }
}
