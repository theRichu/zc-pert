/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ziscloud.zcdiagram.gef;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class Node extends Element {
	private static final long serialVersionUID = -7956642150585296745L;
	final public static String PROP_LOCATION = "LOCATION";
	protected Point location = new Point(0, 0);
	Dimension size = new Dimension(30, 30);
	protected String name;
	protected boolean visible = true;
	protected List outputs = new ArrayList();
	protected List inputs = new ArrayList();

	public Node(int id, Point location, String name) {
		super();
		this.id = id;
		this.location = location;
		this.name = name;
	}

	public void addInput(Connection connection) {
		this.inputs.add(connection);
	}

	public void addOutput(Connection connection) {
		this.outputs.add(connection);
	}

	public List getIncomingConnections() {
		return this.inputs;
	}

	public List getOutgoingConnections() {
		return this.outputs;
	}

	public void removeInput(Connection connection) {
		this.inputs.remove(connection);
	}

	public void removeOutput(Connection connection) {
		this.outputs.remove(connection);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		if (this.visible == visible) {
			return;
		}
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (this.name.equals(name)) {
			return;
		}
		this.name = name;
	}

	public void setLocation(Point p) {
		if (this.location.equals(p)) {
			return;
		}
		this.location = p;
		firePropertyChange(PROP_LOCATION, null, p);
	}

	public Point getLocation() {
		return location;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
}