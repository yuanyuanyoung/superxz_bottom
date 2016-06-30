package com.dh.superxz_bottom.view;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 通过以下几个属性设置EditText的四个方位的图片显示
 * 
 * android:drawableLeft对应的资源获取getCompoundDrawables()[0];
 * 
 * android:drawableTop对应的资源获取getCompoundDrawables()[1];
 * 
 * android:drawableRight对应的资源获取getCompoundDrawables()[2];
 * 
 * android:drawableBottom对应的资源获取getCompoundDrawables()[3];
 * 
 * 重写EditText为drawableRight设置是否显示，以及点击事件
 * 
 * @author Scw
 *
 */
public class CustomEdittext extends EditText {
    private final static String TAG = "Scw_CustomEdittext";
    private Drawable left;
    private Drawable top;
    private Drawable right;
    private Drawable bottom;
    // private Drawable imgAble;
    private Context mContext;

    public CustomEdittext(Context context) {
	super(context);
	mContext = context;
	init();
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	mContext = context;
	init();
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
	super(context, attrs);
	mContext = context;
	init();
    }

    private void init() {
	left = getCompoundDrawables()[0] == null ? null : this
		.getCompoundDrawables()[0];
	top = getCompoundDrawables()[1] == null ? null : this
		.getCompoundDrawables()[1];
	right = this.getCompoundDrawables()[2] == null ? mContext
		.getResources().getDrawable(
			R.drawable.ic_menu_close_clear_cancel) : this
		.getCompoundDrawables()[2];
	bottom = getCompoundDrawables()[3] == null ? null : this
		.getCompoundDrawables()[3];
	// imgAble = mContext.getResources().getDrawable(R.drawable.delete);
	// setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
	addTextChangedListener(new TextWatcher() {
	    @Override
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		setDrawable();
	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
	    }

	    @Override
	    public void afterTextChanged(Editable s) {
	    }
	});
	setDrawable();
    }

    // 设置drawableRight是否显示
    private void setDrawable() {
	if (length() < 1)
	    setCompoundDrawablesWithIntrinsicBounds(left, top, null, bottom);
	else
	    setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    // 处理删除事件
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
	if (right != null && event.getAction() == MotionEvent.ACTION_UP) {
	    int eventX = (int) event.getRawX();
	    int eventY = (int) event.getRawY();
	    Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
	    Rect rect = new Rect();
	    getGlobalVisibleRect(rect);
	    rect.left = rect.right - 50;
	    if (rect.contains(eventX, eventY))
		setText("");
	}
	return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
	super.finalize();
    }

}
