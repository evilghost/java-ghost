package com.ghost.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ghost.async.AsyncTask;

class TestTask extends AsyncTask<Void, Integer, Void>
{
	public TestTask(Feedback<Integer, Void> feedback)
	{
		// TODO Auto-generated constructor stub
		super(feedback);
	}
	
	@Override
	protected Void doInBackground(Void... params)
	{
		// TODO Auto-generated method stub
		int i = 0;
		while (true)
		{
			publishProgress(new Integer(++i));
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
		return null;
	}
}

public class MainActivity extends Activity 
{
	public static String S_TAG = "test";
	
	private TestTask.FeedbackBase<Integer, Void> m_testFeedback;

	public MainActivity()
	{
		// TODO Auto-generated constructor stub
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    CreateTestFeedback();
	    
		new TestTask(m_testFeedback).execute((Void)null);
	}
	
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		m_testFeedback.cancelAll(true);
		
		DestroyTestFeedback();
	}
	
	private void CreateTestFeedback()
	{
		m_testFeedback = new TestTask.FeedbackBase<Integer, Void>(){
			@Override
			public void onPreExecute()
			{
				// TODO Auto-generated method stub
				printMethodName();
			}

			@Override
			public void onProgressUpdate(Integer... values)
			{
				// TODO Auto-generated method stub
				printMethodName();
				Log.v(S_TAG, String.format("%d", values[0]));
			}

			@Override
			public void onPostExecute(Void result)
			{
				// TODO Auto-generated method stub
				printMethodName();
			}
			
			@Override
			public void onCancelled()
			{
				// TODO Auto-generated method stub
				printMethodName();
			}
			
			@Override
			public void registerTask(AsyncTask<?, Integer, Void> task)
			{
				// TODO Auto-generated method stub
				super.registerTask(task);
				printMethodName();
			}
			
			@Override
			public void unregisterTask(AsyncTask<?, Integer, Void> task)
			{
				// TODO Auto-generated method stub
				super.unregisterTask(task);
				printMethodName();
			}
			
			private void printMethodName()
			{
				Log.v(S_TAG, new Exception().getStackTrace()[1].getMethodName());
			}
		};
	}
	private void DestroyTestFeedback()
	{
		m_testFeedback = null;
	}

}
