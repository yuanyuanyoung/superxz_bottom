package com.dh.superxz_bottom.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.entity.MenuVo;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.yinzldemo.VehicleFragment;

/**
 * @author 超级小志
 *
 */
public class BitmapFragment extends VehicleFragment {
	private ArrayList<MenuVo> mMenuVos;
	List<Fragment> fragmentList = new ArrayList<Fragment>();// 页面list

	@ViewInject(R.id.vp_top)
	private ViewPager vp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.bitmap_fragment, container, false); // 加载fragment布局
		ViewUtils.inject(this, view);
		initView();
		return view;
	}

	private void initView() {
		mMenuVos = new ArrayList<MenuVo>();
		mMenuVos.clear();
		for (int i = 0; i < 5; i++) {
			MenuVo vo = new MenuVo();
			vo.setMenuName("菜单" + i);
			vo.setPictureLocation("https://www.baidu.com/img/270new_30a763e04c05a9e39501d4427e7b6990.png");
			mMenuVos.add(vo);
		}
		initViewPager();
	}

	public void initViewPager() {
		fragmentList.clear();
		int size = mMenuVos.size();
		int viewPagerCount = size / 4;
		int viewPagerLastCount = size % 4;
		for (int i = 0; i < viewPagerCount; i++) {
			ArrayList<MenuVo> viewPagerTopBeans = new ArrayList<MenuVo>();
			viewPagerTopBeans.add(mMenuVos.get(i * 4 + 0));
			viewPagerTopBeans.add(mMenuVos.get(i * 4 + 1));
			viewPagerTopBeans.add(mMenuVos.get(i * 4 + 2));
			viewPagerTopBeans.add(mMenuVos.get(i * 4 + 3));
			fragmentList.add(new ViewPagerFragmentTop(getActivity(),
					viewPagerTopBeans));
		}
		if (viewPagerLastCount > 0) {
			ArrayList<MenuVo> viewPagerTopBeans = new ArrayList<MenuVo>();
			for (int i = 0; i < viewPagerLastCount; i++) {
				viewPagerTopBeans.add(mMenuVos.get(viewPagerCount * 4 + i));
			}
			fragmentList.add(new ViewPagerFragmentTop(getActivity(),
					viewPagerTopBeans));
		}
		vp.setAdapter(new MyPagerAdapter(getActivity()
				.getSupportFragmentManager(), fragmentList));
	}

	class MyPagerAdapter extends FragmentStatePagerAdapter {

		private List<Fragment> fragmentList;

		public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}

	public void refreshData(boolean showDialog) {
	}
}
