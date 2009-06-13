package com.ziscloud.zcdiagram.gef;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

public class ConnectionPart extends AbstractConnectionEditPart implements
		PropertyChangeListener {

	public void activate() {
		if (isActive()) {
			return;
		}
		super.activate();
		((Connection) getModel()).getTarget().addPropertyChangeListener(this);
		((Connection) getModel()).getSource().addPropertyChangeListener(this);
	}

	public void deactivate() {
		if (!isActive()) {
			return;
		}
		super.deactivate();
		((Connection) getModel()).getTarget()
				.removePropertyChangeListener(this);
		((Connection) getModel()).getSource()
				.removePropertyChangeListener(this);
	}

	protected IFigure createFigure() {
		Connection model = (Connection) getModel();
		PolylineConnection conn = new PolylineConnection();
		conn.setTargetDecoration(new PolygonDecoration());
		conn.setConnectionRouter(new BendpointConnectionRouter());
		if (model.isCirtical()) {
			conn.setForegroundColor(ColorConstants.red);
		}
		if (model.isVirtual()) {
			conn.setLineStyle(Graphics.LINE_DOT);
		}
		// set label
		final Label label = new Label(model.getLabel());
		label.setOpaque(true);
		conn.add(label, new MidpointLocator(conn, 0));
		// set router
		conn.setConnectionRouter(new BendpointConnectionRouter());
		return conn;
	}

	protected void createEditPolicies() {
	}

	protected void refreshVisuals() {
		// 绘制折线
		// Connection model = (Connection) getModel();
		// PolylineConnection conn = (PolylineConnection) getFigure();
		// Point start = model.getSource().getLocation();
		// Point end = model.getTarget().getLocation();
		// int offset = model.getSource().getSize().width / 2;
		// if (start.y != end.y) {
		// // System.out.println(start.y + " : " + end.y);
		// List<Bendpoint> bendpoints = new ArrayList<Bendpoint>();
		// // System.out.println((start.x + offset) + " : " + (end.y +
		// // offset));
		// bendpoints.add(new AbsoluteBendpoint(start.x + offset, end.y
		// + offset));
		// conn.setRoutingConstraint(bendpoints);
		// }
	}

	public void setSelected(int value) {
		super.setSelected(value);
		if (value != EditPart.SELECTED_NONE) {
			((PolylineConnection) getFigure()).setLineWidth(2);
		} else {
			((PolylineConnection) getFigure()).setLineWidth(1);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Node.PROP_LOCATION)) {
			// 更新折线折点
			// refreshVisuals();
		}
	}

}