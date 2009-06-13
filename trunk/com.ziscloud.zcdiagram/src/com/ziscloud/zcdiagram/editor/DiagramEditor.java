package com.ziscloud.zcdiagram.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.PrintFigureOperation;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.print.PrintGraphicalViewerOperation;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.dao.DrawNodeDAO;
import com.ziscloud.zcdiagram.dao.SessionFactory;
import com.ziscloud.zcdiagram.draw.DrawDiagram;
import com.ziscloud.zcdiagram.gef.Diagram;
import com.ziscloud.zcdiagram.gef.Node;
import com.ziscloud.zcdiagram.gef.PartFactory;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class DiagramEditor extends GraphicalEditor {
	public static final String ID = "com.ziscloud.zcdiagram.editor.diagramEditor";
	private Diagram diagram = new Diagram();
	private List<Node> nodes = new ArrayList<Node>();

	public Diagram getDiagram() {
		return this.diagram;
	}

	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new PartFactory());
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, true);
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, true);
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_SPACING, new Dimension(15,
				15));
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.CTRL),
				MouseWheelZoomHandler.SINGLETON);
		//
		IAction action = new ToggleGridAction(getGraphicalViewer());
		getActionRegistry().registerAction(action);
		action = new ToggleRulerVisibilityAction(getGraphicalViewer());
		getActionRegistry().registerAction(action);
		action = new ToggleSnapToGeometryAction(getGraphicalViewer());
		getActionRegistry().registerAction(action);
		action = new ZoomInAction(root.getZoomManager());
		getActionRegistry().registerAction(action);
		action = new ZoomOutAction(root.getZoomManager());
		getActionRegistry().registerAction(action);
		// print action
		action = new Action() {
			@Override
			public void run() {
				int style = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell().getStyle();
				Shell shell = new Shell(
						(style & SWT.MIRRORED) != 0 ? SWT.RIGHT_TO_LEFT
								: SWT.NONE);
				PrintDialog dialog = new PrintDialog(shell, SWT.NULL);
				PrinterData data = dialog.open();
				if (data != null) {
					PrintGraphicalViewerOperation operation = new PrintGraphicalViewerOperation(
							new Printer(data), getGraphicalViewer());
					// here you can set the Print Mode
					operation.setPrintMode(PrintFigureOperation.TILE);
					operation.run(getPartName());
				}
			}

		};
		action.setText("Print");
		action.setImageDescriptor(ImageUtil.PRINT);
		getEditorSite().getActionBars().getToolBarManager().insertBefore(
				SWTHelper.EXPORTIMGACTION_ID, action);
	}

	protected void initializeGraphicalViewer() {
		// draw
		DiagramEditorInput input = (DiagramEditorInput) getEditorInput();
		//start to draw the diagram
		DrawDiagram drawDiagram = new DrawDiagram(input.getProject(), input.getModel());
		nodes = drawDiagram.draw();
		// printNode();
		diagram.addNode(nodes);
		getGraphicalViewer().setContents(this.diagram);
	}

	public void doSave(IProgressMonitor monitor) {
		for (Node node : nodes) {
			DrawNodeDAO nodeDAO = new DrawNodeDAO();
			Transaction tx = null;
			try {
				tx = SessionFactory.getSession().beginTransaction();
				nodeDAO.updateCoordinate(node.getId(), node.getLocation().x,
						node.getLocation().y);
				tx.commit();
			} catch (HibernateException he) {
				if (null != tx)
					tx.rollback();
				throw new RuntimeException("保存网络图调整结果错误", he);
			} finally {
				SessionFactory.closeSession();
			}
		}
	}

	public void doSaveAs() {
	}

	public boolean isDirty() {
		return getCommandStack().isDirty();
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void setInput(IEditorInput input) {
		super.setInput(input);
		diagram = new Diagram();
	}

	public Object getAdapter(Class type) {
		if (type == ZoomManager.class)
			return getGraphicalViewer().getProperty(
					ZoomManager.class.toString());
		return super.getAdapter(type);
	}

	@SuppressWarnings("unused")
	private void printNode() {
		for (Node node : nodes) {
			System.out.println(node.getId() + " -> x:" + node.getLocation().x
					+ ", y:" + node.getLocation().y);
		}
	}

	public GraphicalViewer getViewer() {
		return getGraphicalViewer();
	}

	@Override
	public String getPartName() {
		return getEditorInput().getName() + " - 网络图";
	}
}
