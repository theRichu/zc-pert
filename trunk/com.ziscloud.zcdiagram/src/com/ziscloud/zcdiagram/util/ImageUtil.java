package com.ziscloud.zcdiagram.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.ziscloud.zcdiagram.ui.Activator;

public class ImageUtil {
	public static ISharedImages SHAREDIMAGER = PlatformUI.getWorkbench()
			.getSharedImages();
	public static ImageDescriptor SAVE = SHAREDIMAGER
			.getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT);
	public static ImageDescriptor PRINT = SHAREDIMAGER
			.getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT);
	// get the local image
	public static ImageDescriptor BLANK;
	public static ImageDescriptor PROJECT;
	public static ImageDescriptor NEW_ACT;
	public static ImageDescriptor SELECTONE;
	public static ImageDescriptor SELECTALL;
	public static ImageDescriptor UNSELECTONE;
	public static ImageDescriptor UNSELECTALL;
	public static ImageDescriptor COLUMNVISIBILITY;
	public static ImageDescriptor FILTER;
	public static ImageDescriptor FILTER_NUll;
	public static ImageDescriptor EXPORTIMG;
	public static ImageDescriptor DIAGRAM;
	public static ImageDescriptor DEL_ACT;
	public static ImageDescriptor DOING_ACT;
	public static ImageDescriptor DONE_ACT;
	public static ImageDescriptor NOTDO_ACT;
	public static ImageDescriptor OPT;
	public static ImageDescriptor DIAGRAM_MODEL_ONE;
	public static ImageDescriptor DIAGRAM_MODEL_TWO;

	static {
		BLANK = Activator.getImageDescriptor("icons/blank.png");
		PROJECT = Activator.getImageDescriptor("icons/project.png");
		NEW_ACT = Activator.getImageDescriptor("icons/new_activity.png");
		SELECTONE = Activator.getImageDescriptor("icons/select_one.png");
		SELECTALL = Activator.getImageDescriptor("icons/select_all.png");
		UNSELECTONE = Activator.getImageDescriptor("icons/unselect_one.png");
		UNSELECTALL = Activator.getImageDescriptor("icons/unselect_all.png");
		COLUMNVISIBILITY = Activator
				.getImageDescriptor("icons/column_visibility.png");
		FILTER = Activator.getImageDescriptor("icons/funnel.png");
		EXPORTIMG = Activator.getImageDescriptor("icons/export_image.png");
		DIAGRAM = Activator.getImageDescriptor("icons/diagram.png");
		FILTER_NUll = Activator.getImageDescriptor("icons/funnel_null.png");
		DEL_ACT = Activator.getImageDescriptor("icons/del_activity.png");
		DOING_ACT = Activator.getImageDescriptor("icons/doing_activity.png");
		DONE_ACT = Activator.getImageDescriptor("icons/done_activity.png");
		NOTDO_ACT = Activator.getImageDescriptor("icons/notDo_activity.png");
		OPT = Activator.getImageDescriptor("icons/optimize.png");
		DIAGRAM_MODEL_ONE=Activator.getImageDescriptor("icons/diagram_model_one.png");;
		DIAGRAM_MODEL_TWO=Activator.getImageDescriptor("icons/diagram_model_two.png");;
	}

}
