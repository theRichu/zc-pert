package com.ziscloud.zcdiagram.core;

public interface IModelChangeProvider {

	public void addModelChangedListener(IModelChangedListener listener);

	public void fireModelChanged(IModelChangedEvent event);

	public void fireModelObjectChanged(Object object, Object oldValue,
			Object newValue);
	
	public void removeModelChangedListener(IModelChangedListener listener);

}
