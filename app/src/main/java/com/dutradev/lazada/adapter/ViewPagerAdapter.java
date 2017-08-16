package com.dutradev.lazada.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dutradev.lazada.view.home.fragment.FragmentPromotions;
import com.dutradev.lazada.view.home.fragment.FragmentElectronics;
import com.dutradev.lazada.view.home.fragment.FragmentBeauty;
import com.dutradev.lazada.view.home.fragment.FragmentMomAndBaby;
import com.dutradev.lazada.view.home.fragment.FragmentHomeAndLiving;
import com.dutradev.lazada.view.home.fragment.FragmentHighlights;
import com.dutradev.lazada.view.home.fragment.FragmentSportsAndTravel;
import com.dutradev.lazada.view.home.fragment.FragmentFashion;
import com.dutradev.lazada.view.home.fragment.FragmentBrand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutradev on 15/08/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> titleFragment = new ArrayList<String>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(new FragmentHighlights());
        listFragment.add(new FragmentPromotions());
        listFragment.add(new FragmentElectronics());
        listFragment.add(new FragmentHomeAndLiving());
        listFragment.add(new FragmentMomAndBaby());
        listFragment.add(new FragmentBeauty());
        listFragment.add(new FragmentFashion());
        listFragment.add(new FragmentSportsAndTravel());
        listFragment.add(new FragmentBrand());

        titleFragment.add("Nổi bật");
        titleFragment.add("Chương trình khuyến mãi");
        titleFragment.add("Điện tử");
        titleFragment.add("Nhà cửa & đời sống");
        titleFragment.add("Mẹ và bé");
        titleFragment.add("Làm đẹp");
        titleFragment.add("Thời trang");
        titleFragment.add("Thể thao & du lịch");
        titleFragment.add("Thương hiệu");
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleFragment.get(position);
    }
}
