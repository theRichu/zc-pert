package com.ziscloud.zcdiagram.gef;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class DiagramPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {

	protected List getModelChildren() {
		return ((Diagram) this.getModel()).getNodes();
	}

	public void activate() {
		super.activate();
		((Diagram) getModel()).addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((Diagram) getModel()).removePropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		refreshChildren();
	}

	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		figure.setLayoutManager(new FreeformLayout());
		return figure;
	}

	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramLayoutEditPolicy());
	}

}