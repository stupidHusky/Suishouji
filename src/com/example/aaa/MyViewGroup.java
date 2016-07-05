package com.example.aaa;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyViewGroup  extends ViewGroup{
	private View loginView;
	private View mainView;
	private ImageView ivTou ;
	
	public View getLoginView() {
		return loginView;
	}
	public View getMainView() {
		return mainView;
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		loginView = getChildAt(0);
		mainView = getChildAt(1);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		loginView.measure(loginView.getLayoutParams().width, heightMeasureSpec);
		mainView.measure(widthMeasureSpec, heightMeasureSpec);
		
	}
	@Override
	protected void onLayout(boolean change, int l, int t, int r, int b) {
		loginView.layout(-loginView.getLayoutParams().width, 0, 0, b);
		mainView.layout(0, 0, r, b);
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewGroup(Context context) {
		super(context);
	}

}
