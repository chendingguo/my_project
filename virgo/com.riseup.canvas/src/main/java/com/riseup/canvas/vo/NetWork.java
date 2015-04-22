package com.riseup.canvas.vo;

import java.io.Serializable;
import java.util.List;

public class NetWork implements Serializable{
	
	private static final long serialVersionUID = -1672768904433513338L;
	List<Node> nodes;
	List<Edge> edges;
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	

}
