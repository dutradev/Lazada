package com.dutradev.lazada.model.signinsignup;

import android.util.Log;

import com.dutradev.lazada.connectinternet.DownloadJSON;
import com.dutradev.lazada.model.objectclass.Customer;
import com.dutradev.lazada.view.home.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by dutradev on 16/08/2017.
 */

public class ModelSignUp {
    public Boolean registerMember(Customer mCustomer) {
        String duongdan = HomeActivity.SERVER_NAME;
        boolean isCheck = false;

        List<HashMap<String,String>> attrs = new ArrayList<>();

        HashMap<String,String> hsHam = new HashMap<>();
        hsHam.put("ham","DangKyThanhVien");

        HashMap<String,String> hsTenNV = new HashMap<>();
        hsTenNV.put("tennv", mCustomer.getmName());

        HashMap<String,String> hsTenDN = new HashMap<>();
        hsTenDN.put("tendangnhap", mCustomer.getmUserName());

        HashMap<String,String> hsMatKhau = new HashMap<>();
        hsMatKhau.put("matkhau", mCustomer.getmPassword());

        HashMap<String,String> hsMaLoaiNV = new HashMap<>();
        hsMaLoaiNV.put("maloainv",String.valueOf(mCustomer.getmCustomerType()));

        HashMap<String,String> hsEmailDocQuyen = new HashMap<>();
        hsEmailDocQuyen.put("emaildocquyen", mCustomer.getmReciveNotification());

        attrs.add(hsHam);
        attrs.add(hsTenNV);
        attrs.add(hsTenDN);
        attrs.add(hsMatKhau);
        attrs.add(hsMaLoaiNV);
        attrs.add(hsEmailDocQuyen);

        DownloadJSON downloadJSON = new DownloadJSON(duongdan,attrs);
        downloadJSON.execute();

        try {
            String dulieuJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dulieuJSON);
            String ketqua = jsonObject.getString("ketqua");
            Log.d("kiemtra",ketqua);
            if(ketqua.equals("true")){
                isCheck = true;
            }else{
                isCheck = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isCheck;
    }
}
