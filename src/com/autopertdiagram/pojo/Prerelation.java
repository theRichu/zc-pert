package com.autopertdiagram.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Prerelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "prerelation", catalog = "autopertdiagram")
public class Prerelation implements java.io.Serializable {

	// Fields

	private Integer id;
	private Process processByProcess;
	private Process processByPreProcess;
    private Integer ordering;

	// Constructors

	/** default constructor */
	public Prerelation() {
	}

	/** full constructor */
	public Prerelation(Process processByProcess, Process processByPreProcess, Integer ordering) {
		this.processByProcess = processByProcess;
		this.processByPreProcess = processByPreProcess;
        this.ordering = ordering;
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
	@JoinColumn(name = "process")
	public Process getProcessByProcess() {
		return this.processByProcess;
	}

	public void setProcessByProcess(Process processByProcess) {
		this.processByProcess = processByProcess;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preProcess")
	public Process getProcessByPreProcess() {
		return this.processByPreProcess;
	}

	public void setProcessByPreProcess(Process processByPreProcess) {
		this.processByPreProcess = processByPreProcess;
	}

    @Column(name = "ordering")
    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prerelation other = (Prerelation) obj;
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