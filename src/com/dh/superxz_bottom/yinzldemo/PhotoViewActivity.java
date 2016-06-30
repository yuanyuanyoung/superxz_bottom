package com.dh.superxz_bottom.yinzldemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.view.photoview.HackyViewPager;
import com.dh.superxz_bottom.view.photoview.PhotoView;

public class PhotoViewActivity extends VehicleActivity {

	private static final String ISLOCKED_ARG = "isLocked";
	private ViewPager mViewPager;
	private int position;
	private static ArrayList<String> datasList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_view);
		mViewPager = (HackyViewPager) findViewById(R.id.viewpager);
		datasList=new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			datasList
					.add("https://www.baidu.com/img/270new_30a763e04c05a9e39501d4427e7b6990.png");
		}

		mViewPager.setAdapter(new SamplePagerAdapter());
		if (getIntent() != null) {
			position = getIntent().getIntExtra("position", 0);
			mViewPager.setCurrentItem(position);
		}

		if (savedInstanceState != null) {
			boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG,
					false);
			((HackyViewPager) mViewPager).setLocked(isLocked);
		}

	}

	static class SamplePagerAdapter extends PagerAdapter {

		ArrayList<String> datas = datasList;

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			photoView.setImageUri(datas.get(position));

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	private boolean isViewPagerActive() {
		return (mViewPager != null && mViewPager instanceof HackyViewPager);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (isViewPagerActive()) {
			outState.putBoolean(ISLOCKED_ARG,
					((HackyViewPager) mViewPager).isLocked());
		}
		super.onSaveInstanceState(outState);
	}
}
