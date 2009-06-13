package com.ziscloud.zcdiagram.gef;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

public class NodeFigure extends Ellipse {
	private Label label;

	public NodeFigure(String name) {
		this.label = new Label();
		this.label.setText(name);
		this.add(label);
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