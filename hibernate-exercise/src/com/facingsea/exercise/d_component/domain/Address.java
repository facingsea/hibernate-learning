package com.facingsea.exercise.d_component.domain;

/**
 * Address是Person的一部分
 * @author wangzhf
 *
 */
public class Address {
	
	private String province ;
	private String city ;
	
	private Person person ;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
