package com.dh.superxz_bottom.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.entity.MenuVo;
import com.dh.superxz_bottom.framework.util.Density;
import com.dh.superxz_bottom.framework.util.StringUtils;
import com.dh.superxz_bottom.view.CircleImageView;
import com.dh.superxz_bottom.yinzldemo.PhotoViewActivity;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author 超级小志
 * 
 */
@SuppressLint("ValidFragment")
public class ViewPagerFragmentTop extends Fragment {
	private Context mContext;
	private ArrayList<MenuVo> mViewPagerTopBeans;

	@SuppressLint("ValidFragment")
	public ViewPagerFragmentTop(Context context,
			ArrayList<MenuVo> viewPagerTopBeans) {
		super();
		this.mContext = context;
		this.mViewPagerTopBeans = viewPagerTopBeans;
	}

	/**
	 * 覆盖此函数，先通过inflater inflate函数得到view最后返回
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// L.e("yinzl", "viewPagerFragmentTop size is :" +
		// mViewPagerTopBeans.size());
		LinearLayout view = (LinearLayout) inflater.inflate(
				R.layout.view_pager_fragment_demo1, container, false);
		// TextView tv_top1 = (TextView) v.findViewById(R.id.tv_top1);
		// TextView tv_top2 = (TextView) v.findViewById(R.id.tv_top2);
		// TextView tv_top3 = (TextView) v.findViewById(R.id.tv_top3);
		// TextView tv_top4 = (TextView) v.findViewById(R.id.tv_top4);
		// ImageView iv_top1 = (ImageView) v.findViewById(R.id.iv_top1);
		// ImageView iv_top2 = (ImageView) v.findViewById(R.id.iv_top2);
		// ImageView iv_top3 = (ImageView) v.findViewById(R.id.iv_top3);
		// ImageView iv_top4 = (ImageView) v.findViewById(R.id.iv_top4);
		// LinearLayout ll_top1 = (LinearLayout) v.findViewById(R.id.ll_top1);
		// LinearLayout ll_top2 = (LinearLayout) v.findViewById(R.id.ll_top2);
		// LinearLayout ll_top3 = (LinearLayout) v.findViewById(R.id.ll_top3);
		// LinearLayout ll_top4 = (LinearLayout) v.findViewById(R.id.ll_top4);
		// switch (mViewPagerTopBeans.size()) {
		// case 1:
		// MenuVo men0V010 = mViewPagerTopBeans.get(0);
		// ll_top1.setVisibility(View.VISIBLE);
		// ll_top2.setVisibility(View.INVISIBLE);
		// ll_top3.setVisibility(View.INVISIBLE);
		// ll_top4.setVisibility(View.INVISIBLE);
		// tv_top1.setText(men0V010.getMenuName());
		// setImageViewByView(iv_top1, R.drawable.icon_top_menu1,
		// men0V010.getPictureLocation());
		// break;
		// case 2:
		// MenuVo men0V020 = mViewPagerTopBeans.get(0);
		// MenuVo men0V021 = mViewPagerTopBeans.get(1);
		// ll_top1.setVisibility(View.VISIBLE);
		// ll_top2.setVisibility(View.VISIBLE);
		// ll_top3.setVisibility(View.INVISIBLE);
		// ll_top4.setVisibility(View.INVISIBLE);
		// tv_top1.setText(men0V020.getMenuName());
		// tv_top2.setText(men0V021.getMenuName());
		// setImageViewByView(iv_top1, R.drawable.icon_top_menu1,
		// men0V020.getPictureLocation());
		// setImageViewByView(iv_top2, R.drawable.icon_top_menu2,
		// men0V021.getPictureLocation());
		// break;
		// case 3:
		// MenuVo men0V030 = mViewPagerTopBeans.get(0);
		// MenuVo men0V031 = mViewPagerTopBeans.get(1);
		// MenuVo men0V032 = mViewPagerTopBeans.get(2);
		// ll_top1.setVisibility(View.VISIBLE);
		// ll_top2.setVisibility(View.VISIBLE);
		// ll_top3.setVisibility(View.VISIBLE);
		// ll_top4.setVisibility(View.INVISIBLE);
		// tv_top1.setText(men0V030.getMenuName());
		// tv_top2.setText(men0V031.getMenuName());
		// tv_top3.setText(men0V032.getMenuName());
		// setImageViewByView(iv_top1, R.drawable.icon_top_menu1,
		// men0V030.getPictureLocation());
		// setImageViewByView(iv_top2, R.drawable.icon_top_menu2,
		// men0V031.getPictureLocation());
		// setImageViewByView(iv_top2, R.drawable.icon_top_menu3,
		// men0V032.getPictureLocation());
		// break;
		// case 4:
		// MenuVo men0V040 = mViewPagerTopBeans.get(0);
		// MenuVo men0V041 = mViewPagerTopBeans.get(1);
		// MenuVo men0V042 = mViewPagerTopBeans.get(2);
		// MenuVo men0V043 = mViewPagerTopBeans.get(3);
		// ll_top1.setVisibility(View.VISIBLE);
		// ll_top2.setVisibility(View.VISIBLE);
		// ll_top3.setVisibility(View.VISIBLE);
		// ll_top4.setVisibility(View.VISIBLE);
		// tv_top1.setText(men0V040.getMenuName());
		// tv_top2.setText(men0V041.getMenuName());
		// tv_top3.setText(men0V042.getMenuName());
		// tv_top4.setText(men0V043.getMenuName());
		// setImageViewByView(iv_top1, R.drawable.icon_top_menu1,
		// men0V040.getPictureLocation());
		// setImageViewByView(iv_top2, R.drawable.icon_top_menu2,
		// men0V041.getPictureLocation());
		// setImageViewByView(iv_top3, R.drawable.icon_top_menu3,
		// men0V042.getPictureLocation());
		// setImageViewByView(iv_top4, R.drawable.icon_top_menu4,
		// men0V043.getPictureLocation());
		// break;
		// default:
		// break;
		// }

		// ll_top1.setOnClickListener(this);
		// ll_top2.setOnClickListener(this);
		// ll_top3.setOnClickListener(this);
		// ll_top4.setOnClickListener(this);
		int len = mViewPagerTopBeans.size();
		if (mViewPagerTopBeans != null && len > 0) {
			for (int i = 0; i < len; i++) {
				final MenuVo vo = mViewPagerTopBeans.get(i);
				View item_view = inflater
						.inflate(R.layout.home_menu_item, null);
				SimpleDraweeView sdv = (SimpleDraweeView) item_view
						.findViewById(R.id.sdv_icon);
				Uri uri = Uri.parse(vo.getPictureLocation());
				sdv.setImageURI(uri);
				sdv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mContext,
								PhotoViewActivity.class);
						mContext.startActivity(intent);
					}
				});
				// (SimpleDraweeView)item_view.findViewById(R.id.sdv).
				((TextView) item_view.findViewById(R.id.tv_label)).setText(vo
						.getMenuName());
				((TextView) item_view.findViewById(R.id.tv_sub_label))
						.setText(vo.getSubtitle());
				item_view.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
					}
				});
				item_view
						.setLayoutParams(new LinearLayout.LayoutParams(Density
								.getSceenWidth(mContext) / 4,
								LayoutParams.MATCH_PARENT));
				view.addView(item_view);
			}
		}

		return view;
	}

}
