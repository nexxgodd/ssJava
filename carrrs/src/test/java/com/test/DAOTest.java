package com.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.skillstorm.bean.Car;
import com.skillstorm.data.CarrrService;

public class DAOTest {
	static CarrrService service = new CarrrService();
	static final String VIN="JHMGE8G24AC915549";
	static final Car NEWCAR = new Car("newvin", 2020, "1", "1", "Sedan", 42, "red", "1");
	
	
	
	@BeforeClass
	public static void prepDB() {
		service.add(new Car("updateA",0, "", "", "", 0, "", ""));
		service.add(new Car("updateB",0, "", "", "", 0, "", ""));
		service.add(new Car("delete",0, "", "", "", 0, "", ""));
		
	}
	
	/*********************************** Select ***********************************/
	
	@Test
	public void selectAll() {
		List<Car> all =service.findAll();
//		all.forEach(System.out::println);
//		for(Car car:all) {
//			if
//		}
		
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
	
	/*********************************** Insert ***********************************/

	@Test
	public void insert() {
		if(!service.add(new Car("insertA",0, "Howdy", "", "", 0, "", ""))) {
			fail();
		}
		Car car =service.find("insertA");
		assertTrue(car!=null && car.getMake().equals("Howdy"));
	}
	
	@Test
	public void insertWithNulls() {
		if(!service.add(new Car("insertB",0, "", null, "", 0, null, null))) {
			fail();
		}
		Car car =service.find("insertB");
		assertTrue(car!=null && car.getUrl()==car.getModel());
	}
	
	@Test
	public void insertBad() {
		assertFalse(service.add(new Car("not2b",0, "", null, null, 0, null, null)));
	}
	
	@Test
	public void insertDup() {
		assertFalse(service.add(new Car(VIN,0, "", "", "", 0, "", "")));
	}
	
	/*********************************** Update ***********************************/

	
	@Test
	public void updateT() {
		if(!service.updateAvailability("updateA", true)) {
			fail();
		}
		Car car =service.find("updateA");
		assertTrue(car!=null && car.getAvailable());
	}
	
	@Test
	public void updateF() {
		if(!service.updateAvailability("updateA", false)) {
			fail();
		}
		Car car =service.find("updateA");
		assertTrue(car!=null && !car.getAvailable());
	}
	
	/*********************************** Delete ***********************************/
	
	@Test
	public void delete() {
		if(!service.remove("delete")) {
			fail();
		}
		assertNull(service.find("delete"));
	}
	
	@Test
	public void deleteBad() {
		assertFalse(service.remove("badvin"));
	}
	
	
	@AfterClass
	public static void fixDB() {
		
		service.remove("updateA");
		service.remove("updateB");
		service.remove("insertA");
		service.remove("insertB");
		
		
	}
	
}
