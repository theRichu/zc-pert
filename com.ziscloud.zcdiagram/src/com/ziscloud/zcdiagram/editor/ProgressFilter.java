package com.ziscloud.zcdiagram.editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.ziscloud.zcdiagram.pojo.Activity;

public class ProgressFilter extends ViewerFilter {
	private String[] fields;

	public ProgressFilter(String[] fields) {
		this.fields = fields;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Activity activity = (Activity) element;
		for (String str : fields) {
			try {
				Method m = null;
				if (str.equals("notDO")) {
					m = Activity.class.getMethod("getActualStartDate");
					Object o = m.invoke(activity);
					if (null != o && (!StringUtils.isBlank(o.toString()))) {
						return false;
					}
				}
				if (str.equals("doing")) {
					m = Activity.class.getMethod("getActualStartDate");
					Object o = m.invoke(activity);
					if (null == o || StringUtils.isBlank(o.toString())) {
						return false;
					} else {
						m = Activity.class.getMethod("getActualEndDate");
						o = m.invoke(activity);
						if (o != null && (!StringUtils.isBlank(o.toString()))) {
							return false;
						}
					}
				}
				if (str.equals("done")) {
					m = Activity.class.getMethod("getActualEndDate");
					Object o = m.invoke(activity);
					if (null == o || StringUtils.isBlank(o.toString())) {
						return false;
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
