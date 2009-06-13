/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ziscloud.zcdiagram.handler;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.actions.ActionFactory;

import com.ziscloud.zcdiagram.editor.DiagramEditor;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.SWTHelper;

/**
 * @author zhanghao TODO To change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DiagramActionBarContributor extends ActionBarContributor {
	ExportImageAction exportAction;

	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());
		exportAction = new ExportImageAction();
	}

	protected void declareGlobalActionKeys() {
		addGlobalActionKey(GEFActionConstants.TOGGLE_GRID_VISIBILITY);
		addGlobalActionKey(GEFActionConstants.TOGGLE_RULER_VISIBILITY);
		addGlobalActionKey(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY);
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(exportAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.ZOOM_IN));
		toolBarManager.add(getAction(GEFActionConstants.ZOOM_OUT));
		toolBarManager.add(new ZoomComboContributionItem(getPage()));
	}

	public void contributeToMenu(IMenuManager menuManager) {
		// IMenuManager viewMenu = new MenuManager("&View", "view");
		// menuManager.insertAfter(IWorkbenchActionConstants.M_EDIT,viewMenu);
		// viewMenu.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
		// viewMenu.add(getAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY));
		// viewMenu.add(getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY));
	}

	private class ExportImageAction extends Action {

		/**
		 * Constructor
		 */
		public ExportImageAction() {
			super();
			setImageDescriptor(ImageUtil.EXPORTIMG);
			setId(SWTHelper.EXPORTIMGACTION_ID);
		}

		@Override
		public void run() {
			DiagramEditor editor = (DiagramEditor) getPage().getActiveEditor();
			FileDialog dialog = new FileDialog(editor.getSite().getShell(),
					SWT.SAVE);
			dialog.setFilterNames(new String[] { "PNG(*.png)", "GIF(*.gif)",
					"JPEG/JPG(*.jpg)" });
			dialog.setFilterExtensions(new String[] { "*.png", "*.gif",
							"*.jpg" });
			dialog.setFileName(editor.getPartName());
			String fileName = dialog.open();
			if (null != fileName) {
				ScalableFreeformRootEditPart rootPart = (ScalableFreeformRootEditPart) editor
						.getViewer().getRootEditPart();
				IFigure figure = rootPart
						.getLayer(ScalableFreeformRootEditPart.PRINTABLE_LAYERS);
				// To ensure every graphical element is included
				byte[] data = createImage(figure, SWT.IMAGE_PNG);
				try {
					FileOutputStream fos = new FileOutputStream(fileName);
					fos.write(data);
					fos.close();
					MessageDialog.openInformation(editor.getSite().getShell(),
							"导出网络图", "导出网络图成功！");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		/**
		 * See http://dev.eclipse.org/newslists/news.eclipse.tools.gef/msg05012.
		 * html
		 * 
		 * @param figure
		 * @param format
		 * @return
		 */
		private byte[] createImage(IFigure figure, int format) {
			Rectangle r = figure.getBounds();
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			Image image = null;
			GC gc = null;
			Graphics g = null;
			try {
				image = new Image(null, r.width, r.height);
				gc = new GC(image);
				g = new SWTGraphics(gc);
				g.translate(r.x * -1, r.y * -1);
				figure.paint(g);
				ImageLoader imageLoader = new ImageLoader();
				imageLoader.data = new ImageData[] { image.getImageData() };
				imageLoader.save(result, format);
			} finally {
				if (g != null) {
					g.dispose();
				}
				if (gc != null) {
					gc.dispose();
				}
				if (image != null) {
					image.dispose();
				}
			}
			return result.toByteArray();
		}
	}
}
