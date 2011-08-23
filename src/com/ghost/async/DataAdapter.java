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
		switch (getState())
		{
		case EMPTY:
			m_state = State.LOADING;
			loadData();
			return getDefaultData();
		case LOADING:
		case LOAD_FAILED:
			return getDefaultData();
		}
		return m_data;
	}
	
	protected abstract void loadData();
	
	protected void onLoadData(Data data)
	{
		if (null == data)
		{
			m_state = State.LOAD_FAILED;
		}
		else 
		{
			m_state = State.READY;
			m_data = data;
		}
		notifyObservers();
	}
	
}
