package com.dh.superxz_bottom.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.dh.superxz_bottom.R;

/**
 * 上拉加载更多的swiperefreshlayout
 */
public class RefreshLayout extends SwipeRefreshLayout implements
		OnScrollListener {
	/**
	 * 滑动到最下面时的上拉操作
	 */

	private int mTouchSlop;
	/**
	 * listview实例
	 */
	private ListView mListView;

	/**
	 * 上拉监听器, 到了最底部的上拉加载操作
	 */
	private OnLoadListener mOnLoadListener;

	/**
	 * ListView的加载中footer
	 */
	private View mListViewFooter;

	private static final int TOUCH_STATE_NONE = 0;
	// private static final int TOUCH_STATE_X = 1;
	private static final int TOUCH_STATE_Y = 2;
	private float mDownY;
	// private float mDownX;
	private int mTouchState;

	private int MAX_Y = 5;
	private int MAX_X = 3;

	/**
	 * 是否在加载中 ( 上拉加载更多 )
	 */
	private boolean isLoading = false;

	/**
	 * @param context
	 */
	public RefreshLayout(Context context) {
		this(context, null);
		init();
	}

	public RefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		mListViewFooter = LayoutInflater.from(context).inflate(
				R.layout.listview_footer, null, false);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		// 初始化ListView对象
		if (mListView == null) {
			getListView();
		}
	}

	private void init() {
		MAX_Y = dp2px(MAX_Y);
		MAX_X = dp2px(MAX_X);
		mTouchState = TOUCH_STATE_NONE;
	}

	/**
	 * 获取ListView对象
	 */
	private void getListView() {
		int childs = getChildCount();
		if (childs > 0) {
			View childView = getChildAt(0);
			if (childView instanceof ListView) {
				mListView = (ListView) childView;
				// 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
				mListView.setOnScrollListener(this);
				// Log.d(VIEW_LOG_TAG, "### 找到listview");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			// mYDown = (float) event.getX();
			// mXDown = (int) event.getRawX();
			mDownY = event.getY();
			// mDownX = event.getX();
			mTouchState = TOUCH_STATE_NONE;
			break;

		case MotionEvent.ACTION_MOVE:
			// 移动
			// mLastY = (float) event.getX();
			// mLastX = (int) event.getRawX();

			if ((mDownY - event.getY()) >= mTouchSlop) {
				mTouchState = TOUCH_STATE_Y;
			}
			break;

		case MotionEvent.ACTION_UP:
			// 抬起
			if (canLoad()) {
				loadData();
			}
			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	/**
	 * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
	 * 
	 * @return
	 */
	private boolean canLoad() {
		return isBottom() && !isLoading && isPullUp();
	}

	/**
	 * 判断是否到了最底部
	 */
	private boolean isBottom() {

		if (mListView != null && mListView.getAdapter() != null) {
			return mListView.getLastVisiblePosition() == (mListView
					.getAdapter().getCount() - 1);
		}
		return false;
	}

	/**
	 * 是否是上拉操作
	 * 
	 * @return
	 */
	private boolean isPullUp() {
		// L.e("wang", "mTouchState=" + mTouchState);

		// return (mLastY - mYDown) >= mTouchSlop;
		return (mTouchState == TOUCH_STATE_Y);
	}

	/**
	 * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
	 */
	private void loadData() {
		if (mOnLoadListener != null) {
			// 设置状态
			setLoading(true);
			//
			mOnLoadListener.onLoad();
		}
	}

	/**
	 * @param loading
	 */
	public void setLoading(boolean loading) {
		isLoading = loading;
		try {
			if (isLoading) {
				if (null != mListView && null != mListViewFooter)
					mListView.addFooterView(mListViewFooter);
			} else {
				if (null != mListView && null != mListViewFooter)
					mListView.removeFooterView(mListViewFooter);
				// mLastY = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mDownY = 0;
		mTouchState = TOUCH_STATE_NONE;
	}

	/**
	 * @param loadListener
	 */
	public void setOnLoadListener(OnLoadListener loadListener) {
		mOnLoadListener = loadListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.setEnabled(true);
		// 滚动时到了最底部也可以加载更多
		if (canLoad()) {
			loadData();
		}
	}

	/**
	 * 加载更多的监听器
	 * 
	 * @author mrsimple
	 */
	public static interface OnLoadListener {
		public void onLoad();
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getContext().getResources().getDisplayMetrics());
	}
}