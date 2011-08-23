package com.ghost.async;

import java.util.Observable;

public abstract class DataAdapter<Data> extends Observable{
	public enum State{
		EMPTY,       // 空即无数据
		LOADING,     // 正在加载数据
		READY,       // 数据准备就绪
		LOAD_FAILED  // 数据加载失败
	}
	private Data m_data;
	private State m_state = State.EMPTY;
	
	public Data getDefaultData()
	{
		return null;
	}
	public State getState()
	{
		return m_state;
	}
	public Data getData()
	{
		return m_data;
	}
	public void loadData()
	{
		setState(State.LOADING);
		doLoadData();
	}
	
	protected void setState(final State state)
	{
		if (state != m_state)
		{
			m_state = state;
			notifyObservers();
		}
	}
	
	protected abstract void doLoadData();
	
	protected void onLoadData(final Data data)
	{
		if (null == data)
		{
			setState(State.LOAD_FAILED);
		}
		else 
		{
			m_data = data;
			setState(State.READY);
		}
	}
	
}
