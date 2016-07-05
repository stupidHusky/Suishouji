package com.example.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aaa.R;

public class Regist extends Activity {
	private Button re;
	private EditText et1, et2, et3;
	private String s1, s2, s3;
	private SQLiteDatabase db;
	 boolean canRegist = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regiest);
		db = this
				.openOrCreateDatabase("account.db", Context.MODE_PRIVATE, null);
		re = (Button) findViewById(R.id.re_re_id);
		re.setOnClickListener(new View.OnClickListener() {
			boolean canRegist = true;
			@Override
			public void onClick(View arg0) {
				et1 = (EditText) findViewById(R.id.re_et1_id);
				et2 = (EditText) findViewById(R.id.re_et2_id);
				et3 = (EditText) findViewById(R.id.re_et3_id);
				s1 = et1.getText().toString();
				s2 = et2.getText().toString();
				s3 = et3.getText().toString();
				Cursor cursor = db.rawQuery(
						"select name, password from persons", new String[] {});
				if (cursor != null) {
					while (cursor.moveToNext()) {
						String name = cursor.getString(cursor
								.getColumnIndex("name"));
						if (s1.equals(name)) {
							canRegist = false;
							Toast.makeText(Regist.this, "用户名已存在，请重新输入", 1)
									.show();
							
						} else if (!s2.equals(s3)) {
							Toast.makeText(Regist.this, "密码不一致！", 1).show();
						}
					}
				} else {
					Regist();
				}
			if(canRegist){
				Regist();
			}
			}
			
			private void Regist() {

				ContentValues cv = new ContentValues();
				cv.put("name", s1);
				cv.put("password", s2);
				db.insert("persons", null, cv);
				Toast.makeText(Regist.this, "注册成功", 1).show();
				Intent intent = new Intent(Regist.this, Login.class);
				intent.putExtra("reName", s1);
				startActivity(intent);
				finish();

			}
		});
	}
}
