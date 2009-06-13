package com.ziscloud.zcdiagram.gef;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;

public class NodePart extends AbstractGraphicalEditPart implements
		PropertyChangeListener, NodeEditPart {

	protected DirectEditManager manager;

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Node.PROP_LOCATION))
			refreshVisuals();
	}

	protected IFigure createFigure() {
		return new NodeFigure(((Node) getModel()).getName());
	}

	protected void createEditPolicies() {
	}

	public void activate() {
		if (isActive()) {
			return;
		}
		super.activate();
		((Node) getModel()).addPropertyChangeListener(this);
	}

	public void deactivate() {
		if (!isActive()) {
			return;
		}
		super.deactivate();
		((Node) getModel()).removePropertyChangeListener(this);
	}

	protected void refreshVisuals() {
		Node node = (Node) getModel();
		Rectangle rectangle = new Rectangle(node.getLocation(), node.getSize());
		((NodeFigure) this.getFigure()).setName(((Node) this.getModel())
				.getName());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), rectangle);
	}

	// Abstract methods from NodeEditPart

	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	protected List getModelSourceConnections() {
		return ((Node) this.getModel()).getOutgoingConnections();
	}

	protected List getModelTargetConnections() {
		return ((Node) this.getModel()).getIncomingConnections();
	}

}