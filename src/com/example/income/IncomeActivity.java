package com.example.income;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaa.MainActivity;
import com.example.aaa.R;
import com.example.income_add.IncomeAddActivity;
import com.example.pays.PayActivity;

public class IncomeActivity extends Activity implements OnClickListener {
	// 数据库处理
	private SQLiteDatabase db;
	private List ss, income_types;
	private ImageView payPreIv;
	private Spinner sp1, sp2, account1, account2;
	private TextView tv1, tvBack, accountArrow; // tv1 = 添加里面的+号；tvBack返回主界面
	private Button btn1, btn2, btn3;
	private boolean isSaved = false;
	private EditText etMoney, etComment;
	private String money, type1, type2, acc1, acc2, comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income);

		db = this
				.openOrCreateDatabase("account.db", Context.MODE_PRIVATE, null);
		Cursor cursor = db
				.query("acc_type", null, null, null, null, null, null);
		ss = new ArrayList();
		while (cursor.moveToNext()) {
			String s1 = cursor
					.getString(cursor.getColumnIndex("acc_type_name"));
			System.out.println(s1);
			ss.add(s1);
		}
		Cursor cursor2 = db.query("income_type", null, null, null, null, null,
				null);
		income_types = new ArrayList();
		while (cursor2.moveToNext()) {
			String s1 = cursor2.getString(cursor2.getColumnIndex("in_type"));
			System.out.println("数据查询的结果=" + s1);
			income_types.add(s1);
		}

		sp1 = (Spinner) findViewById(R.id.income_sp_id);
		sp2 = (Spinner) findViewById(R.id.income_sp_id2);
		account1 = (Spinner) findViewById(R.id.income_otheraccount1_id);
		account2 = (Spinner) findViewById(R.id.income_otheraccount2_id);
		tv1 = (TextView) findViewById(R.id.incom_add_id);
		accountArrow = (TextView) findViewById(R.id.income_otheraccount2text_id);
		tvBack = (TextView) findViewById(R.id.income_back_id);
		account2.setVisibility(View.INVISIBLE);
		accountArrow.setVisibility(View.INVISIBLE);
		btn1 = (Button) findViewById(R.id.incomeBut1ID);
		btn2 = (Button) findViewById(R.id.incomeBut2ID);
		btn3 = (Button) findViewById(R.id.incomeBut3ID);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);

		accountArrow.setOnClickListener(this);
		tvBack.setOnClickListener(this);
		tv1.setOnClickListener(this);

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, income_types);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(aa);

		payPreIv = (ImageView) findViewById(R.id.pay_pre_id);
		sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			// selectIndext =被选择的spinner的下标
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int selectIndext, long arg3) {
				String s2 = (String) income_types.get(selectIndext);
				type1 = s2;
				Cursor cursor = db.rawQuery(
						"select in_name from income where in_type = ? ",
						new String[] { s2 });
				List accountNames = new ArrayList();
				while (cursor.moveToNext()) {
					String accs = cursor.getString(cursor
							.getColumnIndex("in_name"));
					accountNames.add(accs);
				}
				ArrayAdapter<String> aaa = new ArrayAdapter<String>(
						IncomeActivity.this,
						android.R.layout.simple_spinner_item, accountNames);
				aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp2.setAdapter(aaa);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		payPreIv.setOnClickListener(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ss);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		account1.setAdapter(adapter);
		account1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int accountIndext, long arg3) {
				accountArrow.setVisibility(View.VISIBLE);
				account2.setVisibility(View.VISIBLE);
				String s1 = (String) ss.get(accountIndext);
				acc1 = s1;

				Cursor cursor = db.rawQuery(
						"select acc_name from account where acc_type = ? ",
						new String[] { s1 });
				List accountNames = new ArrayList();
				while (cursor.moveToNext()) {
					String accs = cursor.getString(cursor
							.getColumnIndex("acc_name"));
					accountNames.add(accs);
				}
				ArrayAdapter<String> acc = new ArrayAdapter<String>(
						IncomeActivity.this,
						android.R.layout.simple_spinner_item, accountNames);
				acc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				account2.setAdapter(acc);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		init();
		init2();
	}

	private void init() {
		etMoney = (EditText) findViewById(R.id.income_money_id);
		etComment = (EditText) findViewById(R.id.income_comment_id);

		// 得到第一个spinner的值

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_pre_id:
			Intent intent = new Intent(IncomeActivity.this, PayActivity.class);
			startActivity(intent);
			break;
		case R.id.incom_add_id:
			Intent intent2 = new Intent(IncomeActivity.this,
					IncomeAddActivity.class);
			startActivity(intent2);
			finish();
			break;
		case R.id.income_back_id:
			Intent intent3 = new Intent(IncomeActivity.this, MainActivity.class);
			startActivity(intent3);
			finish();
			break;
		case R.id.incomeBut1ID:
			isSaved = true;
			money = etMoney.getText().toString();
			comment = etComment.getText().toString();
			ContentValues cv = new ContentValues();
			cv.put("in_money", money);
			cv.put("in_type", type1);
			cv.put("in_name", type2);
			cv.put("acc_type", acc1);
			cv.put("acc_name_type", acc2);
			cv.put("comment", comment);
			long a = System.currentTimeMillis();
			cv.put("savetime", a);
			if (money.length() != 0) {
				db.insert("earning", null, cv);
				Toast.makeText(IncomeActivity.this, "保存成功", 1).show();
			} else {
				Toast.makeText(IncomeActivity.this, "保存失败，请检查收入数字", 1).show();
			}

			break;
		case R.id.incomeBut3ID:
			if (!isSaved) {
				AlertDialog.Builder buider = new AlertDialog.Builder(this);
				buider.setTitle("你还没有保存，是否保存？");
				buider.setPositiveButton("是",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						});
				buider.setNegativeButton("否",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						});
				buider.create();
				buider.show();
			} else {
				Intent intentBacks = new Intent(IncomeActivity.this,
						IncomeActivity.class);
				startActivity(intentBacks);
			}

		}
	}

	private void init2() {

		// 得到第二个spinner的值
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int indext, long arg3) {
				type2 = sp2.getItemAtPosition(indext).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		// 得到第三个spinner的值

		// 得到第四个spinner的值
		account2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int indext, long arg3) {
				acc2 = account2.getItemAtPosition(indext).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}
}
