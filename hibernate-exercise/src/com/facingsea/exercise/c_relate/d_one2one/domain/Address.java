package com.facingsea.exercise.c_relate.d_one2one.domain;

public class Address {

	private Integer id;
	private String name;
	
	Company company;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", name=" + name + ", company=" + company + "]";
	}

}
