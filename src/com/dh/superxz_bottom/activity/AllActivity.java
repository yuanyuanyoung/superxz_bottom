package com.dh.superxz_bottom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.dhutils.CommUtil;
import com.dh.superxz_bottom.xutils.sample.utils.PubUtils;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.yinzldemo.DatePickerActivity;
import com.dh.superxz_bottom.yinzldemo.FragmentTabActivity2;

public class AllActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @ViewInject(R.id.dl_drawer_layout)
    private DrawerLayout dl_drawer_layout;
    @ViewInject(R.id.ll_drawer_view)
    private LinearLayout ll_drawer_view;
    @ViewInject(R.id.lv_side)
    private ListView lv_side;
    @ViewInject(R.id.ll_top)
    private LinearLayout ll_top;
    @ViewInject(R.id.ll_side_top)//顶部颜色块
    private LinearLayout ll_side_top;//侧滑顶部颜色块
    @ViewInject(R.id.tv_menu)
    private TextView tv_menu;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        ViewUtils.inject(this);
        initView();
        initData();
    }

    private void initData() {


    }

    private void initView() {

        //沉浸状态栏
        if (Build.VERSION.SDK_INT >= 19) {
            ll_top.setVisibility(View.VISIBLE);
            ll_side_top.setVisibility(View.VISIBLE);
            getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置(侧边)顶部块高度
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height = CommUtil
                    .getStatusHeight(this);
            ll_top.setLayoutParams(layoutParams);
            ll_side_top.setLayoutParams(layoutParams);
        }

        lv_side.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                new String[]{"注册", "登录", "FragmentTabActivity2", "忘记密码", "时间选择", "SRV", "7", "8"}));
        lv_side.setOnItemClickListener(this);
        tv_menu.setOnClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dl_drawer_layout.closeDrawers();

        Intent intent = new Intent();

        switch (position) {
            case 0:
                intent.setClass(AllActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case 1:
                intent.setClass(AllActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(AllActivity.this, FragmentTabActivity2.class);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(AllActivity.this, ForgetActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent.setClass(AllActivity.this, DatePickerActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent.setClass(AllActivity.this, RecycleTestActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        ;

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                PubUtils.popTipOrWarn(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                VehicleApp.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_menu:
                dl_drawer_layout.openDrawer(Gravity.LEFT);

                break;
            default:
                break;

        };
    }
}
