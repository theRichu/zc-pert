package com.ziscloud.zcdiagram.gef;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Diagram extends Element {
	private static final long serialVersionUID = 7331508814122003436L;
    protected List nodes = new ArrayList();

    public void addNode(Node node) {
        nodes.add(node);
    }
    
    public void addNode(List<Node> nodes) {
    	this.nodes.addAll(nodes);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    public List getNodes() {
        return nodes;
    }

    public InputStream getAsStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(this);
        out.close();
        InputStream istream = new ByteArrayInputStream(os.toByteArray());
        os.close();
        return istream;
    }

    public static Diagram makeFromStream(InputStream istream) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(istream);
        Diagram diagram = (Diagram) ois.readObject();
        ois.close();
        return diagram;
    }

}