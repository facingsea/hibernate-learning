package com.facingsea.exercise.i_c3p0.domain;

import java.util.Date;

public class C3P0Bean {
	
	private String id;
	private String name;
	private Date register;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getRegister() {
		return register;
	}
	public void setRegister(Date register) {
		this.register = register;
	}

}
