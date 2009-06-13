package com.ziscloud.zcdiagram.editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.pojo.Activity;

public class ActivityFilter extends ViewerFilter {
	private Map<String, Text> fields;

	public ActivityFilter(Map<String, Text> fields) {
		this.fields = fields;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Activity activity = (Activity) element;
		for (String str : fields.keySet()) {
			String v = fields.get(str).getText();
			if (!StringUtils.isBlank(v)) {
				try {
					Method m = Activity.class.getMethod("get" + str);
					if (str.endsWith("Date")) {
						if (!(m.invoke(activity).toString().equals(v))) {
							return false;
						}
					} else {
						if (m.invoke(activity).toString().indexOf(v) == -1) {
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
		}
		return true;
	}

}
