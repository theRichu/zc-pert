package com.ziscloud.zcdiagram.pojo;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_log", catalog = "zcdiagram_db")
public class UserLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8450404182546345485L;
	private Integer id;
	private User user;
	private String log;
	private Date logTime;

	// Constructors

	/** default constructor */
	public UserLog() {
	}

	/** full constructor */
	public UserLog(User user, String log, Timestamp logTime) {
		this.user = user;
		this.log = log;
		this.logTime = logTime;
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
	@JoinColumn(name = "userId", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "log", nullable = false, length = 65535)
	public String getLog() {
		return this.log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logTime", nullable = false, length = 19)
	public Date getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

}