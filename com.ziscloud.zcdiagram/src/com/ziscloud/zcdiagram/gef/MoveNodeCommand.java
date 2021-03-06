package com.ziscloud.zcdiagram.gef;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class MoveNodeCommand extends Command {
    private Node node;

    private Point oldPos;

    private Point newPos;

    public void setLocation(Point p) {
        this.newPos = p;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void execute() {
        oldPos = this.node.getLocation();
        node.setLocation(newPos);
    }

    public String getLabel() {
        return "Move Node";
    }

    public void redo() {
        this.node.setLocation(newPos);
    }

    public void undo() {
        this.node.setLocation(oldPos);
    }
}