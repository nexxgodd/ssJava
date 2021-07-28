package com.skillstorm.bean;

public class Car {
	
	private String vin;	
	private int year;
	private String make;
	private String model;
	private String body_style;
	private float price;
	private String color;
	private boolean available;
	private String url;
	
	//												  null										   null			null
	public Car(String vin, int year, String make, String model, String body_style, float price, String color, String url) {
		this.vin = vin;
		this.year = year;
		this.make = make;
		this.model = model;
		this.body_style = body_style;
		this.price = price;
		this.color = color;
		this.url = url;
	}
	
	public Car(String vin, int year, String make, String model, String body_style, float price, String color, boolean available, String url) {
		this(vin,year,make,model,body_style,price,color,url);
		this.available = available;
	}

	public String getVin() { return vin; }
	public int    getYear() { return year; }
	public String getMake() { return make; }
	public String getModel() { return model; }
	public String getBody_style() { return body_style; }
	public float  getPrice() { return price; }
	public String getColor() { return color; }
	public boolean getAvailable() { return available; }
	public String getUrl() { return url; }
	
	
	
	@Override
	public String toString() {
		return year + " " + make + " " + model + " for: $" + price;
	}
}
