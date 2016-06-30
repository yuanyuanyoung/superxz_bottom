package com.dh.superxz_bottom.framework.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dh.superxz_bottom.R;

/**
 * 提示对话框
 * 
 * @author afei
 * 
 */
public class TipDialog extends Dialog implements android.view.View.OnClickListener {

    private Button btn_ok;

    private TextView tv_content;

    public TipDialog(Context context) {
        super(context, R.style.dialog_tip);
    }

    public TipDialog(Context context, int theme) {
        super(context, R.style.dialog_tip);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_dialog_tip);

        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);

        tv_content = (TextView) findViewById(R.id.tv_content);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }

    public void showTip(int msg) {
        show();
        tv_content.setText(msg);
    }

    public void showTip(String msg) {
        show();
        tv_content.setText(msg);
    }

    public void setMessage(int msg) {
        tv_content.setText(msg);
    }

    public void onClick(View v) {
        dismiss();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        /*if (keyCode == KeyEvent.KEYCODE_HOME) {  //not use,is symbol 
        }*/
        return super.onKeyDown(keyCode, event);
    }
}
