package com.dh.superxz_bottom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.activity.PayCentActivity;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.yinzldemo.VehicleFragment;

/**
 * Created by mac on 16/10/9.
 */

public class MineFragment extends VehicleFragment implements View.OnClickListener {
    @ViewInject(R.id.ll_mine_pay)
    private LinearLayout ll_mine_pay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ViewUtils.inject(this, view);

        initview();

        return view;
    }

    private void initview() {
        ll_mine_pay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mine_pay:
                launch(PayCentActivity.class);


                break;
            default:
                break;
        }
    }
}
