package com.example.pays;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aaa.MainActivity;
import com.example.aaa.R;
import com.example.income.IncomeActivity;
import com.example.payadd.PayAddActivity;

public class PayActivity extends Activity implements OnClickListener {
	private ImageView nextIv;
	private TextView tv1, tv2, tvBack, add;// tv1日期，tv2时间
	private Button btn1, btn2, btn3;
	private int years;
	private int month;
	private int day;
	private int hourInstance;
	private int minInstance;
	private String people;
	private String type;
	private String money;
	private String comment;
	private SQLiteDatabase db;
	
	private EditText etMoney,etComment;
	private Spinner spTpye,spPeople;
	private boolean isSaved = false; // 监控是否保存

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		nextIv = (ImageView) findViewById(R.id.pay_next_id);
		db = this.openOrCreateDatabase("account.db",Context.MODE_PRIVATE, null);
		
		tv1 = (TextView) findViewById(R.id.pay_date_id);
		tv2 = (TextView) findViewById(R.id.pay_time_id);
		tvBack = (TextView) findViewById(R.id.pay_back_id);
		add = (TextView) findViewById(R.id.pay_addpay_id);

		btn1 = (Button) findViewById(R.id.payBut1ID);
		btn2 = (Button) findViewById(R.id.payBut2ID);
		btn3 = (Button) findViewById(R.id.payBut3ID);

		etMoney = (EditText) findViewById(R.id.pay_money_id);
		etComment = (EditText) findViewById(R.id.pay_comment_id);
		spTpye = (Spinner) findViewById(R.id.pay_type_id);
		spPeople = (Spinner) findViewById(R.id.pay_people_id);
		init();
		nextIv.setOnClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tvBack.setOnClickListener(this);
		add.setOnClickListener(this);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		years = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hourInstance = calendar.get(Calendar.HOUR_OF_DAY);
		minInstance = calendar.get(Calendar.MINUTE);
		tv1.setText(years + "年" + month + "月" + day + "日");
		tv2.setText(hourInstance + "时" + minInstance + "分");

	}// 时间选择器
	//初始化数据
	private void init() {
		
		spPeople.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int index, long arg3) {
				TextView tv = (TextView)view;
				System.out.println(tv.getText()+"tv");
				 people = spPeople.getItemAtPosition(index).toString();
				System.out.println("sp"+people);
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spTpye.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				TextView tv = (TextView) view;
				type = tv.getText().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
	}

	private void updateDate() {
		tv1.setText(years + "年" + month + "月" + day + "日");
	}

	private TimePickerDialog.OnTimeSetListener dot = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker arg0, int hour, int min) {
			hourInstance = hour;
			minInstance = min;
			updateTime();
		}

		private void updateTime() {
			tv2.setText(hourInstance + "时" + minInstance + "分");

		}
	};

	// 日期选择器
	private DatePickerDialog.OnDateSetListener dpo = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			years = year;
			month = monthOfYear + 1;
			day = dayOfMonth;
			updateDate();
		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_next_id:
			Intent intent = new Intent(PayActivity.this, IncomeActivity.class);
			startActivity(intent);
			break;
		case R.id.pay_date_id:
			DatePickerDialog dpd = new DatePickerDialog(PayActivity.this, dpo,
					years, month, day);
			dpd.show();
			break;
		case R.id.pay_time_id:
			TimePickerDialog tpd = new TimePickerDialog(PayActivity.this, dot,
					hourInstance, minInstance, true);
			tpd.show();
			break;
		case R.id.pay_addpay_id:
			Intent intentAdd = new Intent(PayActivity.this,
					PayAddActivity.class);
			startActivity(intentAdd);
			finish();
			break;
		case R.id.pay_back_id:
			Intent intentBack = new Intent(PayActivity.this, MainActivity.class);
			startActivity(intentBack);
			finish();
			break;
		case R.id.payBut1ID:
			isSaved = true;
			money = etMoney.getText().toString();
			comment  = etComment.getText().toString();
			ContentValues cv = new ContentValues();
			cv.put("pay_money",money);
			cv.put("pay_people", people);
			cv.put("pay_type", type);
			cv.put("comment", comment);
		
			Calendar c = Calendar.getInstance();
			c.set(years, month, day, hourInstance, minInstance);
			long a =c.getTimeInMillis();
			System.out.println("获取的选择的时间"+a);
			cv.put("savetime", a);
			if(money.length() != 0){
				
			db.insert("pays", null, cv);
			Toast.makeText(PayActivity.this, "保存成功", 1).show();
			}else{
				Toast.makeText(PayActivity.this, "请输入支出", 1).show();
			}
			break;
		case R.id.payBut3ID:
			if (!isSaved) {
				AlertDialog.Builder buider = new AlertDialog.Builder(this);
				buider.setTitle("你还没有保存，是否保存？");
				buider.setPositiveButton("是",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						});
				buider.setNegativeButton("否",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						});
				buider.create();
				buider.show();
			}
			else{
				Intent intentBacks = new Intent(PayActivity.this, PayActivity.class);
				startActivity(intentBacks);
				finish();
			}
		}
	}
}
