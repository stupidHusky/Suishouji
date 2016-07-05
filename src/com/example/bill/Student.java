package com.example.bill;

public class Student {
	private String name;
	private String type;
	private String money;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", type=" + type + ", money=" + money
				+ "]";
	}
	public Student(String name, String type, String money) {
		super();
		this.name = name;
		this.type = type;
		this.money = money;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
