package com.autopertdiagram.pojo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Project entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project", catalog = "autopertdiagram")
public class Project implements java.io.Serializable {

    // Fields
    private Integer id;
    private String name;
    private String preSymbol;
    private Integer planPeriod;
    private Double planCost;
    private Date planStartDate;
    private Date planEndDate;
    private String contractor;
    private Integer actualPeriod;
    private Double actualCost;
    private Date actualStartDate;
    private Date actualEndDate;
    private String xmlPath;
    private long lastModifyDate;
    private long xmlGenerateDate;
    private Set<Process> processes = new HashSet<Process>(0);

    // Constructors

    /**
     * default constructor
     */
    public Project() {
    }

    /**
     * full constructor
     */
    public Project(String name, String preSymbol, Integer planPeriod, Double planCost,
                   Date planStartDate, Date planEndDate, String contractor,
                   Integer actualPeriod, Double actualCost, Date actualStartDate,
                   Date actualEndDate, Set<Process> processes) {
        this.name = name;
        this.preSymbol = preSymbol;
        this.planPeriod = planPeriod;
        this.planCost = planCost;
        this.planStartDate = planStartDate;
        this.planEndDate = planEndDate;
        this.contractor = contractor;
        this.actualPeriod = actualPeriod;
        this.actualCost = actualCost;
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
        this.processes = processes;
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

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "preSymbol", length = 4)
    public String getPreSymbol() {
        return this.preSymbol;
    }

    public void setPreSymbol(String preSymbol) {
        this.preSymbol = preSymbol;
    }

    @Column(name = "planPeriod")
    public Integer getPlanPeriod() {
        return this.planPeriod;
    }

    public void setPlanPeriod(Integer planPeriod) {
        this.planPeriod = planPeriod;
    }

    @Column(name = "planCost", precision = 22, scale = 0)
    public Double getPlanCost() {
        return this.planCost;
    }

    public void setPlanCost(Double planCost) {
        this.planCost = planCost;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "planStartDate", length = 10)
    public Date getPlanStartDate() {
        return this.planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "planEndDate", length = 10)
    public Date getPlanEndDate() {
        return this.planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    @Column(name = "contractor")
    public String getContractor() {
        return this.contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    @Column(name = "actualPeriod")
    public Integer getActualPeriod() {
        return this.actualPeriod;
    }

    public void setActualPeriod(Integer actualPeriod) {
        this.actualPeriod = actualPeriod;
    }

    @Column(name = "actualCost", precision = 22, scale = 0)
    public Double getActualCost() {
        return this.actualCost;
    }

    public void setActualCost(Double actualCost) {
        this.actualCost = actualCost;
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

    @Column(name = "xmlPath")
    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    @Column(name = "lastModifyDate")
    public long getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(long lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    @Column(name = "xmlGenerateDate")
    public long getXmlGenerateDate() {
        return xmlGenerateDate;
    }

    public void setXmlGenerateDate(long xmlGenerateDate) {
        this.xmlGenerateDate = xmlGenerateDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
    public Set<Process> getProcesses() {
        return this.processes;
    }

    public void setProcesses(Set<Process> processes) {
        this.processes = processes;
    }

}