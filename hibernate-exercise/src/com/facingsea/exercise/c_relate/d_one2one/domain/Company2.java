package com.facingsea.exercise.c_relate.d_one2one.domain;

public class Company2 {
	
	private Integer id ;
	private String name ;
	
	Address2 address2;

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

	public Address2 getAddress2() {
		return address2;
	}

	public void setAddress2(Address2 address2) {
		this.address2 = address2;
	}

	@Override
	public String toString() {
		return "Company2 [id=" + id + ", name=" + name + ", address2=" + address2 + "]";
	}

}
