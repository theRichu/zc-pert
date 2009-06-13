package com.ziscloud.zcdiagram.pojo;

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

/**
 * Power entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "power", catalog = "zcdiagram_db")
public class Power implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 7772552549976748228L;
	private Integer id;
	private String remarks;
	private Set<User> users = new HashSet<User>(0);

	// Constructors

	/** default constructor */
	public Power() {
	}

	/** minimal constructor */
	public Power(String remarks) {
		this.remarks = remarks;
	}

	/** full constructor */
	public Power(String remarks, Set<User> users) {
		this.remarks = remarks;
		this.users = users;
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

	@Column(name = "remarks", nullable = false)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "power")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}