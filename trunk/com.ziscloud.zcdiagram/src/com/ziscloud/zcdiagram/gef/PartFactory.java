package com.ziscloud.zcdiagram.gef;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;


public class PartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		if (model instanceof Diagram)
			part = new DiagramPart();
		else if (model instanceof Connection)
			part = new ConnectionPart();
		else
			part = new NodePart();
		part.setModel(model);
		return part;
	}
}