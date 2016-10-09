package com.dh.superxz_bottom.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.adapter.ArrayListAdapter;
import com.dh.superxz_bottom.dhutils.MyToast;
import com.dh.superxz_bottom.view.CircleImageView;
import com.dh.superxz_bottom.view.CustomButton;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.xutils.view.annotation.event.OnClick;
import com.dh.superxz_bottom.yinzldemo.VehicleNoSwipbackActivity;

import java.util.ArrayList;

/**
 * Created by mac on 16/10/9.
 */

public class PayCentActivity extends VehicleNoSwipbackActivity {

    @ViewInject(R.id.pc_lv_pay)
    private ListView pc_lv_pay;

    @ViewInject(R.id.lv_pay_num)
    private ListView lv_pay_num;

    @ViewInject(R.id.pc_cb_confirm_payment)
    private CustomButton pc_cb_confirm_payment;


    private PcAdapter adpater;
    private PcAdapterNum adpater_num;
    private ArrayList<String> list;

    private String[] list_det;
    private String[] list_num;


    private int selectTAG;
    private int selectTAG_num;
    private String payOrderId;
    private String orderDeposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_cent);
        ViewUtils.inject(this);
        super.setTitle("充值");
        super.initTop();

        init();
    }

    private void init() {

        list = new ArrayList<String>();
        list.add("支付宝");
        list.add("微信");
        list.add("话费支付");

        adpater = new PcAdapter(this);
        adpater.setList(list);
        pc_lv_pay.setAdapter(adpater);


        pc_lv_pay.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adpater.setSelectedIndex(position);
                adpater.notifyDataSetChanged();
                selectTAG = position;
                MyToast.show(PayCentActivity.this,"你点了"+list.get(position),1500);
            }
        });

        list_num = new String[]{"10", "15", "25"};
        list_det = new String[]{"100", "200", "超级会员包"};

        adpater_num = new PcAdapterNum(this);
        adpater_num.setList(list_num);
        lv_pay_num.setAdapter(adpater_num);
        lv_pay_num.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adpater_num.setSelectedIndex(position);
                adpater_num.notifyDataSetChanged();
                selectTAG_num = position;
                MyToast.show(PayCentActivity.this,"你点了"+list_num[position],1500);
            }
        });
    }

    @OnClick(R.id.pc_cb_confirm_payment)
    public void onClickSubmitOrder(View view) {

        switch (selectTAG) {
            case 0:
                break;
            case 1:
                break;
            default:
                break;
        }
        // launch(new Intent(this, PayStateActivity.class));
    }

    class PcAdapter extends ArrayListAdapter<String> {
        private Context context;

        private int selectedTag = 0;

        public PcAdapter(Activity context) {
            super(context);
            this.context = context;
        }

        public void setSelectedIndex(int index) {
            selectedTag = index;
        }

        class Holder {
            CircleImageView icon;
            TextView title;
            CheckBox check;
        }

        @SuppressLint({"ResourceAsColor", "InflateParams"})
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_paymentcenter, null);
                holder.icon = (CircleImageView) convertView
                        .findViewById(R.id.pc_it_ci_payment_icon);
                holder.title = (TextView) convertView
                        .findViewById(R.id.pc_it_tv_payment_title);
                holder.check = (CheckBox) convertView
                        .findViewById(R.id.pc_it_cb_check);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            if ("支付宝".equals(mList.get(position))) {
                holder.icon.setImageResource(R.drawable.icon_ali_pay);
            } else if ("微信".equals(mList.get(position))) {
                holder.icon.setImageResource(R.drawable.icon_wexin_pay);
            } else if ("话费支付".equals(mList.get(position))) {
                holder.icon.setImageResource(R.drawable.icon_message_pay);
            }
            holder.title.setText(mList.get(position).toString());
            if (selectedTag == position) {
                holder.check.setChecked(true);
            } else {
                holder.check.setChecked(false);
            }
            return convertView;
        }
    }

    class PcAdapterNum extends ArrayListAdapter<String> {
        private Context context;

        private int selectedTag = 0;

        public PcAdapterNum(Activity context) {
            super(context);
            this.context = context;
        }

        public void setSelectedIndex(int index) {
            selectedTag = index;
        }

        class Holder {
            TextView icon;
            TextView title;
            CheckBox check;
        }

        @SuppressLint({"ResourceAsColor", "InflateParams"})
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_paynum, null);
                holder.icon = (TextView) convertView
                        .findViewById(R.id.pc_it_ci_payment_icon);
                holder.title = (TextView) convertView
                        .findViewById(R.id.pc_it_tv_payment_title);
                holder.check = (CheckBox) convertView
                        .findViewById(R.id.pc_it_cb_check);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.icon.setText(list_det[position].toString());
            holder.title.setText(list_num[position].toString());
            if (selectedTag == position) {
                holder.check.setChecked(true);
            } else {
                holder.check.setChecked(false);
            }
            return convertView;
        }
    }
}
