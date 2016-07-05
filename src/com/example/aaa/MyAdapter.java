package com.example.aaa;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter{

	private List allData;

	//此构造函数用来传递数据
	//集合中的每一个数据就代表ListView中的一行
	public MyAdapter(List allData) {
		super();
		this.allData = allData;
	}

	@Override
	public int getCount() {
		return allData == null ? 0 : allData.size(); //列表项的个数 返回几 就有几行
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater li = LayoutInflater.from(parent.getContext());
		View view = li.inflate(R.layout.item,null);
		/*
				TextView tv = (TextView)view.findViewById(一个Id代表View中的视图比如TextView);
				tv.setText(allData.get(position).toString);
		 
		 */
		return view ;
	}

}
