package com.ziscloud.zcdiagram.editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.SWTHelper;

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
					Method m = null;
					if (-1 == str.indexOf("Date")) {
						// calculate the field that not date type
						m = Activity.class.getMethod("get" + str);
						if (-1 == m.invoke(activity).toString().indexOf(v)) {
							return false;
						}
					} else {
						Date from = DateUtils.parseDate(v,
								SWTHelper.DATE_PATERNS);
						m = Activity.class.getMethod("get"
								+ StringUtils
										.replaceEach(str, new String[] {
												"From", "To" }, new String[] {
												"", "" }));
						Date d = ((Date) m.invoke(activity));
						if (str.endsWith("From")) {
							if (d.before(from)) {
								return false;
							}
						}
						if (str.endsWith("To")) {
							if (d.after(from)) {
								return false;
							}
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
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

}
