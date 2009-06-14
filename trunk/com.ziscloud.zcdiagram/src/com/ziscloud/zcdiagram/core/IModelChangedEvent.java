package com.ziscloud.zcdiagram.core;

public interface IModelChangedEvent {
	public static final int CHANGE = 0;
	public static final int INSERT = 1;
	public static final int REMOVE = 2;

	public IModelChangeProvider getChangeProvider();

	public int getChangeType();

	public Object getNewValue();

	public Object getOldValue();
	
}
