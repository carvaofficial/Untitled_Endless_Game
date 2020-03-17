package com.example.untitledendlessgame.Scenes.MarkersTabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabsAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> tabsFragments = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void insert(Fragment f) {
        tabsFragments.add(f);
    }

    @Override
    public Fragment getItem(int position) {
        return tabsFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabsFragments.size();
    }
}
