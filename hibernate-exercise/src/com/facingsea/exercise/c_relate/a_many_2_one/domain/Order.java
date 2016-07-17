package com.facingsea.exercise.c_relate.a_many_2_one.domain;

public class Order {
	
	private Integer id;
	private Double price;
	
	private Customer customer;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", price=" + price + ", customer=" + customer + "]";
	}

}
