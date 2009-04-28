package com.autopertdiagram.pojo;

import javax.persistence.*;
import java.util.*;

/**
 * Process entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "process", catalog = "autopertdiagram")
public class Process implements java.io.Serializable {

    // Fields

    private Integer id;
    private Node nodeBySourceNode;
    private Project project;
    private Node nodeByTargetNode;
    private String symbol;
    private String name;
    private Integer planPeriod;
    private Integer actualPeriod;
    private Date planStartDate;
    private Date planEndDate;
    private Date actualStartDate;
    private Date actualEndDate;
    private Double planCost;
    private Double actualCost;
    private Double output;
    private String isVirtual;
    private String isCriticl;
    private String isStarted;
    private String isFinished;
    private Integer ordering;
    private List<Prerelation> prerelationsForPreProcess = new LinkedList<Prerelation>();
    private List<Prerelation> prerelationsForProcess = new LinkedList<Prerelation>();

    // Constructors

    /**
     * default constructor
     */
    public Process() {
    }

    /**
     * full constructor
     */
    public Process(Node nodeBySourceNode, Project project,
                   Node nodeByTargetNode, String symbol, String name,
                   Integer planPeriod, Integer actualPeriod, Date planStartDate,
                   Date planEndDate, Date actualStartDate, Date actualEndDate,
                   Double planCost, Double actualCost, Double output,
                   String isVirtual, String isCriticl, String isStarted,
                   String isFinished, LinkedList<Prerelation> prerelationsForPreProcess,
                   LinkedList<Prerelation> prerelationsForProcess) {
        this.nodeBySourceNode = nodeBySourceNode;
        this.project = project;
        this.nodeByTargetNode = nodeByTargetNode;
        this.symbol = symbol;
        this.name = name;
        this.planPeriod = planPeriod;
        this.actualPeriod = actualPeriod;
        this.planStartDate = planStartDate;
        this.planEndDate = planEndDate;
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
        this.planCost = planCost;
        this.actualCost = actualCost;
        this.output = output;
        this.isVirtual = isVirtual;
        this.isCriticl = isCriticl;
        this.isStarted = isStarted;
        this.isFinished = isFinished;
        this.prerelationsForPreProcess = prerelationsForPreProcess;
        this.prerelationsForProcess = prerelationsForProcess;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceNode")
    public Node getNodeBySourceNode() {
        return this.nodeBySourceNode;
    }

    public void setNodeBySourceNode(Node nodeBySourceNode) {
        this.nodeBySourceNode = nodeBySourceNode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetNode")
    public Node getNodeByTargetNode() {
        return this.nodeByTargetNode;
    }

    public void setNodeByTargetNode(Node nodeByTargetNode) {
        this.nodeByTargetNode = nodeByTargetNode;
    }

    @Column(name = "symbol")
    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "planPeriod")
    public Integer getPlanPeriod() {
        return this.planPeriod;
    }

    public void setPlanPeriod(Integer planPeriod) {
        this.planPeriod = planPeriod;
    }

    @Column(name = "actualPeriod")
    public Integer getActualPeriod() {
        return this.actualPeriod;
    }

    public void setActualPeriod(Integer actualPeriod) {
        this.actualPeriod = actualPeriod;
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

    @Column(name = "planCost", precision = 22, scale = 0)
    public Double getPlanCost() {
        return this.planCost;
    }

    public void setPlanCost(Double planCost) {
        this.planCost = planCost;
    }

    @Column(name = "actualCost", precision = 22, scale = 0)
    public Double getActualCost() {
        return this.actualCost;
    }

    public void setActualCost(Double actualCost) {
        this.actualCost = actualCost;
    }

    @Column(name = "output", precision = 22, scale = 0)
    public Double getOutput() {
        return this.output;
    }

    public void setOutput(Double output) {
        this.output = output;
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

    @Column(name = "isFinished", length = 5)
    public String getIsFinished() {
        return this.isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    @Column(name = "ordering")
    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processByPreProcess")
    @OrderBy("ordering ASC")
    public List<Prerelation> getPrerelationsForPreProcess() {
        return this.prerelationsForPreProcess;
    }

    public void setPrerelationsForPreProcess(
            List<Prerelation> prerelationsForPreProcess) {
        this.prerelationsForPreProcess = prerelationsForPreProcess;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processByProcess")
    @OrderBy("ordering ASC")
    public List<Prerelation> getPrerelationsForProcess() {
        return this.prerelationsForProcess;
    }

    public void setPrerelationsForProcess(
            List<Prerelation> prerelationsForProcess) {
        this.prerelationsForProcess = prerelationsForProcess;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Process other = (Process) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
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