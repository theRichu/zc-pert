package com.ziscloud.zcdiagram.gef;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;


public class DiagramLayoutEditPolicy extends XYLayoutEditPolicy {

	protected Command createAddCommand(EditPart child, Object constraint) {
		return null;
	}

	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		if (!(child instanceof NodePart))
			return null;
		if (!(constraint instanceof Rectangle))
			return null;
		MoveNodeCommand cmd = new MoveNodeCommand();
		cmd.setNode((Node) child.getModel());
		cmd.setLocation(((Rectangle) constraint).getLocation());
		return cmd;

	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

}