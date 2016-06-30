package com.dh.superxz_bottom.yinzldemo;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.dhutils.datepicker.DatePicker;
import com.dh.superxz_bottom.dhutils.datepicker.TimePicker;

/**
 * @author czo
 * @Date: 15-12-11
 * @Time: 上午10:26
 * @Description:日期控件
 */
public class DatePickerActivity extends VehicleActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView timeView;
    private Button submitView;

    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_date_picker);
	mCalendar = Calendar.getInstance();

	datePicker = (DatePicker) findViewById(R.id.datePicker);
	timePicker = (TimePicker) findViewById(R.id.timePicker);
	timeView = (TextView) findViewById(R.id.time_view);
	submitView = (Button) findViewById(R.id.get_time_btn);

	submitView.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		mCalendar.set(Calendar.YEAR, datePicker.getYear());
		mCalendar.set(Calendar.MONTH, datePicker.getMonth());
		mCalendar.set(Calendar.DAY_OF_MONTH, datePicker.getDay());
		mCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHourOfDay());
		mCalendar.set(Calendar.MINUTE, timePicker.getMinute());
		timeView.setText(mCalendar.getTime().toLocaleString());
	    }
	});

    }
}