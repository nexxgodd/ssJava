package com.skillstorm.data;

import java.util.List;

import com.skillstorm.bean.Car;

public class CarrrService {
	CarrrDAO dao=new CarrrDAO();
	
	public List<Car> findAll(){
		return dao.selectAll();
	}

	public Car find(String vin) {
		return dao.select(vin);
	}
	
	public boolean add(Car car) {
		return dao.insert(car);
	}
	
	public boolean updateAvailability(String vin, boolean available) {
		return dao.updateAvailability(vin, available);
	}
	public boolean update(String vin, Car car) {
		return dao.update(vin, car);
	}
	
	public boolean remove(String vin) {
		return dao.delete(vin);
	}
}
