package com.autopertdiagram.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Node entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "node", catalog = "autopertdiagram")
public class Node implements java.io.Serializable, Comparable<Node> {

	// Fields

	private Integer id;
	private Integer label;
	private Integer x;
	private Integer y;
	private Integer outDegree;
	private Integer inDegree;
	private Project project;
	private Set<Process> processesForSourceNode = new HashSet<Process>(0);
	private Set<Process> processesForTargetNode = new HashSet<Process>(0);

	// Constructors

	/** default constructor */
	public Node() {
	}

	public Node(Integer id) {
		this.id = id;
	}

	public Node(Integer id, Integer label, Project project) {
		this.id = id;
		this.label = label;
		this.project = project;
	}

	/** full constructor */
	public Node(Integer label, Integer x, Integer y, Integer outDegree,
			Integer inDegree, Set<Process> processesForSourceNode,
			Set<Process> processesForTargetNode) {
		this.label = label;
		this.x = x;
		this.y = y;
		this.outDegree = outDegree;
		this.inDegree = inDegree;
		this.processesForSourceNode = processesForSourceNode;
		this.processesForTargetNode = processesForTargetNode;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "label")
	public Integer getLabel() {
		return this.label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	@Column(name = "x")
	public Integer getX() {
		return this.x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	@Column(name = "y")
	public Integer getY() {
		return this.y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	@Column(name = "outDegree")
	public Integer getOutDegree() {
		return this.outDegree;
	}

	public void setOutDegree(Integer outDegree) {
		this.outDegree = outDegree;
	}

	@Column(name = "inDegree")
	public Integer getInDegree() {
		return this.inDegree;
	}

	public void setInDegree(Integer inDegree) {
		this.inDegree = inDegree;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nodeBySourceNode")
	public Set<Process> getProcessesForSourceNode() {
		return this.processesForSourceNode;
	}

	public void setProcessesForSourceNode(Set<Process> processesForSourceNode) {
		this.processesForSourceNode = processesForSourceNode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nodeByTargetNode")
	public Set<Process> getProcessesForTargetNode() {
		return this.processesForTargetNode;
	}

	public void setProcessesForTargetNode(Set<Process> processesForTargetNode) {
		this.processesForTargetNode = processesForTargetNode;
	}

	public int compareTo(Node o) {
		if (null == o || (!(o instanceof Node))) {
			return 1;
		}
		return this.getId() - o.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Node other = (Node) obj;
		if (this.id != other.id
				&& (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

}