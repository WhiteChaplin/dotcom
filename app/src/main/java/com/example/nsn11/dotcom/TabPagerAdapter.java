package com.example.nsn11.dotcom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    public int tabCount;
    private Bundle fragmentBundle = null;

    public TabPagerAdapter(FragmentManager fm, int tabCount, Bundle fragmentBundle) {
        super(fm);
        this.tabCount = tabCount;
        this.fragmentBundle = fragmentBundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabFragment1 tabFragment1 = new TabFragment1();
                tabFragment1.setArguments(this.fragmentBundle);
                return tabFragment1;
            case 1:
                TabFragment2 tabFragment2 = new TabFragment2();
                tabFragment2.setArguments(this.fragmentBundle);
                return tabFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
