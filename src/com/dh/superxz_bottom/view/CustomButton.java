package com.dh.superxz_bottom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.dh.superxz_bottom.R;

/**
 * 重写Button,自定义Button样式 可在代码中或者XML文件中设置按钮的形状（是否圆角） 设置背景 设置文字颜色
 * 设置点击效果(color支持String和Resources ID)等
 * 
 * @author Scw
 * @date 2015-12-1
 */
public class CustomButton extends Button {
    private boolean fillet = false;// 是否设置圆角
    private float radius = 8;// 圆角半径
    private int shape = 0;// 圆角样式，矩形、圆形等，由于矩形的Id为0，默认为矩形
    private int backColori = 0;// 背景色，int类型
    private int backColorSelectedi = 0;// 按下后的背景色，int类型
    private int backGroundImage = 0;// 背景图，只提供了Id
    private int backGroundImageSeleted = 0;// 按下后的背景图，只提供了Id
    private int textColori = 0;// 文字颜色，int类型
    private int textColorSeletedi = 0;// 按下后的文字颜色，int类型
    private String backColors = "";// 背景色，String类型
    private String textColors = "";// 文字颜色，String类型
    private String textColorSeleteds = "";// 按下后的文字颜色，String类型
    private String backColorSelecteds = "";// 按下后的背景色，String类型
    private GradientDrawable gradientDrawable;// 控件的样式

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	init(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs) {
	this(context, attrs, 0);
	init(context, attrs);
    }

    public CustomButton(Context context) {
	this(context, null);
    }

    @SuppressLint({ "ClickableViewAccessibility", "Recycle" })
    private void init(Context context, AttributeSet attrs) {
	// 设置Touch事件
	setOnTouchListener(new OnTouchListener() {
	    @Override
	    public boolean onTouch(View arg0, MotionEvent event) {
		// 按下改变样式
		setColor(event, arg0);
		return true;
	    }
	});
	/*
	 * 使用resalues/attrs.xml中的<declare- styleable>定义的Gallery属性.
	 */
	/* 取得CustomButon属性的Index id */
	TypedArray typearray = context.obtainStyledAttributes(attrs,
		R.styleable.CustomButton);
	/* 让对象的styleable属性能够反复使用 */
	setBackColor(typearray.getColor(
		R.styleable.CustomButton_customBackground, 0));
	setBackColorSelected(typearray.getColor(
		R.styleable.CustomButton_clickeffect, 0));
	setFillet(typearray.getBoolean(R.styleable.CustomButton_fillet, fillet));
	setTextColori(typearray.getColor(
		R.styleable.CustomButton_customTextColor, Color.WHITE));
	setTextColorSelected(typearray.getColor(
		R.styleable.CustomButton_textclickeffect, 0));
	setRadius(typearray.getFloat(R.styleable.CustomButton_customRadius,
		radius));

	// 将Button的默认背景色改为透明（透明色的资源ID无法获取）
	if (gradientDrawable == null) {
	    gradientDrawable = new GradientDrawable();
	}
	if (0 == typearray.getInt(R.styleable.CustomButton_customBackground, 0)) {
	    gradientDrawable.setColor(Color.TRANSPARENT);
	} else {
	    setBackColor(typearray.getInt(
		    R.styleable.CustomButton_customBackground, 0));
	}
	// 设置文字默认居中
	setGravity(Gravity.CENTER);
	typearray.recycle();
    }

    public boolean setColor(MotionEvent event, View arg0) {
	if (MotionEvent.ACTION_DOWN == event.getAction()) {
	    // 按下
	    if (0 != backColorSelectedi) {
		// 先判断是否设置了按下后的背景色int型
		if (fillet) {
		    if (gradientDrawable == null) {
			gradientDrawable = new GradientDrawable();
		    }
		    gradientDrawable.setColor(backColorSelectedi);
		} else {
		    setBackgroundColor(backColorSelectedi);
		}
	    } else if (!TextUtils.isEmpty(backColorSelecteds)) {
		if (fillet) {
		    if (gradientDrawable == null) {
			gradientDrawable = new GradientDrawable();
		    }
		    gradientDrawable.setColor(Color
			    .parseColor(backColorSelecteds));
		} else {
		    setBackgroundColor(Color.parseColor(backColorSelecteds));
		}
	    }
	    // 判断是否设置了按下后文字的颜色
	    if (0 != textColorSeletedi) {
		setTextColor(textColorSeletedi);
	    } else if (!textColorSeleteds.equals("")) {
		setTextColor(Color.parseColor(textColorSeleteds));
	    }
	    // 判断是否设置了按下后的背景图
	    if (0 != backGroundImageSeleted) {
		setBackgroundResource(backGroundImageSeleted);
	    }
	}
	if (event.getAction() == MotionEvent.ACTION_UP) {
	    // 抬起
	    if (0 == backColori && TextUtils.isEmpty(backColors)) {
		// 如果没有设置背景色，默认改为透明
		if (fillet) {
		    if (gradientDrawable == null) {
			gradientDrawable = new GradientDrawable();
		    }
		    gradientDrawable.setColor(Color.TRANSPARENT);
		} else {
		    setBackgroundColor(Color.TRANSPARENT);
		}
	    } else if (0 != backColori) {
		if (fillet) {
		    if (gradientDrawable == null) {
			gradientDrawable = new GradientDrawable();
		    }
		    gradientDrawable.setColor(backColori);
		} else {
		    setBackgroundColor(backColori);
		}
	    } else {
		if (fillet) {
		    if (gradientDrawable == null) {
			gradientDrawable = new GradientDrawable();
		    }
		    gradientDrawable.setColor(Color.parseColor(backColors));
		} else {
		    setBackgroundColor(Color.parseColor(backColors));
		}
	    }
	    // 如果为设置字体颜色，默认为白色
	    if (0 == textColori && TextUtils.isEmpty(textColors)) {
		setTextColor(Color.WHITE);
	    } else if (textColori != 0) {
		setTextColor(textColori);
	    } else {
		setTextColor(Color.parseColor(textColors));
	    }
	    if (backGroundImage != 0) {
		setBackgroundResource(backGroundImage);
	    }
	}
	return arg0.onTouchEvent(event);

    }

    /**
     * 设置按钮的背景色,如果未设置则默认为透明
     * 
     * @param backColor
     */
    public void setBackColor(String backColor) {
	this.backColors = backColor;
	if (backColor.equals("")) {
	    if (fillet) {
		if (gradientDrawable == null) {
		    gradientDrawable = new GradientDrawable();
		}
		gradientDrawable.setColor(Color.TRANSPARENT);
	    } else {
		setBackgroundColor(Color.TRANSPARENT);
	    }
	} else {
	    if (fillet) {
		if (gradientDrawable == null) {
		    gradientDrawable = new GradientDrawable();
		}
		gradientDrawable.setColor(Color.parseColor(backColor));
	    } else {
		setBackgroundColor(Color.parseColor(backColor));
	    }
	}
    }

    /**
     * 设置按钮的背景色,如果未设置则默认为透明
     * 
     * @param backColor
     */
    public void setBackColor(int backColor) {
	this.backColori = backColor;
	if (backColori == 0) {
	    if (fillet) {
		if (gradientDrawable == null) {
		    gradientDrawable = new GradientDrawable();
		}
		gradientDrawable.setColor(Color.TRANSPARENT);
	    } else {
		setBackgroundColor(Color.TRANSPARENT);
	    }
	} else {
	    if (fillet) {
		if (gradientDrawable == null) {
		    gradientDrawable = new GradientDrawable();
		}
		gradientDrawable.setColor(backColor);
	    } else {
		setBackgroundColor(backColor);
	    }
	}
    }

    /**
     * 设置按钮按下后的颜色
     * 
     * @param backColorSelected
     */
    public void setBackColorSelected(int backColorSelected) {
	this.backColorSelectedi = backColorSelected;
    }

    /**
     * 设置按钮按下后的颜色
     * 
     * @param backColorSelected
     */
    public void setBackColorSelected(String backColorSelected) {
	this.backColorSelecteds = backColorSelected;
    }

    /**
     * 设置按钮的背景图
     * 
     * @param backGroundImage
     */
    public void setBackGroundImage(int backGroundImage) {
	this.backGroundImage = backGroundImage;
	if (backGroundImage != 0) {
	    setBackgroundResource(backGroundImage);
	}
    }

    /**
     * 设置按钮按下的背景图
     * 
     * @param backGroundImageSeleted
     */
    public void setBackGroundImageSeleted(int backGroundImageSeleted) {
	this.backGroundImageSeleted = backGroundImageSeleted;
    }

    /**
     * 设置按钮圆角半径大小
     * 
     * @param radius
     */
    public void setRadius(float radius) {
	if (gradientDrawable == null) {
	    gradientDrawable = new GradientDrawable();
	}
	gradientDrawable.setCornerRadius(radius);
    }

    /**
     * 设置按钮文字颜色
     * 
     * @param textColor
     */
    public void setTextColors(String textColor) {
	this.textColors = textColor;
	setTextColor(Color.parseColor(textColor));
    }

    /**
     * 设置按钮文字颜色
     * 
     * @param textColor
     */
    public void setTextColori(int textColor) {
	this.textColori = textColor;
	setTextColor(textColor);
    }

    /**
     * 设置按钮按下的文字颜色
     * 
     * @param textColor
     */
    public void setTextColorSelected(String textColor) {
	this.textColorSeleteds = textColor;
    }

    /**
     * 设置按钮按下的文字颜色
     * 
     * @param textColor
     */
    public void setTextColorSelected(int textColor) {
	this.textColorSeletedi = textColor;
    }

    /**
     * 按钮的形状
     * 
     * @param shape
     */
    public void setShape(int shape) {
	this.shape = shape;
    }

    /**
     * 设置其是否为圆角
     * 
     * @param fillet
     */
    @SuppressWarnings("deprecation")
    public void setFillet(Boolean fillet) {
	this.fillet = fillet;
	if (fillet) {
	    if (gradientDrawable == null) {
		gradientDrawable = new GradientDrawable();
	    }
	    // GradientDrawable.RECTANGLE
	    gradientDrawable.setShape(shape);
	    gradientDrawable.setCornerRadius(radius);
	    setBackgroundDrawable(gradientDrawable);
	}
    }
}