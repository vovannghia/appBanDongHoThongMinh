package com.example.appsmartwatch.model;

public class LoaiSP {
    public int ID;
    public String tenloaiSP;
    public String hinhanhloaiSP;

    public LoaiSP(int ID, String tenloaiSP, String hinhanhloaiSP) {
        this.ID = ID;
        this.tenloaiSP = tenloaiSP;
        this.hinhanhloaiSP = hinhanhloaiSP;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenloaiSP() {
        return tenloaiSP;
    }

    public void setTenloaiSP(String tenloaiSP) {
        this.tenloaiSP = tenloaiSP;
    }

    public String getHinhanhloaiSP() {
        return hinhanhloaiSP;
    }

    public void setHinhanhloaiSP(String hinhanhloaiSP) {
        this.hinhanhloaiSP = hinhanhloaiSP;
    }
}
