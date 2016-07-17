package com.facingsea.exercise.c_relate.e_many2many.domain;

import java.util.HashSet;
import java.util.Set;

public class Student {

	private Integer id;
	private String sname;
	
	Set<Course> courses = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

}
