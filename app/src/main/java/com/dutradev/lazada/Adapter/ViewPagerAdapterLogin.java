package com.dutradev.lazada.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dutradev.lazada.View.LogIn.Fragment.FragmentLogIn;
import com.dutradev.lazada.View.LogIn.Fragment.FragmentRegister;

/**
 * Created by dutradev on 15/08/2017.
 */

public class ViewPagerAdapterLogin extends FragmentPagerAdapter{
    public ViewPagerAdapterLogin(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                FragmentLogIn fragmentLogIn = new FragmentLogIn();
                return fragmentLogIn;
            case 1 :
                FragmentRegister fragmentRegister = new FragmentRegister();
                return fragmentRegister;

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Đăng nhập";
            case 1 :
                return "Đăng ký";

            default: return null;
        }

    }
}
