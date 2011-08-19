package com.ghost.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ghost.async.AsyncTask;

class TestTask extends AsyncTask<Void, Void, Void>
{
	public TestTask(Feedback<Void, Void> feedback)
	{
		// TODO Auto-generated constructor stub
		super(feedback);
	}
	
	@Override
	protected Void doInBackground(Void... params)
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; ++i)
		{
			publishProgress((Void)null);
		}
		return null;
	}
}

public class MainActivity 
	extends Activity 
	implements TestTask.Feedback<Void, Void>
{
	public static String S_TAG = "test";

	public MainActivity()
	{
		// TODO Auto-generated constructor stub
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
		new TestTask(this).execute((Void)null);
	}

	@Override
	public void onPreExecute()
	{
		// TODO Auto-generated method stub
		printMethodName();
	}

	@Override
	public void onProgressUpdate(Void... values)
	{
		// TODO Auto-generated method stub
		printMethodName();
	}

	@Override
	public void onPostExecute(Void result)
	{
		// TODO Auto-generated method stub
		printMethodName();
	}
	
	private static void printMethodName()
	{
		Log.v(S_TAG, new Exception().getStackTrace()[1].getMethodName());
	}

}
