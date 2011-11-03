package com.ghost.app;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	public static final String PARAM = "activity_create_param";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 加载参数
		Bundle paramBundle = savedInstanceState;
		if (null == savedInstanceState)
		{
			paramBundle = getIntent().getExtras();
		}
        if (null != paramBundle)
        {
        	onCreate(paramBundle.getSerializable(PARAM));
        }
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		// 保存构建参数
		outState.putSerializable(PARAM, getCreateParam());
	}
	
	protected void onCreate(Serializable param)
	{
		
	}
	
	protected Serializable getCreateParam()
	{
		return null;
	}

}
