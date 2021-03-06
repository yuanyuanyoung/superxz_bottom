package com.dh.superxz_bottom.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.dhutils.FragmentTabUtils;
import com.dh.superxz_bottom.fragment.BitmapFragment;
import com.dh.superxz_bottom.fragment.EventBusFragment;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.yinzldemo.VehicleNoSwipbackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class TabTestActivity extends VehicleNoSwipbackActivity implements FragmentTabUtils.OnRgsExtraCheckedChangedListener {
    @ViewInject(R.id.rg_tab)
    private RadioGroup rg_tab;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    List<Fragment> fragments = new ArrayList<Fragment>();
    FragmentTabUtils fragmentTabUtils;
    BitmapFragment bitmapFragment=new BitmapFragment();
    EventBusFragment eventBusFragment= new EventBusFragment();
    FragmentManager fragmentManager=this.getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabtest);
        super.initTop();
        ViewUtils.inject(this);
        initview();
    }

    private void initview() {
        fragments.add(0,bitmapFragment );
        fragments.add(1,eventBusFragment);
//        fragmentTabUtils=new FragmentTabUtils(fragmentManager,fragments,R.id.fragmentContent,rg_tab,this );
        fragmentTabUtils=new FragmentTabUtils(viewPager,fragmentManager,fragments,rg_tab);

    }

    @Override
    public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {

    }
}
