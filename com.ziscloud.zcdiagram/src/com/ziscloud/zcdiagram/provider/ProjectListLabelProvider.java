package com.ziscloud.zcdiagram.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.util.ImageUtil;

public class ProjectListLabelProvider extends LabelProvider {
	@Override
	public Image getImage(Object element) {
		return ImageUtil.PROJECT.createImage();
	}

	@Override
	public String getText(Object element) {
		Project project = (Project) element;
		return project.getName();
	}
}
