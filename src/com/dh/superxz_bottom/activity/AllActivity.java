package com.dh.superxz_bottom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.dhutils.CommUtil;
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
    @ViewInject(R.id.btn_top_right)
    private Button btn_top_right;
    @ViewInject(R.id.content)
    private View mContentView;
    @ViewInject(R.id.loading_spinner)
    private View mLoadingView;
    private int mShortAnimationDuration;
    private boolean isclick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        ViewUtils.inject(this);
        initView();
        initData();
    }

    private void initData() {
//        try {
//           InputStream inputStream= getAssets().open("data.txt");
//
//            int length = inputStream.available();
//
//            byte [] buffer = new byte[length];
//
//            //读取数据
//            inputStream.read(buffer);
//
//            //依test.txt的编码类型选择合适的编码，如果不调整会乱码
//            String s= EncodingUtils.getString(buffer, "BIG5");
//           MyToast.show(AllActivity.this,"11111111111"+s,5000);
//
//            //关闭
//            inputStream.close();
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


    private void initView() {

        //沉浸状态栏
        if (Build.VERSION.SDK_INT >= 19) {
            ll_top.setVisibility(View.VISIBLE);
            ll_side_top.setVisibility(View.VISIBLE);
            //透明状态栏
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
                new String[]{"注册", "登录", "FragmentTabActivity2", "忘记密码", "时间选择", "侧滑删除RecyclerView", "7", "8"}));
        lv_side.setOnItemClickListener(this);
        tv_menu.setOnClickListener(this);
        btn_top_right.setOnClickListener(this);

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
            case 6:
                intent.setClass(AllActivity.this, TabTestActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        ;

    }
    private boolean isExit = false;

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            VehicleApp.getInstance().exit();
            finish();
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                PubUtils.popTipOrWarn(this, "再按一次退出程序");
//                exitTime = System.currentTimeMillis();
//            } else {
//                VehicleApp.getInstance().exit();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_menu:
                dl_drawer_layout.openDrawer(Gravity.LEFT);

                break;
            case R.id.btn_top_right:
//                if (!isclick)
//                    crossfade();
//                else
//                    defaultView();
                break;

            default:
                break;

        }
        ;
    }





    }








