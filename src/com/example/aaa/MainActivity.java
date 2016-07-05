package com.example.aaa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.account.AccountActivity;
import com.example.bill.BillActivity;
import com.example.budget.BudgetActivity;
import com.example.login.Login;
import com.example.more.MoreActivity;
import com.example.pays.PayActivity;

public class MainActivity extends Activity implements OnClickListener {
	private SQLiteDatabase db;
	private ImageView ivTou;
	private ExpandableListView elv;
	private TextView allIncome, allPay, allLast, today, tvDayMoney,
			tvWeekMoney, tvMonthMoney, iNtvDayMoney, iNtvWeekMoney,
			iNtvMonthMoney,loginFrameName;
	private Button jiyibi;
	private String incomes, pays;
	private ImageView b1, b2, b3, b5,b6;// b1 返回 b3账户 b5 预算 b6更多
	private MyViewGroup mvg;
	private boolean isShow = false;
	private int payYear, payMonth, payDay, inYear, inMonth, inDay;
	private int years;
	private int month;
	private int day;
	private int dayMoney;
	private int weekMoney;
	private int monthMoney;
	private int INdayMoney;
	private int INweekMoney;
	private int INmonthMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myv_view_group);

		ivTou = (ImageView) findViewById(R.id.login_tou_id);
		ivTou.setOnClickListener(this);
		String sql = "create table T_Account_Group(_id int primary key, Group_Name text,Group_Desc text,Last_UpdTime long, Delete_Flag int)";
		db = this
				.openOrCreateDatabase("account.db", Context.MODE_PRIVATE, null);
		creatDb();

		Cursor cursor = db.rawQuery("select sum(in_money) aa from earning",
				new String[] {});
		if (cursor != null) {
			while (cursor.moveToNext()) {
				incomes = cursor.getString(cursor.getColumnIndex("aa"));
				allIncome = (TextView) findViewById(R.id.all_income_id);
				allIncome.setText(incomes);
			}
		}
		Cursor cursor2 = db.rawQuery("select sum(pay_money) aa from pays",
				new String[] {});
		if (cursor2 != null) {
			while (cursor2.moveToNext()) {
				pays = cursor2.getString(cursor2.getColumnIndex("aa"));
				allPay = (TextView) findViewById(R.id.all_pay_id);
				allPay.setText(pays);
			}
		}
		allLast = (TextView) findViewById(R.id.all_last_id);

		getLast();// 设置预算余额度的方法
		loginFrameName = (TextView) findViewById(R.id.login_frame_name_id);
		
		jiyibi = (Button) findViewById(R.id.main_jiyibi_id);
		allIncome = (TextView) findViewById(R.id.all_income_id);
		mvg = (MyViewGroup) findViewById(R.id.myViewGroup1);
		b1 = (ImageView) findViewById(R.id.b1);
		b2 = (ImageView) findViewById(R.id.b2);
		b3 = (ImageView) findViewById(R.id.b3);
		b5 = (ImageView) findViewById(R.id.b5);
		b6 = (ImageView) findViewById(R.id.b6);
		today = (TextView) findViewById(R.id.main_date_id2);
		intiTime();
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);

		jiyibi.setOnClickListener(this);
		getPayAndInTime();
	}

	private void creatDb() {
		db.execSQL("create table if not exists acc_type(_id int primary key,acc_type_name text)");
		db.execSQL("create table if not exists account (_id int primary key, acc_name text,acc_type text references acc_type_name(acc_type),acc_comment text)");
		db.execSQL("create table if not exists income_type(_id int primary key,in_type text)");
		db.execSQL("create table if not exists income(_id int primary key,in_name text,in_type text references in_type(income_type),int_comment text)");
		db.execSQL("create table if not exists earning(_id int primary key,in_money int,in_type text ,in_name text, acc_type text, acc_name_type text,comment text,savetime long)");
		db.execSQL("create table if not exists pays(_id int primary key, pay_money int,  pay_type text,pay_people text,comment text,savetime long)");
		db.execSQL("create table if not exists  buget(_id int,dress int,food int,home int,travle int,chatting int,hobbily int)");

	}

	private void getPayAndInTime() {
		Cursor cursor = db.rawQuery("select savetime from pays ",
				new String[] {});
		System.out.println("第一步通过");
		List list = new ArrayList();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				long a = cursor.getLong(cursor.getColumnIndex("savetime"));
				System.out.println("得到的long时间=" + a);
				Calendar calendar = Calendar.getInstance();
				int day = calendar.get(Calendar.DAY_OF_YEAR);// 今天的
				System.out.println("今天=" + day);
				calendar.setTimeInMillis(a);
				System.out.println("数据哭存储的天="
						+ calendar.get(Calendar.DAY_OF_YEAR));
				if (calendar.get(Calendar.DAY_OF_YEAR) == day) {
					System.out.println("进入");
					Cursor cursor2 = db.rawQuery(
							"select pay_money from pays where savetime = ? ",
							new String[] { a + "" });

					if (cursor2 != null) {
						while (cursor2.moveToNext()) {
							dayMoney += cursor2.getInt(cursor2
									.getColumnIndex("pay_money"));
							System.out.println("dayMoney" + dayMoney);
						}
						tvDayMoney = (TextView) findViewById(R.id.day_money_id);
						tvDayMoney.setText(dayMoney + "");
					}

				}
				// 12月
				if (calendar.get(Calendar.MONTH) == 11) {
					Cursor cursor2 = db.rawQuery(
							"select pay_money from pays where savetime = ? ",
							new String[] { a + "" });

					if (cursor2 != null) {
						while (cursor2.moveToNext()) {
							monthMoney += cursor2.getInt(cursor2
									.getColumnIndex("pay_money"));
						}
						tvMonthMoney = (TextView) findViewById(R.id.year_money_id);
						tvMonthMoney.setText(monthMoney + "");
					}
				}
				if (calendar.get(Calendar.MONTH) == 11) {
					Cursor cursor2 = db.rawQuery(
							"select pay_money from pays where savetime = ? ",
							new String[] { a + "" });

					if (cursor2 != null) {
						while (cursor2.moveToNext()) {
							weekMoney += cursor2.getInt(cursor2
									.getColumnIndex("pay_money"));
						}
						tvWeekMoney = (TextView) findViewById(R.id.month_money_id);
						tvWeekMoney.setText(weekMoney + "");
					}
				}
			}
		}

		// 设置收入的表内容
		Cursor InCursor = db.rawQuery("select savetime from earning ",
				new String[] {});
		System.out.println("第一步通过");
		if (InCursor != null) {
			while (InCursor.moveToNext()) {
				long a = InCursor.getLong(InCursor.getColumnIndex("savetime"));
				System.out.println("得到的long时间=" + a);
				Calendar calendar = Calendar.getInstance();
				int day = calendar.get(Calendar.DAY_OF_YEAR);
				calendar.setTimeInMillis(a);
				if (calendar.get(Calendar.DAY_OF_YEAR) == day) {
					Cursor cursor2 = db.rawQuery(
							"select in_money from earning where savetime = ? ",
							new String[] { a + "" });

					if (cursor2 != null) {
						while (cursor2.moveToNext()) {
							INdayMoney += cursor2.getInt(cursor2
									.getColumnIndex("in_money"));
							System.out.println("INdayMoney" + INdayMoney);
						}
						iNtvDayMoney = (TextView) findViewById(R.id.day_in_id);
						iNtvDayMoney.setText(INdayMoney + "");
					}

				}
				// 12月
				if (calendar.get(Calendar.MONTH) == 11) {
					Cursor cursor2 = db.rawQuery(
							"select in_money from earning where savetime = ? ",
							new String[] { a + "" });

					if (cursor2 != null) {
						while (cursor2.moveToNext()) {
							INmonthMoney += cursor2.getInt(cursor2
									.getColumnIndex("in_money"));
						}
						iNtvMonthMoney = (TextView) findViewById(R.id.month_in_id);
						iNtvMonthMoney.setText(INmonthMoney + "");
					}
				}
				if (calendar.get(Calendar.MONTH) == 11) {
					Cursor cursor2 = db.rawQuery(
							"select in_money from earning where savetime = ? ",
							new String[] { a + "" });

					if (cursor2 != null) {
						while (cursor2.moveToNext()) {
							INweekMoney += cursor2.getInt(cursor2
									.getColumnIndex("in_money"));
						}
						iNtvWeekMoney = (TextView) findViewById(R.id.week_in_id);
						iNtvWeekMoney.setText(INweekMoney + "");
					}
				}
			}
		}
	}

	private void getLast() {
		int a = 0;
		int b = 0;
		Cursor cursor = db.rawQuery(
				"select sum(dress) from buget;",
				new String[] {});
		if (cursor != null) {

			if (cursor.moveToNext()) {
				a = cursor.getInt(0);
			}
		}
		Cursor cursor2 = db.rawQuery("select sum(pay_money) aa from pays",
				new String[] {});
		if (cursor2 != null) {
			if (cursor2.moveToNext()) {
				int payss = cursor2.getInt(cursor2.getColumnIndex("aa"));
				b = payss;
			}
			int c = a - b;
			allLast.setText(c + "");

		}
	}

	private void intiTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		years = calendar.get(Calendar.YEAR);
		int a = calendar.get(Calendar.WEEK_OF_MONTH);
		int b = calendar.get(Calendar.DAY_OF_WEEK);

		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		int firstDay = day - b + 1;

		int lastDay = day - b + 7;
		today.setText(years + "年" + month + "月" + day + "日");
		TextView thisWeek = (TextView) findViewById(R.id.main_this_week_id);
		thisWeek.setText(month + "月" + firstDay + "日" + "-" + lastDay + "日");
		TextView thisMonth = (TextView) findViewById(R.id.main_month_id);
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			thisMonth.setText(month + "月" + 1 + "日" + "-" + 31 + "日");
		} else if (month == 2) {
			thisMonth.setText(month + "月" + 1 + "日" + "-" + 28 + "日");
		} else {
			thisMonth.setText(month + "月" + 1 + "日" + "-" + 30 + "日");
		}

	}

	class MyAnimation extends Animation {
		private MyViewGroup mv = null;
		private int distance = 0;

		public MyAnimation(MyViewGroup mv, int distance) {
			super();
			this.mv = mv;
			this.distance = distance;
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			mv.scrollTo((int) (distance * interpolatedTime), 0); // 达到补间动画效果 原理为
																	// 位移在interpolatedTime的时间里面逐步实现
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b1:
			
			String name = getIntent().getStringExtra("name");
			loginFrameName.setText(name+"");
			System.out.println("用户名："+ name);
			MyAnimation ma = null;
			if (!isShow) {
				ma = new MyAnimation(mvg, -280);
				ma.setDuration(500);
				RotateAnimation ra = new RotateAnimation(0, -180,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				ra.setDuration(500);
				ra.setFillAfter(true);
				b1.startAnimation(ra);
				mvg.startAnimation(ma);

			} else {
				RotateAnimation ra = new RotateAnimation(-180, 0,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				ra.setDuration(500);
				ra.setFillAfter(true);
				b1.startAnimation(ra);
				ma = new MyAnimation(mvg, 0);
				ma.setDuration(500);
				mvg.startAnimation(ma); // 未解决问题，
										// 当点击重新关闭的时候没有动画效果，因为点下面会默认返回原来的位置
			}
			isShow = !isShow;
			break;
		case R.id.main_jiyibi_id:
			Intent intent = new Intent(MainActivity.this, PayActivity.class);
			startActivity(intent);
			break;
		case R.id.b2:
			Intent intent2 = new Intent(MainActivity.this, BillActivity.class);
			startActivity(intent2);
			finish();
			break;
		case R.id.b3:
			Intent intent3 = new Intent(MainActivity.this,
					AccountActivity.class);
			startActivity(intent3);
			finish();
			break;
		case R.id.b5:
			Intent intent5 = new Intent(MainActivity.this, BudgetActivity.class);
			startActivity(intent5);
			finish();
			break;
		case R.id.b6:
			Intent intent6 = new Intent(MainActivity.this, MoreActivity.class);
			startActivity(intent6);
		
			break;
		case R.id.login_tou_id:
			Intent intentT = new Intent(MainActivity.this, Login.class);
			startActivity(intentT);
		}
	}
}
