package com.facingsea.exercise.c_relate.d_one2one.domain;

public class Address2 {

	private Integer id;
	private String name;
	
	Company2 company2;

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

	public Company2 getCompany2() {
		return company2;
	}

	public void setCompany2(Company2 company2) {
		this.company2 = company2;
	}

	@Override
	public String toString() {
		return "Address2 [id=" + id + ", name=" + name + ", company2=" + company2 + "]";
	}

}
