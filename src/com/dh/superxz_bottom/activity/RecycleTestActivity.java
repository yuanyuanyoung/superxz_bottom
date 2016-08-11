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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.dhutils.MyToast;
import com.dh.superxz_bottom.framework.log.L;
import com.dh.superxz_bottom.framework.util.Density;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import static com.dh.superxz_bottom.R.id.recyclerview;

public class RecycleTestActivity extends Activity implements SwipyRefreshLayout.OnRefreshListener {

    @ViewInject(recyclerview)
    private SwipeRecyclerView recyclerView;
    @ViewInject(R.id.swipyrefreshlayout)
    private SwipyRefreshLayout swipyrefreshlayout;

    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_test);
        ViewUtils.inject(this);
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
        recyclerView.setRightViewWidth(Density.of(this, 80));
        swipyrefreshlayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipyrefreshlayout.setOnRefreshListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(new MyAdapter());
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        if (direction == (SwipyRefreshLayoutDirection.BOTTOM)) {
            MyToast.show(RecycleTestActivity.this, direction + "", 1500);
        } else {
            MyToast.show(RecycleTestActivity.this, direction + "", 1500);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Hide the refresh after 2sec
                RecycleTestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipyrefreshlayout.setRefreshing(false);
                        recyclerView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                });
            }
        }, 2000);

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
            arg0.ll_orderlsit_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(arg1);
                    notifyDataSetChanged();
                }
            });
            if (0 != arg0.getView().getScrollX()) {
                arg0.getView().scrollTo(0, 0);
            }
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
            LinearLayout ll_orderlsit_del;
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
                ll_orderlsit_del = (LinearLayout) arg0.findViewById(R.id.ll_delect);

            }

        }
    }
}
