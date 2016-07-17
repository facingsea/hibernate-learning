package com.facingsea.exercise.c_relate.a_many_2_one.domain;

import java.util.Date;

public class Customer {

	private Integer id;
	private String name;
	private Date register;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", register=" + register + "]";
	}

}
