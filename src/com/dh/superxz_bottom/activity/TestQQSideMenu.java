package com.dh.superxz_bottom.activity;

import android.os.Bundle;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.view.qqSideMenu.CoordinatorMenu;
import com.dh.superxz_bottom.yinzldemo.VehicleNoSwipbackActivity;

/**
 * Created by Administrator on 2017/1/17.
 */

public class TestQQSideMenu extends VehicleNoSwipbackActivity{

    private CoordinatorMenu mCoordinatorMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testqqside);
        setNomStatus();
    }
}
