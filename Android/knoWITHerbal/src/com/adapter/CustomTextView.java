package com.adapter;

import com.config.Config;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * @author Kevin Jimenez Omiple
 * 
 * omiple.kevin@gmail.com
 *
 * Any replication codes without citation of the author aforementioned
 * is a direct violation of ownership rights of the author.
 *
 *
 */
public class CustomTextView extends TextView{

	public CustomTextView(Context context) {
		super(context);
		this.setTypeface(Config.globalFont(context));
		// TODO Auto-generated constructor stub
	}
	
	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setTypeface(Config.globalFont(context));
		// TODO Auto-generated constructor stub
	}
	
	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setTypeface(Config.globalFont(context));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

}
