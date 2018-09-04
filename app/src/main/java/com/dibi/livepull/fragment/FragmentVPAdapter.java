package com.dibi.livepull.fragment;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;


/**
 * Created by _H_JY on 2015/11/23.
 */


public class FragmentVPAdapter extends FragmentPagerAdapter {
    private ArrayList<TestFm> fragments;
    private FragmentManager fm;
    public FragmentVPAdapter(FragmentManager fm, ArrayList<TestFm> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    public void setFragments(ArrayList<TestFm> fragments) {
        if(this.fragments != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:this.fragments){
                ft.remove(f);
            }
            ft.commit();
            ft=null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}