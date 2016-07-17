package com.facingsea.exercise.c_relate.e_many2many.domain;

import java.util.HashSet;
import java.util.Set;

public class Course {

	private Integer id;
	private String cname;

	Set<Student> students = new HashSet<>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
