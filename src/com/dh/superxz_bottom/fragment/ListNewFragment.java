package com.dh.superxz_bottom.fragment;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.swipemenulistview.SwipeMenu;
import com.dh.superxz_bottom.swipemenulistview.SwipeMenuCreator;
import com.dh.superxz_bottom.swipemenulistview.SwipeMenuItem;
import com.dh.superxz_bottom.swipemenulistview.SwipeMenuListView;
import com.dh.superxz_bottom.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.dh.superxz_bottom.view.RefreshLayout;
import com.dh.superxz_bottom.view.RefreshLayout.OnLoadListener;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author 超级小志
 * 
 */
public class ListNewFragment extends Fragment {
	private List<ApplicationInfo> mAppList;
	private AppAdapter mAdapter;
	@ViewInject(R.id.listView)
	private SwipeMenuListView listView;

	@ViewInject(R.id.swiperefreshlayout)
	private RefreshLayout swiperefreshlayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_list, container, false);
		ViewUtils.inject(this, view);
		initView();
		// 初始化请求网路数据
		swiperefreshlayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						swiperefreshlayout.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						swiperefreshlayout.setRefreshing(true);

						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								swiperefreshlayout.setRefreshing(false);
							}
						}, 1500);

					}

				});
		setRefreshLayoutListener();
		return view;
	}

	private void initView() {
		mAppList = getActivity().getPackageManager()
				.getInstalledApplications(0);

		mAdapter = new AppAdapter();
		listView.setAdapter(mAdapter);

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// Create different menus depending on the view type
				switch (menu.getViewType()) {
				case 0:
					createMenu1(menu);
					break;
				case 1:
					createMenu2(menu);
					break;
				case 2:
					createMenu3(menu);
					break;
				}
			}

			private void createMenu1(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18,
						0x5E)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.ic_action_favorite);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.ic_action_good);
				menu.addMenuItem(item2);
			}

			private void createMenu2(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0,
						0x3F)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.ic_action_important);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F,
						0x25)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.ic_action_discard);
				menu.addMenuItem(item2);
			}

			private void createMenu3(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1,
						0xF5)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.ic_action_about);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.ic_action_share);
				menu.addMenuItem(item2);
			}
		};
		// set creator
		listView.setMenuCreator(creator);

		// step 2. listener item click event
		listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
					break;
				case 1:
					// delete
					// delete(item);
					mAppList.remove(position);
					mAdapter.notifyDataSetChanged();
					break;
				}
			}
		});
	}

	protected void setRefreshLayoutListener() {
		// 下拉加载监听器
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

						swiperefreshlayout.setRefreshing(false);
					}
				}, 1500);
			}
		});

		// 上拉加载监听器
		swiperefreshlayout.setOnLoadListener(new OnLoadListener() {

			@Override
			public void onLoad() {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						swiperefreshlayout.setLoading(false);
					}
				}, 1500);

			}
		});
	}

	class AppAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mAppList.size();
		}

		@Override
		public ApplicationInfo getItem(int position) {
			return mAppList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getViewTypeCount() {
			// menu type count
			return 3;
		}

		@Override
		public int getItemViewType(int position) {
			// current menu type
			return position % 3;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity()
						.getApplicationContext(), R.layout.item_list_app, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ApplicationInfo item = getItem(position);
			holder.iv_icon.setImageDrawable(item.loadIcon(getActivity()
					.getPackageManager()));
//			BitmapDrawable bd = (BitmapDrawable) item.loadIcon(getActivity()
//					.getPackageManager());
//			holder.iv_icon.setImageURI(Uri.parse(MediaStore.Images.Media
//					.insertImage(getActivity().getContentResolver(),
//							bd.getBitmap(), null, null)));
			// holder.iv_icon.setImageDrawable(item.loadIcon(getActivity()
			// .getPackageManager()));
			holder.tv_name.setText(item.loadLabel(getActivity()
					.getPackageManager()));
			return convertView;
		}

		class ViewHolder {
			SimpleDraweeView iv_icon;
			TextView tv_name;

			public ViewHolder(View view) {
				iv_icon = (SimpleDraweeView) view.findViewById(R.id.iv_icon);
				tv_name = (TextView) view.findViewById(R.id.tv_name);
				view.setTag(this);
			}
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}
