package com.basic.adminowner.entity;

import jakarta.persistence.*;
@Entity

public class Property {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
	private String name;
    private String location;
    private double price;
    @ManyToOne
    private User owner;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}


}
