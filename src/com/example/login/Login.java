package com.example.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aaa.MainActivity;
import com.example.aaa.R;


public class Login  extends Activity{
	private Button regist, log;
	private SQLiteDatabase db;
	private EditText etName, etPass;
	private String name, pass,dbName,dbPass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.longin);
		
		db = this.openOrCreateDatabase("account.db", Context.MODE_PRIVATE,null);
		String sql = "create table  if not exists persons(_id int,name text,password text)";
		db.execSQL(sql);
		etName = (EditText) findViewById(R.id.log_et1_id);
		String reName = getIntent().getStringExtra("reName");
		etName.setText(reName);
		regist = (Button) findViewById(R.id.login_re_id);
		log = (Button) findViewById(R.id.login_log_id);
		log.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				etPass = (EditText) findViewById(R.id.log_et2_id);
				
				
				 name  = etName.getText().toString();
				 pass = etPass.getText().toString();
				
				Cursor cursor = db.rawQuery("select name, password from persons", new String[]{});
				if(cursor != null){
					while(cursor.moveToNext()){
					dbName = cursor.getString(cursor.getColumnIndex("name"));
					dbPass = cursor.getString(cursor.getColumnIndex("password"));
					
					if(dbName.equals(name) && dbPass.equals(pass)){
						Toast.makeText(Login.this, "登录成功", 1).show();
						Intent intent = new Intent(Login.this,MainActivity.class);
						intent.putExtra("name", name);
						intent.putExtra("pass", pass);
						startActivity(intent);
						finish();
						return;
					}else{
						
					}
				}
					Toast.makeText(Login.this, "密码错误", 1).show();
				}
			}
		});
		regist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Login.this,Regist.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
