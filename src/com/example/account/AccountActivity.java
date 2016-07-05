package com.example.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aaa.MainActivity;
import com.example.aaa.R;

public class AccountActivity extends Activity {
	private int others,money,CreditCard,bankbook,bankCard,allMoneyInt,allMoneyInt2 ,otherVmoney,CreCard,bookVcard;
	private TextView tvothers,tvmoney,tvCreditCard,tvbankbook,tvbankCard,allMoney,allMoney2,moneyT,crecardT,finance;

	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		db = this.openOrCreateDatabase("account.db", Context.MODE_PRIVATE, null);
		tvothers =(TextView) findViewById(R.id.acc_others_id);
		tvmoney =(TextView) findViewById(R.id.acc_money_id);
		tvCreditCard =(TextView) findViewById(R.id.acc_credCard_id);
		tvbankbook =(TextView) findViewById(R.id.acc_bankbook_id);
		tvbankCard =(TextView) findViewById(R.id.acc_bankcard_id);
		allMoney = (TextView) findViewById(R.id.acc_allmoney_id);
		allMoney2 = (TextView) findViewById(R.id.acc_allmoney2_id);
		moneyT = (TextView) findViewById(R.id.acc_all_moneyt_id);
		crecardT = (TextView) findViewById(R.id.acc_all_crecard_id);
		finance = (TextView) findViewById(R.id.acc_all_finance_id);
		Cursor cursor1 = db.rawQuery("select in_money from earning where acc_name_type=?", new String[]{"其他"});
		Cursor cursor2 = db.rawQuery("select in_money from earning where acc_name_type=?", new String[]{"现金"});
		Cursor cursor3 = db.rawQuery("select in_money from earning where acc_name_type=?", new String[]{"信用卡"});
		Cursor cursor4 = db.rawQuery("select in_money from earning where acc_name_type=?", new String[]{"存折"});
		Cursor cursor5 = db.rawQuery("select in_money from earning where acc_name_type=?", new String[]{"银行卡"});
		//得到其他	
		if(cursor1 != null){
				while(cursor1.moveToNext()){
					others += cursor1.getInt(0);
					
				}
				tvothers.setText(others+"");
			}//得到现金
			if(cursor2 != null){
				while(cursor2.moveToNext()){
					money += cursor2.getInt(0);
					
				}
				tvmoney.setText(money+"");
			}//得到信用卡
			if(cursor3 != null){
				while(cursor3.moveToNext()){
					CreditCard += cursor3.getInt(0);
					
				}
				tvCreditCard.setText(CreditCard+"");
			}//得到存折
			if(cursor4 != null){
				while(cursor4.moveToNext()){
					bankbook += cursor4.getInt(0);
					System.out.println("存折="+bankbook);
				}
				tvbankbook.setText(bankbook+"");
			}//得到银行卡
			if(cursor5 != null){
				while(cursor5.moveToNext()){
					bankCard += cursor5.getInt(0);
					
				}
				tvbankCard.setText(bankCard+"");
			}
			allMoneyInt = money + CreditCard + others+ bankbook+bankCard;
			allMoneyInt2 =allMoneyInt;
			otherVmoney = others +money;
			moneyT.setText(otherVmoney+"");
			crecardT.setText(CreditCard+"");
			bookVcard = bankbook + bankCard;
			finance.setText(bookVcard+"");
			allMoney.setText(allMoneyInt+"");
			allMoney2.setText(allMoneyInt2+"");
	}
	public void goBack(View view){
		Intent intent = new Intent(AccountActivity.this,MainActivity.class);
		startActivity(intent);
	}
}
