package com.ziscloud.zcdiagram.pojo;

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

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "zcdiagram_db")
public class User implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -4158344256846242154L;
	private Integer id;
	private Power power;
	private String loginName;
	private String password;
	private String name;
	private String department;
	private String telephone;
	private String email;
	private String office;
	private Set<UserLog> userLogs = new HashSet<UserLog>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Power power, String loginName, String password, String name) {
		this.power = power;
		this.loginName = loginName;
		this.password = password;
		this.name = name;
	}

	/** full constructor */
	public User(Power power, String loginName, String password, String name,
			String department, String telephone, String email, String office,
			Set<UserLog> userLogs) {
		this.power = power;
		this.loginName = loginName;
		this.password = password;
		this.name = name;
		this.department = department;
		this.telephone = telephone;
		this.email = email;
		this.office = office;
		this.userLogs = userLogs;
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
	@JoinColumn(name = "powerId", nullable = false)
	public Power getPower() {
		return this.power;
	}

	public void setPower(Power power) {
		this.power = power;
	}

	@Column(name = "loginName", nullable = false, length = 20)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "password", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "department")
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "telephone", length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "office")
	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserLog> getUserLogs() {
		return this.userLogs;
	}

	public void setUserLogs(Set<UserLog> userLogs) {
		this.userLogs = userLogs;
	}

}