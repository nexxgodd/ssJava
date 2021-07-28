package com.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.skillstorm.bean.Car;
import com.skillstorm.data.CarrrService;

public class DAOTest {
	CarrrService service = new CarrrService();
	static final String VIN="JHMGE8G24AC915549";
	
	@Test
	public void selectAll() {
		List<Car> all =service.findAll();
		//all.forEach(System.out::println);
		assertTrue(all.size()>0);
	}
	
	@Test
	public void select() {
		Car car =service.find(VIN);
		assertTrue(car!=null && car.getMake().equals("Infiniti"));
	}
	
	@Test
	public void selectBad() {
		Car car =service.find("badvin");
		assertTrue(car==null);
	}
	
	@Test
	public void insertAndDelete() {
		assertTrue(false);//						needs imp
	}
	
	@Test
	public void updateT() {
		if(!service.updateAvailability(VIN, true)) {
			fail();
		}
		Car car =service.find(VIN);
		assertTrue(car!=null && car.getAvailable());
	}
	
	@Test
	public void updateF() {
		if(!service.updateAvailability(VIN, false)) {
			fail();
		}
		Car car =service.find(VIN);
		assertTrue(car!=null && !car.getAvailable());
	}
	
}
