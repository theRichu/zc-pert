package com.ziscloud.zcdiagram.editor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class TableColumns {
	private Map<String, Boolean> columnsHashMap = new LinkedHashMap<String, Boolean>();

	public TableColumns() {
	}

	public TableColumns(String[] column) {
		if (null != column) {
			for (int i = 0; i < column.length; i++) {
				String clm = column[i];
				if (!StringUtils.isBlank(clm)) {
					columnsHashMap.put(clm, true);
				}
			}
		}
	}

	public TableColumns(String[] column, Boolean[] editable) {
		if (null != column && null != editable) {
			int editSize = editable.length;
			for (int i = 0; i < column.length; i++) {
				String clm = column[i];
				if (!StringUtils.isBlank(clm)) {
					if (i >= editSize || null == editable[i]) {
						columnsHashMap.put(clm, false);
					} else {
						columnsHashMap.put(clm, editable[i]);
					}
				}
			}
		}
	}

	public boolean add(String column, Boolean editable) {
		return columnsHashMap.put(column, editable);
	}

	public boolean remove(String column) {
		return columnsHashMap.remove(column);
	}

	public String[] getColumns() {
		return columnsHashMap.keySet().toArray(
				new String[columnsHashMap.keySet().size()]);
	}

	public boolean isEditable(String column) {
		return columnsHashMap.get(column);
	}
}
