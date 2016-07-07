package com.dh.superxz_bottom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.xutils.sample.utils.ToastUtil;

import java.util.List;

/**
 * Created by yyy on 16-07-06.
 */
public class SlideItemAdapter extends RecyclerView.Adapter<SlideItemAdapter.TextVH> implements ItemSlideHelper.Callback {

    private RecyclerView mRecyclerView;
    private List<String> list;

    private Context ctx;

    public SlideItemAdapter(List<String> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }


    @Override
    public TextVH onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
        TextVH holder = new TextVH(view);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(list.get((Integer) view.getTag()));
                notifyDataSetChanged();
                ToastUtil.showLong(ctx, (Integer) view.getTag() + "");
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(list.get((Integer) view.getTag()));
                notifyDataSetChanged();
                ToastUtil.showLong(ctx, (Integer) view.getTag() + "");
            }
        });
        holder.ll_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove((Integer) view.getTag());
                notifyDataSetChanged();
                ToastUtil.showLong(ctx, (Integer) view.getTag() + "");
            }
        });
        return new TextVH(view);
    }

    @Override
    public void onBindViewHolder(TextVH holder, final int position) {
        String text = "item: " + position;
//        TextVH textVH = (TextVH) holder;
        holder.textView.setText("item: " + list.get(position));

//        holder.ll_delect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.remove(position);
//                notifyDataSetChanged();
//            }
//        });
        holder.itemView.setTag(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {

        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            if (viewGroup.getChildCount() == 2) {
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        }


        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }


    class TextVH extends RecyclerView.ViewHolder {

        TextView textView;
        TextView tv_delete;
        LinearLayout ll_delect;

        public TextVH(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_text);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            ll_delect = (LinearLayout) itemView.findViewById(R.id.ll_delect);
        }
    }

}
