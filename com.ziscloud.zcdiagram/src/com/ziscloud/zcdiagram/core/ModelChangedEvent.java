package com.ziscloud.zcdiagram.core;


public class ModelChangedEvent implements IModelChangedEvent {
	private IModelChangeProvider provider;
	private int changeType;
	private Object oldValue;
	private Object newValue;

	public ModelChangedEvent(IModelChangeProvider provider, int changeType,
			Object oldValue, Object newValue) {
		this.provider = provider;
		this.changeType = changeType;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	@Override
	public IModelChangeProvider getChangeProvider() {
		return provider;
	}

	@Override
	public int getChangeType() {
		return changeType;
	}

	@Override
	public Object getNewValue() {
		return newValue;
	}

	@Override
	public Object getOldValue() {
		return oldValue;
	}

}
