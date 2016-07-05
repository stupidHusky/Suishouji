package com.example.bill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.aaa.R;

public class ExpandAdapter extends BaseExpandableListAdapter {
	private SQLiteDatabase db;
	private List<Map<String, String>> group;
	private List<List<Student>> child;
	private List<MyChildItemView> childItem;
	private Context context;
	private LayoutInflater mInflater;

	public ExpandAdapter(Context context, List<Map<String, String>> group,
			List<List<Student>> child) {
		super();
		mInflater = LayoutInflater.from(context);
		this.group = group;
		this.context = context;
		this.child = child;

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean arg2, View convertView, ViewGroup parent) {
		MyView mv = new MyView();
		convertView = mInflater.inflate(R.layout.expand, null);
		mv.name = (TextView) convertView.findViewById(R.id.expand_tv1_id);
		mv.type = (TextView) convertView.findViewById(R.id.expand_tv2_id);
		mv.money = (TextView) convertView.findViewById(R.id.expand_tv3_id);
		mv.name.setText(child.get(groupPosition).get(childPosition).getName());
		mv.type.setText(child.get(groupPosition).get(childPosition).getType());
		mv.money.setText(child.get(groupPosition).get(childPosition).getMoney());
	
		convertView.setTag(mv);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return child.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean arg1,
			View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.bill_item, null);
		
		
		MyGroupView mg = new MyGroupView();
		mg.months = (TextView) convertView.findViewById(R.id.billitem_tv_id1);
		mg.pays = (TextView) convertView.findViewById(R.id.billitem_tv_id2);
		mg.incomes = (TextView) convertView.findViewById(R.id.billitem_tv_id3);
		mg.lasts = (TextView) convertView.findViewById(R.id.billitem_tv_id4);
		mg.days = (TextView) convertView.findViewById(R.id.billitem_tv_id5);
		mg.paysText = (TextView) convertView.findViewById(R.id.billitem_tv_id6);
		mg.incomesText = (TextView) convertView
				.findViewById(R.id.billitem_tv_id7);
		mg.lastsText = (TextView) convertView
				.findViewById(R.id.billitem_tv_id8);
		for (int i = 0; i < group.get(groupPosition).size(); i++) {
			mg.months.setText(group.get(groupPosition).get("months"));
			mg.pays.setText(group.get(groupPosition).get("pays"));
			mg.incomes.setText(group.get(groupPosition).get("incomes"));
			mg.lasts.setText(group.get(groupPosition).get("lasts"));
			mg.days.setText(group.get(groupPosition).get("days"));
			mg.paysText.setText(group.get(groupPosition).get("paysText"));
			mg.incomesText.setText(group.get(groupPosition).get("incomesText"));
			mg.lastsText.setText(group.get(groupPosition).get("lastsText"));
		}
		convertView.setTag(mg);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

	class MyView {
		TextView type;
		TextView name;
		TextView money;
	}

	class MyChildItemView {

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getMoney() {
			return money;
		}

		public void setMoney(int money) {
			this.money = money;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public MyChildItemView(String name, int money, String type) {
			super();
			this.name = name;
			this.money = money;
			this.type = type;
		}

		String name;
		int money;
		String type;
	}

	class MyGroupView {
		// { "months", "pays", "incomes", "lasts", "days",
		// "paysText", "incomesText", "lastsText" };
		TextView months;
		TextView pays;
		TextView incomes;
		TextView lasts;
		TextView days;
		TextView paysText;
		TextView incomesText;
		TextView lastsText;

	}
}
