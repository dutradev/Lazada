package com.dutradev.lazada.Model.Home.MenuHandling;

import android.util.Log;

import com.dutradev.lazada.ConnectInternet.DownloadJSON;
import com.dutradev.lazada.Model.ObjectClass.ProductType;
import com.dutradev.lazada.View.Home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by dutradev on 15/08/2017.
 */

public class HandlingJSONMenu {
    public List<ProductType> ParseJSONMenu(String dulieujson) {
        List<ProductType> loaiSanPhamList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(dulieujson);
            JSONArray loaisanpham = jsonObject.getJSONArray("LOAISANPHAM");

            int count = loaisanpham.length();
            for (int i = 0; i < count; i++) {
                JSONObject value = loaisanpham.getJSONObject(i);

                ProductType dataLoaiSanPham = new ProductType();
                dataLoaiSanPham.setMALOAISP(Integer.parseInt(value.getString("MALOAISP")));
                dataLoaiSanPham.setMALOAICHA(Integer.parseInt(value.getString("MALOAI_CHA")));
                dataLoaiSanPham.setTENLOAISP(value.getString("TENLOAISP"));

                loaiSanPhamList.add(dataLoaiSanPham);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return loaiSanPhamList;
    }

    public List<ProductType> LayLoaiSanPhamTheoMaLoai(int maloaisp) {
        List<ProductType> loaiSanPhamList = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>();
        String dataJSON = "";

        String duongdan = "http://10.0.3.2/lazada/loaisanpham.php";
        HashMap<String, String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha", String.valueOf(maloaisp));
        attrs.add(hsMaLoaiCha);
        DownloadJSON downloadJSON = new DownloadJSON(duongdan, attrs);
        //end POST
        downloadJSON.execute();

        try {
            dataJSON = downloadJSON.get();
            HandlingJSONMenu xuLyJSONMenu = new HandlingJSONMenu();
            loaiSanPhamList = xuLyJSONMenu.ParseJSONMenu(dataJSON);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return loaiSanPhamList;
    }
}
