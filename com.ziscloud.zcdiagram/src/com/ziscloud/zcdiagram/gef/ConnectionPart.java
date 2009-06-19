package com.ziscloud.zcdiagram.gef;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
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
		conn.setLineWidth(Connection.LINE_WIDTH);
		if (model.isCirtical()) {
			conn.setForegroundColor(Connection.COLOR_CIRTICAL);
		}
		if (model.isVirtual()) {
			conn.setLineStyle(Connection.STYLE_VIRTAUL);
		}
		// set label
		final Label label = new Label(model.getLabel());
		label.setOpaque(true);
		conn.add(label, new MidpointLocator(conn, 0));
		if (conn.getStart().x == conn.getEnd().x
				&& conn.getStart().y == conn.getEnd().y) {
			if (label.getSize().width > conn.getEnd().x - conn.getStart().x)
				label.setSize(conn.getEnd().x - conn.getStart().x, label
						.getSize().height);
		}
		// set router
		conn.setConnectionRouter(new BendpointConnectionRouter());
		return conn;
	}

	protected void createEditPolicies() {
	}

	protected void refreshVisuals() {
		// draw broken line
		Connection model = (Connection) getModel();
		PolylineConnection conn = (PolylineConnection) getFigure();
		Point start = model.getSource().getLocation();
		Point end = model.getTarget().getLocation();
		List<Bendpoint> bendpoints = new ArrayList<Bendpoint>();
		int distanceX = Math.abs(end.x - start.x);
		int distanceY = Math.abs(end.y - start.y);
		int brokenX = 0;
		int brokenY = 0;
		if (start.y < end.y) {
			brokenX = start.x + Node.RADIUS
					+ (distanceX * Node.BREAK_POINT_RATIO / distanceY);
			brokenY = end.y + Node.RADIUS;
			if (brokenX < (end.x + Node.RADIUS)) {
				bendpoints.add(new AbsoluteBendpoint(brokenX, brokenY));
			}
		}
		if (start.y > end.y) {
			brokenX = end.x + Node.RADIUS
					- (distanceX * Node.BREAK_POINT_RATIO / distanceY);
			brokenY = start.y + Node.RADIUS;
			if (brokenX > (start.x + Node.RADIUS)) {
				bendpoints.add(new AbsoluteBendpoint(brokenX, brokenY));
			}
		}
		conn.setRoutingConstraint(bendpoints);
	}

	public void setSelected(int value) {
		super.setSelected(value);
		if (value != EditPart.SELECTED_NONE) {
			((PolylineConnection) getFigure())
					.setForegroundColor(Connection.COLOR_SELECTED);
		} else {
			((PolylineConnection) getFigure())
					.setForegroundColor(Connection.COLOR_DEFAULT);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Node.PROP_LOCATION)) {
			// update the broken point
			refreshVisuals();
		}
	}

}