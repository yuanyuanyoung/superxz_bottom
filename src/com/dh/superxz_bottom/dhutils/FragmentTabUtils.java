package com.dh.superxz_bottom.dhutils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;


public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragments;
    private RadioGroup rgs;
    private FragmentManager fragmentManager;
    private int fragmentContentId;
    private int currentTab;
    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;
    private ViewPager viewPager;


    public FragmentTabUtils(FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId, RadioGroup rgs, OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
        rgs.setOnCheckedChangeListener(this);
        ((RadioButton) rgs.getChildAt(0)).setChecked(true);

    }
    public FragmentTabUtils(ViewPager viewPager,FragmentManager fragmentManager, List<Fragment> fragments, RadioGroup rgs) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.viewPager=viewPager;
        this.fragmentManager = fragmentManager;


        ((RadioButton) rgs.getChildAt(0)).setChecked(true);
        for (int i = 0; i < rgs.getChildCount(); i++) {
            ((RadioButton) rgs.getChildAt(i))
                    .setOnClickListener(new MyOnClickListener(i));
        }

        viewPager.setAdapter(new MyFragmentAdapter(fragmentManager,
                fragments));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setOnPageChangeListener((ViewPager.OnPageChangeListener) new MyOnPageChangeListener());

    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            for (int i = 0; i < rgs.getChildCount(); i++) {
                if (rgs.getChildAt(i).getId() == checkedId) {
                    Fragment fragment = fragments.get(i);
                    FragmentTransaction ft = obtainFragmentTransaction(i);
                    getCurrentFragment().onStop();
                    if (fragment.isAdded()) {
                        fragment.onStart();
                    } else {
                        ft.add(fragmentContentId, fragment, String.valueOf(i));
                        ft.commit();
                    }
                    showTab(i);
                    if (null != onRgsExtraCheckedChangedListener) {
                        onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                    }
                }
        }

    }



    private void showTab(int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);
            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx;
    }


    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();

        return ft;
    }


    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }


    public static interface OnRgsExtraCheckedChangedListener {
        public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index);
    }


     class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageSelected(int arg0) {
            for (int i = 0; i < rgs.getChildCount(); i++) {
            if (i== arg0) {
                ((RadioButton) rgs.getChildAt(i)).setChecked(true);
            }

            }

        }

    }
    class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            viewPager.setCurrentItem(index);

        }

    }


     class MyFragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> frags;

        public MyFragmentAdapter(FragmentManager fm, List<Fragment> frag) {
            super(fm);
            this.frags = frag;
        }

        @Override
        public Fragment getItem(int arg0) {
            return frags.get(arg0);
        }

        @Override
        public int getCount() {
            return frags.size();
        }

    }



}