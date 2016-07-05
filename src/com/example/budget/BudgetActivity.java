package com.example.budget;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaa.MainActivity;
import com.example.aaa.R;
import com.example.income.IncomeActivity;

public class BudgetActivity extends Activity implements OnClickListener {
	private ImageView nextIv, re;
	private SQLiteDatabase db;
	private EditText et;
	private ImageView head;
	private TextView allBudget, allPay, lastPay;
	private int pays;
	private boolean ifSave = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.budget);
		db = this
				.openOrCreateDatabase("account.db", Context.MODE_PRIVATE, null);
		et = (EditText) findViewById(R.id.budget_all_id);
		et.setEnabled(ifSave);
		head = (ImageView) findViewById(R.id.budget_head_id);
		head.setOnClickListener(this);
		re = (ImageView) findViewById(R.id.budget_return_id);
		re.setOnClickListener(this);
		allBudget = (TextView) findViewById(R.id.budget_all_id);
		allPay = (TextView) findViewById(R.id.budget_allpay_id);
		lastPay = (TextView) findViewById(R.id.buget_last_id);
		getBudget();

	}

	public int getBudget() {
		int a = 0;
		Cursor cursor = db.rawQuery("select sum(dress) from buget;",
				new String[] {});
		if (cursor != null) {
			if (cursor.moveToNext()) {
				a = cursor.getInt(0);
				allBudget.setText(a + "");
			}
		}
		Cursor cursor2 = db.rawQuery("select sum(pay_money) aa from pays",
				new String[] {});
		if(cursor2 != null){
		if (cursor2.moveToNext()) {
			pays = cursor2.getInt(cursor2.getColumnIndex("aa"));
			allPay.setText(pays+"");
		}
		}
		int last = a - pays;
		lastPay.setText(last + "");
		Intent intent = new Intent();
		intent.putExtra("last", last);
		return last;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_next_id:
			Intent intent = new Intent(BudgetActivity.this,
					IncomeActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.budget_return_id:
			Intent intent2 = new Intent(BudgetActivity.this, MainActivity.class);
			startActivity(intent2);
			finish();
			break;
		
			
		case R.id.budget_head_id:
			if (!ifSave) {
				ifSave = !ifSave;
				et.setEnabled(ifSave);
			} else {
				int a = Integer.parseInt(et.getText().toString());
				ContentValues cv = new ContentValues();
				cv.put("dress", a);
				db.insert("buget", null, cv);
				ifSave = !ifSave;
				et.setEnabled(ifSave);
				Toast.makeText(BudgetActivity.this, "已保存", 1).show();
			}
			break;
		
			
		}
	}
}
