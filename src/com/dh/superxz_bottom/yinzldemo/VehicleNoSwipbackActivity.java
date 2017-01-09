package com.dh.superxz_bottom.yinzldemo;

/*
 * Copyright (C), 2014-2014, 联创车盟汽车服务有限公司
 * FileName: VehicleActivity
 * Author:   jun.w
 * Date:     2014/11/3 16:10
 * Description: Activity基类
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.constant.Constant;
import com.dh.superxz_bottom.framework.db.AfeiDb;
import com.dh.superxz_bottom.framework.net.fgview.Response.ErrorMsg;
import com.dh.superxz_bottom.view.StatusBarUtil.SystemBarTintManager;

public class VehicleNoSwipbackActivity extends FragmentActivity implements
        View.OnClickListener {

    protected Button btn_top_right;
    private SystemBarTintManager mTintManager;
    private WindowManager windowManager;
    private WindowManager.LayoutParams fluctuateParam;
    private boolean isShowFluctuate;
    public AfeiDb afeiDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VehicleApp.getInstance().addActivity(this);
        afeiDb = VehicleApp.getInstance().getAfeiDb();
        if (null == afeiDb) {
            afeiDb = AfeiDb.create(this, Constant.DB_NAME, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setTintColor(0xF00099CC);

        }
    }

    //除去状态栏颜色
    public void setNomStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTintManager.setStatusBarTintEnabled(false);
        }
    }

    protected void initTop() {
        RelativeLayout btn_back = (RelativeLayout) this
                .findViewById(R.id.btn_title_btn_back_layout);
        if (null != btn_back) {
            btn_back.setOnClickListener(this);
        }
        btn_top_right = (Button) findViewById(R.id.btn_top_right);
        if (null != btn_top_right) {
            btn_top_right.setOnClickListener(this);
        }
    }

    public void launch(Class<? extends Activity> clazz) {
        launch(new Intent(this, clazz));
    }

    public void launch(Intent intent) {
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VehicleApp.getInstance().getActivitys().remove(this); // 退出时从集合中拿出
    }

    public void setTitle(String title) {
        super.setTitle(title);
        ((TextView) this.findViewById(R.id.tv_title_name)).setText(title);
    }

    public void setTitle(int titleId) {
        super.setTitle(titleId);
        ((TextView) this.findViewById(R.id.tv_title_name)).setText(titleId);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_title_btn_back_layout) {
            onBackPressed();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public interface OnLoginFinishLintener {
        public void onSuccess();

        public void onFailure(ErrorMsg errorMsg);
    }

}
