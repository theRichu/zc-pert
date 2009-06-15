package com.ziscloud.zcdiagram.pojo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DrawMeta entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "draw_meta", catalog = "zcdiagram_db")
public class DrawMeta implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 4704456984453711880L;
	private Integer id;
	private DrawNode drawNodeByEndNode;
	private DrawNode drawNodeByStartNode;
	private Activity activitiy;
	private Project project;
	private String name;
	private String symbol;
	private Date startDate;
	private Integer period;
	private Date endDate;
	private String isVirtual;
	private String isCriticl;
	private String isStarted;
	private String isEnded;
	private Integer ordinal;
	private List<Relation> rltnForCurAct = new LinkedList<Relation>();
	private List<Relation> rltnForPreAct = new LinkedList<Relation>();
	private Integer model;

	// Constructors

	/** default constructor */
	public DrawMeta() {
	}

	/** minimal constructor */
	public DrawMeta(Activity activitiy, Project project, String name,
			String symbol, Date startDate, Integer period, Date endDate) {
		this.activitiy = activitiy;
		this.project = project;
		this.name = name;
		this.symbol = symbol;
		this.startDate = startDate;
		this.period = period;
		this.endDate = endDate;
	}

	/** full constructor */
	public DrawMeta(DrawNode drawNodeByEndNode, DrawNode drawNodeByStartNode,
			Activity activitiy, Project project, String name, String symbol,
			Date startDate, Integer period, Date endDate, String isVirtual,
			String isCriticl, String isStarted, String isEnded,
			Integer ordinal, List<Relation> rltnForCurAct,
			List<Relation> rltnForPreAct) {
		this.drawNodeByEndNode = drawNodeByEndNode;
		this.drawNodeByStartNode = drawNodeByStartNode;
		this.activitiy = activitiy;
		this.project = project;
		this.name = name;
		this.symbol = symbol;
		this.startDate = startDate;
		this.period = period;
		this.endDate = endDate;
		this.isVirtual = isVirtual;
		this.isCriticl = isCriticl;
		this.isStarted = isStarted;
		this.isEnded = isEnded;
		this.ordinal = ordinal;
		this.rltnForCurAct = rltnForCurAct;
		this.rltnForPreAct = rltnForPreAct;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endNodeId")
	public DrawNode getDrawNodeByEndNode() {
		return this.drawNodeByEndNode;
	}

	public void setDrawNodeByEndNode(DrawNode drawNodeByEndNode) {
		this.drawNodeByEndNode = drawNodeByEndNode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "startNodeId")
	public DrawNode getDrawNodeByStartNode() {
		return this.drawNodeByStartNode;
	}

	public void setDrawNodeByStartNode(DrawNode drawNodeByStartNode) {
		this.drawNodeByStartNode = drawNodeByStartNode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actId")
	public Activity getActivitiy() {
		return this.activitiy;
	}

	public void setActivitiy(Activity activitiy) {
		this.activitiy = activitiy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectId", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "symbol", nullable = false, length = 20)
	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "startDate", nullable = false, length = 10)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "period", nullable = false)
	public Integer getPeriod() {
		return this.period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "endDate", nullable = false, length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "isVirtual", length = 5)
	public String getIsVirtual() {
		return this.isVirtual;
	}

	public void setIsVirtual(String isVirtual) {
		this.isVirtual = isVirtual;
	}

	@Column(name = "isCriticl", length = 5)
	public String getIsCriticl() {
		return this.isCriticl;
	}

	public void setIsCriticl(String isCriticl) {
		this.isCriticl = isCriticl;
	}

	@Column(name = "isStarted", length = 5)
	public String getIsStarted() {
		return this.isStarted;
	}

	public void setIsStarted(String isStarted) {
		this.isStarted = isStarted;
	}

	@Column(name = "isEnded", length = 5)
	public String getIsEnded() {
		return this.isEnded;
	}

	public void setIsEnded(String isEnded) {
		this.isEnded = isEnded;
	}

	@Column(name = "ordinal")
	public Integer getOrdinal() {
		return this.ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	@Column(name = "model")
	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "drawMetaByCurAct")
	@OrderBy("ordinal ASC")
	public List<Relation> getRltnForCurAct() {
		return this.rltnForCurAct;
	}

	public void setRltnForCurAct(List<Relation> rltnForCurAct) {
		this.rltnForCurAct = rltnForCurAct;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "drawMetaByPreAct")
	@OrderBy("ordinal ASC")
	public List<Relation> getRltnForPreAct() {
		return this.rltnForPreAct;
	}

	public void setRltnForPreAct(List<Relation> rltnForPreAct) {
		this.rltnForPreAct = rltnForPreAct;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DrawMeta other = (DrawMeta) obj;
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