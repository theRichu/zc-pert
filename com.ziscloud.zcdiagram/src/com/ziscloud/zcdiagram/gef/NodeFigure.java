package com.ziscloud.zcdiagram.gef;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class NodeFigure extends Ellipse {
	private Label label;

	public NodeFigure(String name) {
		this.label = new Label();
		this.label.setText(name);
		this.add(label);
		this.setFill(true);
		this.setLineWidth(0);
		this.setForegroundColor(ColorConstants.white);
		this.setBackgroundColor(new Color(Display.getCurrent(), 51, 102, 153));
	}

	public String getText() {
		return this.label.getText();
	}

	public Rectangle getTextBounds() {
		return this.label.getTextBounds();
	}

	public void setName(String name) {
		this.label.setText(name);
		this.repaint();
	}

	public void setBounds(Rectangle rect) {
		Rectangle rct = new Rectangle(rect.x, rect.y, 30, 30);
		super.setBounds(rct);
		this.label.setBounds(rct);
	}
}