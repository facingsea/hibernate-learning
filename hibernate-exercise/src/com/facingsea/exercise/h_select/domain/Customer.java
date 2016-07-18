package com.facingsea.exercise.h_select.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Customer {
	
	private Integer id;
	
	private String name;
	
	private Date register;
	
	Set<Order> orders = new HashSet<>();
	
	public Customer() {
	}

	public Customer(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

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

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}
