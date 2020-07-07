package com.example.appsmartwatch.model;

public class Cart {
    public int IdSP;
    public String tenSP;
    public long giaSP;
    public String hinhanh;
    public int quantity;
    public Cart(int idSP, String tenSP, long giaSP, String hinhanh, int quantity) {
        IdSP = idSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhanh = hinhanh;
        this.quantity = quantity;
    }
    public int getIdSP() {
        return IdSP;
    }
    public void setIdSP(int idSP) {
        IdSP = idSP;
    }
    public String getTenSP() {
        return tenSP;
    }
    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    public long getGiaSP() {
        return giaSP;
    }
    public void setGiaSP(long giaSP) {
        this.giaSP = giaSP;
    }
    public String getHinhanh() {
        return hinhanh;
    }
    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
