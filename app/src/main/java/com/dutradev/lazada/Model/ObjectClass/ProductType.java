package com.dutradev.lazada.Model.ObjectClass;

import java.util.List;

/**
 * Created by dutradev on 15/08/2017.
 */

public class ProductType {
    public int getMALOAISP() {
        return MALOAISP;
    }

    public void setMALOAISP(int MALOAISP) {
        this.MALOAISP = MALOAISP;
    }

    public int getMALOAICHA() {
        return MALOAICHA;
    }

    public void setMALOAICHA(int MALOAICHA) {
        this.MALOAICHA = MALOAICHA;
    }

    public String getTENLOAISP() {
        return TENLOAISP;
    }

    public void setTENLOAISP(String TENLOAISP) {
        this.TENLOAISP = TENLOAISP;
    }

    public List<ProductType> getListCon() {
        return listCon;
    }

    public void setListCon(List<ProductType> listCon) {
        this.listCon = listCon;
    }

    int MALOAISP, MALOAICHA;
    String TENLOAISP;
    List<ProductType> listCon;
}

