package com.dh.superxz_bottom.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.adapter.ArrayListAdapter;
import com.dh.superxz_bottom.adapter.ViewHolder;
import com.dh.superxz_bottom.alibaba.fastjson.JSON;
import com.dh.superxz_bottom.entity.HrVOBean;
import com.dh.superxz_bottom.framework.log.L;
import com.dh.superxz_bottom.framework.net.exception.DataException;
import com.dh.superxz_bottom.framework.net.fgview.Action;
import com.dh.superxz_bottom.framework.net.fgview.BaseParser;
import com.dh.superxz_bottom.framework.net.fgview.OnResponseListener;
import com.dh.superxz_bottom.framework.net.fgview.Request;
import com.dh.superxz_bottom.framework.net.fgview.Response;
import com.dh.superxz_bottom.framework.net.fgview.Response.ErrorMsg;
import com.dh.superxz_bottom.framework.util.Density;
import com.dh.superxz_bottom.handmark.pulltorefresh.library.PullToRefreshBase;
import com.dh.superxz_bottom.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.dh.superxz_bottom.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.dh.superxz_bottom.handmark.pulltorefresh.library.PullToRefreshSwipteListView;
import com.dh.superxz_bottom.handmark.pulltorefresh.library.SwipeListView;
import com.dh.superxz_bottom.xutils.sample.utils.ToastUtil;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;


/**
 * @author 超级小志
 *
 */
public class ListFragment extends Fragment implements OnClickListener,
		OnItemClickListener {
	private ListView xlv_messages;
	private HRAdapter mMessageAdapter;
	private ArrayList<HrVOBean> mHrList;

	private boolean isUpRefresh = false;

	private int mCurrentPage = 1;
	private static final int PAGE_SIZE = 15;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_fragment, container, false);
		ViewUtils.inject(this, view);
		initView();
		return view;
	}

	private void initView() {

		pslv_message.setMode(Mode.PULL_FROM_START);
		xlv_messages = pslv_message.getRefreshableView();
		((SwipeListView) xlv_messages).setRightViewWidth(Density.of(
				getActivity(), 80));
		xlv_messages.setSelector(new ColorDrawable(0));

		mHrList = new ArrayList<HrVOBean>();
		mMessageAdapter = new HRAdapter(getActivity());
		mMessageAdapter.setList(mHrList);
		xlv_messages.setAdapter(mMessageAdapter);

		pslv_message
				.setOnRefreshListener(new OnRefreshListener2<SwipeListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<SwipeListView> refreshView) {
						isUpRefresh = true;
						mCurrentPage = 1;
						// executeHttp(mCurrentPage);
						queryCouponListByOrderId(true);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<SwipeListView> refreshView) {
						isUpRefresh = false;
						mCurrentPage += 1;
						queryCouponListByOrderId(true);
						// executeHttp(mCurrentPage);
					}
				});
		xlv_messages.setOnItemClickListener(this);
		pslv_message.setRefreshing();
	}

	private void queryCouponListByOrderId(boolean dialog) {

		// 结果解析parser
		// GetQueryListParser parser = new GetQueryListParser();
		// 请求parser
		Request<List<HrVOBean>> req = new Request<List<HrVOBean>>();
		req.setRequestMethod(Request.M_GET);
		req.setBaseParser(new BaseParser<List<HrVOBean>>() {

			@Override
			public List<HrVOBean> parseResDate(String resBody)
					throws DataException {
				if (resBody != null && !resBody.equals("")) {
					return JSON.parseArray(resBody, HrVOBean.class);
				}
				return null;
			}
		});
		HashMap<String, String> params = new HashMap<String, String>();

		// params.put("page", page);
		// params.put("size", size);
		req.setUrl("http://10.202.1.39:8080/hr/ListServlet");
		req.setRequestParams(params);

		Action action = new Action(getActivity());
		if (!dialog) {
			action.setDefaultLoadingTipOpen(false);
		}
		action.execute(req, new OnResponseListener<List<HrVOBean>>() {
			@Override
			public void onResponseFinished(Request<List<HrVOBean>> request,
					Response<List<HrVOBean>> response) {
				List<HrVOBean> resList = new ArrayList<HrVOBean>();
				resList = response.getT();
				ToastUtil.showLong(getActivity(), "list size is :"
						+ resList.get(0).toString());
				L.e("yinzl", "获取到的第一个数据为：" + resList.get(0).toString());
				if (null == resList || resList.size() <= 0) {
					if (!isUpRefresh) {
						mCurrentPage--;
					} else {
						mHrList.clear();
					}
				} else {
					if (isUpRefresh) {// 是上拉，插入到前页
						mHrList.clear();
					}
					mHrList.addAll(resList);
				}
				mMessageAdapter.notifyDataSetChanged();
				pslv_message.onRefreshComplete();
				if (null == resList || (resList.size() < PAGE_SIZE)) {
					pslv_message.setPullLoadEnabled(false);
				} else {
					pslv_message.setPullLoadEnabled(true);
				}
				setEmptyView();
			}

			@Override
			public void onResponseDataError(Request<List<HrVOBean>> equest) {
				// TODO Auto-generated method stub
				ToastUtil.showLong(getActivity(), "请求失败");
				if (mCurrentPage != 1) {
					mCurrentPage--;
				}
				pslv_message.onRefreshComplete();
				setEmptyView();
			}

			@Override
			public void onResponseConnectionError(
					Request<List<HrVOBean>> request, int statusCode) {
				ToastUtil.showLong(getActivity(), "请求失败");
				if (mCurrentPage != 1) {
					mCurrentPage--;
				}
				pslv_message.onRefreshComplete();
				setEmptyView();
			}

			@Override
			public void onResponseFzzError(Request<List<HrVOBean>> request,
					ErrorMsg errorMsg) {
				ToastUtil.showLong(getActivity(), "请求失败");
				if (mCurrentPage != 1) {
					mCurrentPage--;
				}
				pslv_message.onRefreshComplete();
				setEmptyView();
				// btnController(true);
				// if (page.equals(page0)) {
				// lv_voucher.setVisibility(View.GONE);
				// ll_no_data.setVisibility(View.VISIBLE);
				// }
			}

		});
	}

	private void setEmptyView() {
		if (null == pslv_message.getEmptyView()) {
			View view = View.inflate(getActivity(), R.layout.empty, null);
			((TextView) view.findViewById(R.id.tv_no)).setText("暂无数据");
			pslv_message.setEmptyView(view);
		}
	}

	@ViewInject(R.id.pxlv_person)
	private PullToRefreshSwipteListView pslv_message;

	class HRAdapter extends ArrayListAdapter<HrVOBean> {

		public HRAdapter(Activity context) {
			super(context);
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.home_item_notification, null);
			}

			final ImageView iv_icon = ViewHolder.get(convertView, R.id.iv_icon);
			TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
			TextView tv_content = ViewHolder.get(convertView, R.id.tv_content);
			TextView tv_datetime = ViewHolder
					.get(convertView, R.id.tv_datetime);
			Button btn_delete = ViewHolder.get(convertView, R.id.btn_delete);

			final HrVOBean bean = mList.get(position);
			if (bean != null) {

				tv_title.setText(bean.getReward_title());
				tv_content.setText("    " + bean.getReward_welfare());
				tv_datetime.setText(bean.getSub_address());
				btn_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					}
				});
				if (0 != convertView.getScrollX()) {
					convertView.scrollTo(0, 0);
				}
			}
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
