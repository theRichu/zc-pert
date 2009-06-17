package com.ziscloud.zcdiagram.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Project entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project", catalog = "zcdiagram_db")
public class Project implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -3420201365699527647L;
	private Integer id;
	private String name;
	private String symbol;
	private Date planStartDate;
	private Date planEndDate;
	private Integer planPeriod;
	private Double planCost;
	private String manager;
	private String builder;
	private String designer;
	private Date actualStartDate;
	private Date actualEndDate;
	private Integer actualPeriod;
	private Double actualCost;
	private String remarks;
	private Long modifyTime;
	private Long drawTime;
	private Long optOneTime;
	private Long optTwoTime;
	private String isDeleted;
	private Set<Activity> activitiies = new HashSet<Activity>(0);
	private Set<DrawNode> drawNodes = new HashSet<DrawNode>(0);
	private Set<DrawMeta> drawMetas = new HashSet<DrawMeta>(0);

	// Constructors

	/** default constructor */
	public Project() {
	}

	/** minimal constructor */
	public Project(String name, String symbol, Date planStartDate,
			Date planEndDate, Integer planPeriod, Double planCost,
			String manager, String builder) {
		this.name = name;
		this.symbol = symbol;
		this.planStartDate = planStartDate;
		this.planEndDate = planEndDate;
		this.planPeriod = planPeriod;
		this.planCost = planCost;
		this.manager = manager;
		this.builder = builder;
	}

	/** full constructor */
	public Project(String name, String symbol, Date planStartDate,
			Date planEndDate, Integer planPeriod, Double planCost,
			String manager, String builder, String designer,
			Date actualStartDate, Date actualEndDate, Integer actualPeriod,
			Double actualCost, String remarks, Long modifyTime, Long drawTime,
			String isDeleted, Set<Activity> activitiies,
			Set<DrawNode> drawNodes, Set<DrawMeta> drawMetas) {
		this.name = name;
		this.symbol = symbol;
		this.planStartDate = planStartDate;
		this.planEndDate = planEndDate;
		this.planPeriod = planPeriod;
		this.planCost = planCost;
		this.manager = manager;
		this.builder = builder;
		this.designer = designer;
		this.actualStartDate = actualStartDate;
		this.actualEndDate = actualEndDate;
		this.actualPeriod = actualPeriod;
		this.actualCost = actualCost;
		this.remarks = remarks;
		this.modifyTime = modifyTime;
		this.drawTime = drawTime;
		this.isDeleted = isDeleted;
		this.activitiies = activitiies;
		this.drawNodes = drawNodes;
		this.drawMetas = drawMetas;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "symbol", nullable = false, length = 4)
	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "planStartDate", nullable = false, length = 10)
	public Date getPlanStartDate() {
		return this.planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "planEndDate", nullable = false, length = 10)
	public Date getPlanEndDate() {
		return this.planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	@Column(name = "planPeriod", nullable = false)
	public Integer getPlanPeriod() {
		return this.planPeriod;
	}

	public void setPlanPeriod(Integer planPeriod) {
		this.planPeriod = planPeriod;
	}

	@Column(name = "planCost", nullable = false, precision = 15, scale = 3)
	public Double getPlanCost() {
		return this.planCost;
	}

	public void setPlanCost(Double planCost) {
		this.planCost = planCost;
	}

	@Column(name = "manager", nullable = false)
	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Column(name = "builder", nullable = false)
	public String getBuilder() {
		return this.builder;
	}

	public void setBuilder(String builder) {
		this.builder = builder;
	}

	@Column(name = "designer")
	public String getDesigner() {
		return this.designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "actualStartDate", length = 10)
	public Date getActualStartDate() {
		return this.actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "actualEndDate", length = 10)
	public Date getActualEndDate() {
		return this.actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	@Column(name = "actualPeriod")
	public Integer getActualPeriod() {
		return this.actualPeriod;
	}

	public void setActualPeriod(Integer actualPeriod) {
		this.actualPeriod = actualPeriod;
	}

	@Column(name = "actualCost", precision = 15, scale = 3)
	public Double getActualCost() {
		return this.actualCost;
	}

	public void setActualCost(Double actualCost) {
		this.actualCost = actualCost;
	}

	@Column(name = "remarks", length = 65535)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "modifyTime")
	public Long getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "drawTime")
	public Long getDrawTime() {
		return this.drawTime;
	}

	public void setDrawTime(Long drawTime) {
		this.drawTime = drawTime;
	}

	@Column(name = "optOneTime")
	public Long getOptOneTime() {
		return optOneTime;
	}

	public void setOptOneTime(Long optOneTime) {
		this.optOneTime = optOneTime;
	}

	@Column(name = "optTwoTime")
	public Long getOptTwoTime() {
		return optTwoTime;
	}

	public void setOptTwoTime(Long optTwoTime) {
		this.optTwoTime = optTwoTime;
	}

	@Column(name = "isDeleted", length = 5)
	public String getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Activity> getActivitiies() {
		return this.activitiies;
	}

	public void setActivitiies(Set<Activity> activitiies) {
		this.activitiies = activitiies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<DrawNode> getDrawNodes() {
		return this.drawNodes;
	}

	public void setDrawNodes(Set<DrawNode> drawNodes) {
		this.drawNodes = drawNodes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<DrawMeta> getDrawMetas() {
		return this.drawMetas;
	}

	public void setDrawMetas(Set<DrawMeta> drawMetas) {
		this.drawMetas = drawMetas;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Project other = (Project) obj;
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