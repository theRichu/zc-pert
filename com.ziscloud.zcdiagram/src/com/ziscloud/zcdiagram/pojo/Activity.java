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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Activitiy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "activitiy", catalog = "zcdiagram_db")
public class Activity implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 9018993444890327030L;
	private Integer id;
	private Project project;
	private String name;
	private String symbol;
	private String preActivity;
	private Integer planPeriod;
	private Double planCost;
	private Double output;
	private Date planStartDate;
	private Date planEndDate;
	private Date mustStartDate;
	private Date mustEndDate;
	private Date laterStartDate;
	private Date laterEndDate;
	private Date earlyStartDate;
	private Date earlyEndDate;
	private Date actualStartDate;
	private Date actualEndDate;
	private Integer actualPeriod;
	private Double actualCost;
	private String builder;
	private Integer rarDays;
	private Double rarCost;
	private Integer oprarDays;
	private Date popStartDate;
	private Date popEndDate;
	private Date optopStartDate;
	private Date optopEndDate;
	private String remarks;
	private Set<DrawMeta> drawMetas = new HashSet<DrawMeta>(0);

	// Constructors

	/** default constructor */
	public Activity() {
	}

	/** minimal constructor */
	public Activity(Project project, String name, String symbol,
			Integer planPeriod, Double planCost, Double output,
			Date planStartDate, Date planEndDate) {
		this.project = project;
		this.name = name;
		this.symbol = symbol;
		this.planPeriod = planPeriod;
		this.planCost = planCost;
		this.output = output;
		this.planStartDate = planStartDate;
		this.planEndDate = planEndDate;
	}

	/** full constructor */
	public Activity(Project project, String name, String symbol,
			String preActivity, Integer planPeriod, Double planCost,
			Double output, Date planStartDate, Date planEndDate,
			Date mustStartDate, Date mustEndDate, Date laterStartDate,
			Date laterEndDate, Date earlyStartDate, Date earlyEndDate,
			Date actualStartDate, Date actualEndDate, Integer actualPeriod,
			Double actualCost, String builder, Integer rarDays, Double rarCost,
			Integer oprarDays, Date popStartDate, Date popEndDate,
			Date optopStartDate, Date optopEndDate, String remarks,
			Set<DrawMeta> drawMetas) {
		this.project = project;
		this.name = name;
		this.symbol = symbol;
		this.preActivity = preActivity;
		this.planPeriod = planPeriod;
		this.planCost = planCost;
		this.output = output;
		this.planStartDate = planStartDate;
		this.planEndDate = planEndDate;
		this.mustStartDate = mustStartDate;
		this.mustEndDate = mustEndDate;
		this.laterStartDate = laterStartDate;
		this.laterEndDate = laterEndDate;
		this.earlyStartDate = earlyStartDate;
		this.earlyEndDate = earlyEndDate;
		this.actualStartDate = actualStartDate;
		this.actualEndDate = actualEndDate;
		this.actualPeriod = actualPeriod;
		this.actualCost = actualCost;
		this.builder = builder;
		this.rarDays = rarDays;
		this.rarCost = rarCost;
		this.oprarDays = oprarDays;
		this.popStartDate = popStartDate;
		this.popEndDate = popEndDate;
		this.optopStartDate = optopStartDate;
		this.optopEndDate = optopEndDate;
		this.remarks = remarks;
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

	@Column(name = "preActivity")
	public String getPreActivity() {
		return this.preActivity;
	}

	public void setPreActivity(String preActivity) {
		this.preActivity = preActivity;
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

	@Column(name = "output", nullable = false, precision = 15, scale = 3)
	public Double getOutput() {
		return this.output;
	}

	public void setOutput(Double output) {
		this.output = output;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "mustStartDate", length = 10)
	public Date getMustStartDate() {
		return this.mustStartDate;
	}

	public void setMustStartDate(Date mustStartDate) {
		this.mustStartDate = mustStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "mustEndDate", length = 10)
	public Date getMustEndDate() {
		return this.mustEndDate;
	}

	public void setMustEndDate(Date mustEndDate) {
		this.mustEndDate = mustEndDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "laterStartDate", length = 10)
	public Date getLaterStartDate() {
		return this.laterStartDate;
	}

	public void setLaterStartDate(Date laterStartDate) {
		this.laterStartDate = laterStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "laterEndDate", length = 10)
	public Date getLaterEndDate() {
		return this.laterEndDate;
	}

	public void setLaterEndDate(Date laterEndDate) {
		this.laterEndDate = laterEndDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "earlyStartDate", length = 10)
	public Date getEarlyStartDate() {
		return this.earlyStartDate;
	}

	public void setEarlyStartDate(Date earlyStartDate) {
		this.earlyStartDate = earlyStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "earlyEndDate", length = 10)
	public Date getEarlyEndDate() {
		return this.earlyEndDate;
	}

	public void setEarlyEndDate(Date earlyEndDate) {
		this.earlyEndDate = earlyEndDate;
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

	@Column(name = "builder")
	public String getBuilder() {
		return this.builder;
	}

	public void setBuilder(String builder) {
		this.builder = builder;
	}

	@Column(name = "rarDays")
	public Integer getRarDays() {
		return this.rarDays;
	}

	public void setRarDays(Integer rarDays) {
		this.rarDays = rarDays;
	}

	@Column(name = "rarCost", precision = 15, scale = 3)
	public Double getRarCost() {
		return this.rarCost;
	}

	public void setRarCost(Double rarCost) {
		this.rarCost = rarCost;
	}

	@Column(name = "oprarDays")
	public Integer getOprarDays() {
		return this.oprarDays;
	}

	public void setOprarDays(Integer oprarDays) {
		this.oprarDays = oprarDays;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "popStartDate", length = 10)
	public Date getPopStartDate() {
		return this.popStartDate;
	}

	public void setPopStartDate(Date popStartDate) {
		this.popStartDate = popStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "popEndDate", length = 10)
	public Date getPopEndDate() {
		return this.popEndDate;
	}

	public void setPopEndDate(Date popEndDate) {
		this.popEndDate = popEndDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "optopStartDate", length = 10)
	public Date getOptopStartDate() {
		return this.optopStartDate;
	}

	public void setOptopStartDate(Date optopStartDate) {
		this.optopStartDate = optopStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "optopEndDate", length = 10)
	public Date getOptopEndDate() {
		return this.optopEndDate;
	}

	public void setOptopEndDate(Date optopEndDate) {
		this.optopEndDate = optopEndDate;
	}

	@Column(name = "remarks", length = 65535)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "activitiy")
	public Set<DrawMeta> getDrawMetas() {
		return this.drawMetas;
	}

	public void setDrawMetas(Set<DrawMeta> drawMetas) {
		this.drawMetas = drawMetas;
	}

}