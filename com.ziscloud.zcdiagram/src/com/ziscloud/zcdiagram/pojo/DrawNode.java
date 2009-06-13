package com.ziscloud.zcdiagram.pojo;

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
 * DrawNode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "draw_node", catalog = "zcdiagram_db")
public class DrawNode implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 87109083402039486L;
	private Integer id;
	private Project project;
	private String label;
	private Integer x;
	private Integer y;
	private Integer inDegree;
	private Integer outDegree;
	private Set<DrawMeta> drawMetasForEndNode = new HashSet<DrawMeta>(0);
	private Set<DrawMeta> drawMetasForStartNode = new HashSet<DrawMeta>(0);
	private Integer model;

	// Constructors

	/** default constructor */
	public DrawNode() {
	}

	/** minimal constructor */
	public DrawNode(Project project, String label) {
		this.project = project;
		this.label = label;
	}

	/** full constructor */
	public DrawNode(Project project, String label, Integer x, Integer y,
			Integer inDegree, Integer outDegree,
			Set<DrawMeta> drawMetasForEndNode,
			Set<DrawMeta> drawMetasForStartNode) {
		this.project = project;
		this.label = label;
		this.x = x;
		this.y = y;
		this.inDegree = inDegree;
		this.outDegree = outDegree;
		this.drawMetasForEndNode = drawMetasForEndNode;
		this.drawMetasForStartNode = drawMetasForStartNode;
	}

	public DrawNode(Integer id, Integer label, Project project) {
		this.id = id;
		this.label = label.toString();
		this.project = project;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectId", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "label", nullable = false, length = 20)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
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

	@Column(name = "inDegree")
	public Integer getInDegree() {
		return this.inDegree;
	}

	public void setInDegree(Integer inDegree) {
		this.inDegree = inDegree;
	}

	@Column(name = "outDegree")
	public Integer getOutDegree() {
		return this.outDegree;
	}

	public void setOutDegree(Integer outDegree) {
		this.outDegree = outDegree;
	}

	@Column(name = "model")
	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "drawNodeByEndNode")
	public Set<DrawMeta> getDrawMetasForEndNode() {
		return this.drawMetasForEndNode;
	}

	public void setDrawMetasForEndNode(Set<DrawMeta> drawMetasForEndNode) {
		this.drawMetasForEndNode = drawMetasForEndNode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "drawNodeByStartNode")
	public Set<DrawMeta> getDrawMetasForStartNode() {
		return this.drawMetasForStartNode;
	}

	public void setDrawMetasForStartNode(Set<DrawMeta> drawMetasForStartNode) {
		this.drawMetasForStartNode = drawMetasForStartNode;
	}

	public int compareTo(DrawNode o) {
		if (null == o || (!(o instanceof DrawNode))) {
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
		final DrawNode other = (DrawNode) obj;
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