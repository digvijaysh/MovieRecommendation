package com.example.movierecommendation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.movierecommendation.fragments.HomeFragment;
import com.example.movierecommendation.fragments.NewFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsAdapter(FragmentManager fm, int NoofTabs) {
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment newTab = new NewFragment();
                return newTab;
            case 1:
                Fragment home = new HomeFragment();
                return home;
            default:
                return null;
        }
    }
}
