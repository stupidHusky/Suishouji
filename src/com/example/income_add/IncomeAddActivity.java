package com.example.income_add;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaa.R;
import com.example.income.IncomeActivity;
import com.example.pays.PayActivity;

public class IncomeAddActivity extends Activity implements OnClickListener {
	private ImageView iv1;
	private TextView tv1;
	private Button btn1, btn2, btn3;
	private EditText etMain,et1, et2, et3, et4, et5;
	private int count = 1;
	private String type;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_add);
		db = this.openOrCreateDatabase("account.db", Context.MODE_PRIVATE, null);
		iv1 = (ImageView) findViewById(R.id.addincome_return1_id);
		tv1 = (TextView) findViewById(R.id.addincom_return_id);
		btn1 = (Button) findViewById(R.id.income_btn1_id);
		btn2 = (Button) findViewById(R.id.income_btn2_id);
		btn3 = (Button) findViewById(R.id.income_btn3_id);
		etMain = (EditText) findViewById(R.id.incomeadd_main_et_id);
		et1 = (EditText) findViewById(R.id.income_editText1);
		et2 = (EditText) findViewById(R.id.income_editText2);
		et3 = (EditText) findViewById(R.id.income_editText3);
		et4 = (EditText) findViewById(R.id.income_editText4);
		et5 = (EditText) findViewById(R.id.income_editText5);

		et2.setVisibility(View.INVISIBLE);
		et3.setVisibility(View.INVISIBLE);
		et4.setVisibility(View.INVISIBLE);
		et5.setVisibility(View.INVISIBLE);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		iv1.setOnClickListener(this);
		tv1.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.income_btn1_id:
			intiMain();
			intiChild();
			Toast.makeText(IncomeAddActivity.this, "保存成功", 1).show();
		case R.id.addincom_return_id:
			Intent intent = new Intent(IncomeAddActivity.this,
					IncomeActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.income_btn2_id:
			count++;
			if (count < 6) {
			  	switch (count) {
				case 2:
					et2.setVisibility(View.VISIBLE);
					break;
				case 3:
					et3.setVisibility(View.VISIBLE);
					break;
				case 4:
					et4.setVisibility(View.VISIBLE);
					break;
				case 5:
					et5.setVisibility(View.VISIBLE);
					break;
				}

			} else {
				count =5;
				Toast.makeText(IncomeAddActivity.this, "不能继续添加了", 1).show();
			}
		}
	}
	private void intiChild() {
		String etText1 = et1.getText().toString();
		String etText2 = et2.getText().toString();
		String etText3 = et3.getText().toString();
		String etText4 = et4.getText().toString();
		String etText5 = et5.getText().toString();
		 String[] ss = {etText1,etText2,etText3,etText4,etText5};	
		 for(int i = 0;i < count;i++){
		 ContentValues cv2 = new ContentValues();
		 cv2.put("in_name", ss[i]);
		 System.out.println("incomeadd="+ss[i]);
		  cv2.put("in_type", type);
		 db.insert("income", null, cv2);
		 }
	}

	private void intiMain() {
		 type = etMain.getText().toString();
		ContentValues cv = new ContentValues();
		cv.put("in_type",type);
		db.insert("income_type", null, cv);
	}
}
