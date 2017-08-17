package com.dutradev.lazada.presenter.home.menuhandling;

import com.dutradev.lazada.connectinternet.DownloadJSON;
import com.dutradev.lazada.model.home.menuhandling.HandlingJSONMenu;
import com.dutradev.lazada.model.signinsignup.ModelSignIn;
import com.dutradev.lazada.model.objectclass.ProductType;
import com.dutradev.lazada.view.home.HomeActivity;
import com.dutradev.lazada.view.home.IViewMenuHandling;
import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by dutradev on 15/08/2017.
 */

public class PresenterLogicMenuHandling implements IPresenterMenuHandling {

    IViewMenuHandling IViewMenuHandling;

    public PresenterLogicMenuHandling(IViewMenuHandling IViewMenuHandling){
        this.IViewMenuHandling = IViewMenuHandling;
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
        String duongdan = HomeActivity.SERVER_NAME ;

        HashMap<String,String> hsHam = new HashMap<>();
        hsHam.put("ham","LayDanhSachMenu");

        HashMap<String,String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha","0");

        attrs.add(hsMaLoaiCha);
        attrs.add(hsHam);

        DownloadJSON downloadJSON = new DownloadJSON(duongdan,attrs);
        //end phương thức post
        downloadJSON.execute();

        try {
            dataJSON = downloadJSON.get();
            HandlingJSONMenu xuLyJSONMenu = new HandlingJSONMenu();
            loaiSanPhamList = xuLyJSONMenu.ParserJSONMenu(dataJSON);
            IViewMenuHandling.showListMenu(loaiSanPhamList);
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
