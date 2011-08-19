package com.ghost.async;

import java.lang.ref.WeakReference;

/**
 * 重写UI线程中的回调方法，将回调转发给反馈对象*/
public abstract class AsyncTask<Params, Progress, Result> 
	extends android.os.AsyncTask<Params, Progress, Result> 
{
	public static interface Feedback<Progress, Result> {
		public void onPreExecute();
		public void onProgressUpdate(Progress... values);
		public void onPostExecute(Result result);
	}
	protected WeakReference<Feedback<Progress, Result>> m_feedbackReference;
	
	public AsyncTask()
	{
		
	}
	
	public AsyncTask(Feedback<Progress, Result> feedback)
	{
		// TODO Auto-generated constructor stub
		setFeedback(feedback);
	}
	
	public void setFeedback(Feedback<Progress, Result> feedback)
	{
		if (null != feedback)
		{
			m_feedbackReference = new WeakReference<Feedback<Progress, Result>>(feedback);
		}
		else
		{
			m_feedbackReference = null;
		}
	}

	@Override
	protected final void onPreExecute()
	{
		// TODO Auto-generated method stub
		if (null == m_feedbackReference
			|| null == m_feedbackReference.get())
		{
			cancel(false);
		}
		else 
		{
			m_feedbackReference.get().onPreExecute();
		}
	}
	
	@Override
	protected final void onProgressUpdate(Progress... values)
	{
		if (null == m_feedbackReference
			|| null == m_feedbackReference.get())
		{
			cancel(false);
		}
		else 
		{
			m_feedbackReference.get().onProgressUpdate(values);
		}
	}
	
	@Override
	protected final void onPostExecute(Result result) 
	{
		if (null == m_feedbackReference
			|| null == m_feedbackReference.get())
		{
			cancel(false);
		}
		else 
		{
			m_feedbackReference.get().onPostExecute(result);
		}
	}
}
