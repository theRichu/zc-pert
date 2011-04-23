package com.struts1.demo.formbean;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

	private static final long serialVersionUID = -4691250914598475609L;

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
