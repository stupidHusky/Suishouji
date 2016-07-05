package com.example.bill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aaa.MainActivity;
import com.example.aaa.R;

public class BillActivity extends Activity implements OnClickListener {
	private List<Map<String, String>> list = null;

	private ExpandableListView elv;
	private ImageView back;
	private SQLiteDatabase db;
	private TextView allpays, allIncomes, allLasts;
	private String[] months = new String[12];
	private String[] days = new String[12];
	private String[] pays = new String[12];
	private String[] incomes = new String[12];
	private String[] lasts = new String[12];
	private String[] paysText = new String[12];
	private String[] incomesText = new String[12];
	private String[] lastsText = new String[12];
	private List[] aList = new List[12];
	private int year ;
	
	private int monthMoney, paYmonthMoney, allPay, allIcome;
	private List<List<Student>> ls;
	private Spinner spYear;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill);
		spYear = (Spinner) findViewById(R.id.bill_year_s_id);
		back = (ImageView) findViewById(R.id.bill_back_id);
		elv = (ExpandableListView) findViewById(R.id.bill_listView_id);
		allIncomes = (TextView) findViewById(R.id.all_income_id);
		allpays = (TextView) findViewById(R.id.all_pay_id);
		allLasts = (TextView) findViewById(R.id.all_last_id);
		for(int i = 0; i< 12;i++){
			
			aList[i] = new ArrayList<Student>();
		}
		back.setOnClickListener(this);
		
		spYear.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				year = 2015- arg2;
				System.out.println("year"+year);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		ls = new ArrayList<List<Student>>();
		list = new ArrayList<Map<String,String>>();
		
		db = this.openOrCreateDatabase("account.db", Context.MODE_PRIVATE, null);

		for (int i = 0; i < months.length; i++) {
			boolean isAdd = true;

			months[i] = (i + 1) + "月";
			if ((i + 1) == 1 || (i + 1) == 3 || (i + 1) == 5 || (i + 1) == 7
					|| (i + 1) == 8 || (i + 1) == 10 || (i + 1) == 12) {
				days[i] = (i + 1) + "." + "1" + "-" + (i + 1) + "." + "31";
			} else if ((i + 1) == 2) {
				days[i] = (i + 1) + "." + "1" + "-" + (i + 1) + "." + "28";
			} else {
				days[i] = (i + 1) + "." + "1" + "-" + (i + 1) + "." + "30";
			}
			// 得到每个月的收入
			Cursor InCursor = db.rawQuery("select savetime from earning ",
					new String[] {});
			if (InCursor != null) {
				while (InCursor.moveToNext()) {
					long a = InCursor.getLong(InCursor.getColumnIndex("savetime"));
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(a);
					// 如果月份匹配则叠加收入金额
					
					if (calendar.get(Calendar.MONTH) == i) {
						Cursor cursor2 = db.rawQuery("select in_money,in_name,in_type from earning where savetime = ? ",
								new String[] { a + "" });

						if (cursor2 != null) {
							int monthMoneyN = 0;
							String in_name= null;
							
							while (cursor2.moveToNext()) {
								int bbb = cursor2.getInt(cursor2.getColumnIndex("in_money"));
								
								 monthMoneyN +=	 bbb ;
								String in_type = cursor2.getString(cursor2.getColumnIndex("in_type"));
								
								Student stu = new Student("收入",in_type,bbb+"");
								aList[i].add(stu);
								
							}
						
							monthMoney += monthMoneyN;
							incomes[i] = monthMoney + "";
							allIcome += monthMoneyN;
						}

					} else {
						incomes[i] = "0.00";
						if(isAdd){
							Student stu = new Student("无","无",0+"");
							aList[i].add(stu);
							isAdd = false;
						}
						
					
					
					
					}
				}
			}
			// 得到支出
			Cursor payCursor = db.rawQuery("select savetime from pays ",
					new String[] {});
			if (payCursor != null) {
				while (payCursor.moveToNext()) {
					long a = payCursor.getLong(payCursor
							.getColumnIndex("savetime"));
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(a);
					// 如果月份匹配则叠加收入金额
					int paYmonthMoneyN = 0;
					if (calendar.get(Calendar.MONTH) == i) {
						Cursor cursor2 = db
								.rawQuery(
										"select pay_money,pay_people from pays where savetime = ? ",
										new String[] { a + "" });

						if (cursor2 != null) {
							Map<String,String> li = new HashMap<String,String>();
							while (cursor2.moveToNext()) {
								// 得到一个月的支出
								int aaa = cursor2.getInt(cursor2
										.getColumnIndex("pay_money"));
								paYmonthMoneyN += aaa;
								String s = cursor2.getString(cursor2.getColumnIndex("pay_people"));
							Student stu = new Student("支出",s,aaa+"");
							aList[i].add(stu);

							}
//							ls.add(li);
							paYmonthMoney += paYmonthMoneyN;
							pays[i] = paYmonthMoney + "";
							allPay += paYmonthMoneyN;
						} else {
							pays[i] = "0.00";
							//Map<String,String> li = new HashMap<String,String>();
//							li.put("text","无");
//							ls.add(li);
						}

						if (!incomes[i].equals("0.00")
								&& !pays[i].equals("0.00")) {

							lasts[i] = (monthMoney - paYmonthMoney) + "";
						} else {
							lasts[i] = "-" + paYmonthMoney;
						}
					} else {
						pays[i] = "0.00";
						if (!pays[i].equals("0.00")) {
							lasts[i] = monthMoney + "";
						} else {
							lasts[i] = "0.00";
						}
					}
				}
			}

			paysText[i] = "支出";
			incomesText[i] = "收入";
			lastsText[i] = "结余";
		}

		
		for (int i = 0; i < 12; i++) {
		
			Map<String, String> map = new HashMap<String, String>();
			map.put("months", months[i]);
			map.put("pays", pays[i]);
			map.put("incomes", incomes[i]);
			map.put("lasts", lasts[i]);
			map.put("days", days[i]);
			map.put("paysText", paysText[i]);
			map.put("incomesText", incomesText[i]);
			map.put("lastsText", lastsText[i]);
			
			for(int f = 0;f<aList[i].size();f++){
				Iterator it = aList[i].iterator();
				while(it.hasNext()){
					System.out.println(i+"遍历的结果"+it.next());
				}
			}
			ls.add(aList[i]);
			
			
			//ls.add(aa);
			list.add(map);
			allpays.setText(allPay+"");
			allIncomes.setText(allIcome+"");
			allLasts.setText((allIcome - allPay)+"");
		}

		ExpandAdapter ea = new ExpandAdapter(BillActivity.this, list, ls);
		elv.setAdapter(ea);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bill_back_id:
			Intent intent = new Intent(BillActivity.this, MainActivity.class);
			startActivity(intent);
		}
	}

}
