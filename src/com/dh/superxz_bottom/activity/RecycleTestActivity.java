package com.dh.superxz_bottom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.dhutils.MyToast;
import com.dh.superxz_bottom.framework.log.L;
import com.dh.superxz_bottom.framework.util.Density;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.dh.superxz_bottom.R.id.recyclerview;

public class RecycleTestActivity extends Activity {

    @ViewInject(recyclerview)
    private RecyclerView recyclerView;

    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_test);
        ViewUtils.inject(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initData();
    }

    private void initData() {
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
    }

    private void initView() {
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(new MyAdapter());
    }



    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public int getItemCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
////
////            View view = LayoutInflater.from(RecycleTestActivity.this).inflate(
////                    R.layout.item_recycleview, null);
////            return new ViewHolder(view);
//
//
//            View view = mInflater.from(mContext).inflate(R.layout.item_fra_main2, parent, false);
//            ViewHolder holder = new ViewHolder(view);
//            return holder;
//        }

        @Override
        public void onBindViewHolder(ViewHolder arg0, final int arg1) {
            // TODO Auto-generated method stub
            arg0.pos = arg1;
            L.e("yinzl", "position is :" + arg1);
            arg0.textview.setText(list.get(arg1));

        }

        /**
         * 创建新View，被LayoutManager所调用
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(parent.getContext(), R.layout.item_fra_main2, null);
            View view = LayoutInflater.from(RecycleTestActivity.this).inflate(R.layout.item_recycleview, parent, false);
            ViewHolder holder = new ViewHolder(view);
//            if (0 != view.getScrollX()) {
//                view.scrollTo(0, 0);
//            }
            L.e("yinzl", "oncreateview");
            return holder;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textview;
            int pos;
            View view;

            public View getView() {
                return view;
            }

            public void setView(View view) {
                this.view = view;
            }

            public ViewHolder(View arg0) {
                super(arg0);
                setView(arg0);
                arg0.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Toast.makeText(RecycleTestActivity.this, pos + "", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                // TODO Auto-generated constructor stub
                textview = (TextView) arg0.findViewById(R.id.tv_text);

            }

        }
    }
}
