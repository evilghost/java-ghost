package com.ghost.async;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 重写UI线程中的回调方法，将回调转发给反馈对象*/
public abstract class AsyncTask<Params, Progress, Result> 
	extends android.os.AsyncTask<Params, Progress, Result> 
{
	public static interface Feedback<Progress, Result> {
		public void onPreExecute();
		public void onProgressUpdate(Progress... values);
		public void onPostExecute(Result result);
		public void onCancelled();
		public void registerTask(AsyncTask<?, Progress, Result> task);
		public void unregisterTask(AsyncTask<?, Progress, Result> task);
	}
	public static abstract class FeedbackBase<Progress, Result>
		implements Feedback<Progress, Result>
	{
		protected ArrayList<AsyncTask<?, Progress, Result>> m_tasksArrayList = new ArrayList<AsyncTask<?, Progress, Result>>();
		public void onPreExecute(){}
		public void onProgressUpdate(Progress... values){}
		public void onPostExecute(Result result){}
		public void onCancelled(){}
		public void registerTask(AsyncTask<?, Progress, Result> task)
		{
			m_tasksArrayList.add(task);
		}
		public void unregisterTask(AsyncTask<?, Progress, Result> task)
		{
			m_tasksArrayList.remove(task);
		}
		
		public void cancelAll(boolean mayInterruptIfRunning)
		{
			ArrayList<AsyncTask<?, Progress, Result>> tempArrayList = m_tasksArrayList;
			m_tasksArrayList = new ArrayList<AsyncTask<?, Progress, Result>>();
			for(Iterator<AsyncTask<?, Progress, Result>> items = tempArrayList.iterator(); items.hasNext();)
			{
				  items.next().cancel(mayInterruptIfRunning);
			}
		}
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
	
	public boolean setFeedback(Feedback<Progress, Result> feedback)
	{
		if (Status.RUNNING == getStatus())
		{
			return false;
		}
		if (null != feedback)
		{
			m_feedbackReference = new WeakReference<Feedback<Progress, Result>>(feedback);
		}
		else
		{
			m_feedbackReference = null;
		}
		return true;
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
			m_feedbackReference.get().registerTask(this);
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
			m_feedbackReference.get().unregisterTask(this);
			m_feedbackReference.get().onPostExecute(result);
		}
	}
	
	@Override
	protected void onCancelled()
	{
		// TODO Auto-generated method stub
		if (null == m_feedbackReference
			|| null == m_feedbackReference.get())
		{
		}
		else 
		{
			m_feedbackReference.get().unregisterTask(this);
			m_feedbackReference.get().onCancelled();
		}
	}
}
