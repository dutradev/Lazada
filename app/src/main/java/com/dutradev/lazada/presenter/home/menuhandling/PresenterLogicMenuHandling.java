package com.dutradev.lazada.presenter.home.menuhandling;

import com.dutradev.lazada.connectinternet.DownloadJSON;
import com.dutradev.lazada.model.home.menuhandling.HandlingJSONMenu;
import com.dutradev.lazada.model.signin.ModelSignIn;
import com.dutradev.lazada.model.objectclass.ProductType;
import com.dutradev.lazada.view.home.ViewMenuHandling;
import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by dutradev on 15/08/2017.
 */

public class PresenterLogicMenuHandling implements IPresenterMenuHandling {

    ViewMenuHandling viewMenuHandling;

    public PresenterLogicMenuHandling(ViewMenuHandling viewMenuHandling){
        this.viewMenuHandling = viewMenuHandling;
    }

    @Override
    public void LayDanhSachMenu() {
        List<ProductType> loaiSanPhamList;
        String dataJSON = "";
        List<HashMap<String,String>> attrs = new ArrayList<>();

        //Lấy bằng phương thức get
//        String duongdan = "http://192.168.1.9/weblazada/loaisanpham.php?maloaicha=0";

//        DownloadJSON downloadJSON = new DownloadJSON(duongdan);
        // end phương thức get

        //Lấy bằng phương thức post
        String duongdan = "http://10.0.3.2/lazada/loaisanpham.php";

        HashMap<String,String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha","0");

        attrs.add(hsMaLoaiCha);

        DownloadJSON downloadJSON = new DownloadJSON(duongdan,attrs);
        //end phương thức post
        downloadJSON.execute();

        try {
            dataJSON = downloadJSON.get();
            HandlingJSONMenu xuLyJSONMenu = new HandlingJSONMenu();
            loaiSanPhamList = xuLyJSONMenu.ParseJSONMenu(dataJSON);
            viewMenuHandling.HienThiDanhSachMenu(loaiSanPhamList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccessToken LayTokenDungFacebook() {
        ModelSignIn modelSignIn = new ModelSignIn();
        AccessToken accessToken = modelSignIn.LayTokenFacebookHienTai();
        return accessToken;
    }
}
