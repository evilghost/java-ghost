package com.ghost.test;

import java.io.Serializable;

import com.ghost.app.BaseActivity;

public class MainActivity extends BaseActivity 
{
	
	@Override
	protected void onCreate(Serializable param)
	{
		// TODO Auto-generated method stub
		super.onCreate(param);
		
		setContentView(R.layout.main);
	}
	
}
