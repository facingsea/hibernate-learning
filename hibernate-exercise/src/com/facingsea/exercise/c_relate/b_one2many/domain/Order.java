package com.facingsea.exercise.c_relate.b_one2many.domain;

public class Order {
	
	private Integer id;
	private Double price;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", price=" + price + "]";
	}

}
