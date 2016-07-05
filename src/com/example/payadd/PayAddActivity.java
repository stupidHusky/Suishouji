package com.example.payadd;

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
import com.example.income_add.IncomeAddActivity;
import com.example.pays.PayActivity;

public class PayAddActivity extends Activity implements OnClickListener{
	private ImageView iv1;
	private TextView tv1;
	
	private Button btn1, btn2, btn3;
	private EditText et1, et2, et3, et4, et5;
	private int count = 1;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_pay);
		
		iv1 = (ImageView) findViewById(R.id.addpay_return1_id);
		tv1 = (TextView) findViewById(R.id.addpay_return_id);
		btn1 = (Button) findViewById(R.id.addpay_btn1_id);
		btn2 = (Button) findViewById(R.id.addpay_btn2_id);
		btn3 = (Button) findViewById(R.id.addpay_btn3s_id);
		
		et1 = (EditText) findViewById(R.id.addpay_editText1);
		et2 = (EditText) findViewById(R.id.addpay_editText2);
		et3 = (EditText) findViewById(R.id.addpay_editText3);
		et4 = (EditText) findViewById(R.id.addpay_editText4);
		et5 = (EditText) findViewById(R.id.addpay_editText5);

		et2.setVisibility(View.INVISIBLE);
		et3.setVisibility(View.INVISIBLE);
		et4.setVisibility(View.INVISIBLE);
		et5.setVisibility(View.INVISIBLE);
		btn2.setOnClickListener(this);
		iv1.setOnClickListener(this);
		tv1.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addpay_return1_id:
		case R.id.addpay_return_id:
			Intent intent = new Intent(PayAddActivity.this,
					PayActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.addpay_btn1_id:
			
			
			
			
		case R.id.addpay_btn2_id:
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
				count = 5;
				Toast.makeText(PayAddActivity.this, "不能继续添加了", 1).show();
			}
		}
	}

	
}
