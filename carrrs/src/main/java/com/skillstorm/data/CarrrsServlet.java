package com.skillstorm.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.bean.Car;

@WebServlet(urlPatterns = "/api/rental")
public class CarrrsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CarrrService service = new CarrrService();
	
	
    public CarrrsServlet() {}
    
    
    //return all or specified
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		String vin = request.getParameter("vin"); 
		
		//get all if no param
		if(vin==null) {
			List<Car> all =service.findAll();
			//all.forEach(System.out::println);
			String json = new ObjectMapper().writeValueAsString(all);
			response.getWriter().print(json);
			response.setStatus(200); // "return"
			response.setContentType("application/json");
		}
		else {
			if(vin.length()>17) {
				response.getWriter().print("{}");
			}
			else {
				Car car =service.find(vin.toUpperCase());
				String json = car==null?"{}":new ObjectMapper().writeValueAsString(car);
				System.out.println(json);
				response.getWriter().print(json);
				response.setStatus(200); // "return"
				response.setContentType("application/json");
			}
		}
	}

	//add new
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post");
		
		Car newCar= new ObjectMapper().readValue(request.getInputStream(), Car.class);
		boolean success =service.add(newCar);
		
		response.getWriter().print(success);
		response.setStatus(success?201:500);
		response.setContentType("application/json");
	}
	
	//update
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("put");

		String vin = request.getParameter("vin"); 
		
		if(vin==null||vin.length()>17) {	
			response.getWriter().print(false);
			response.setStatus(500);
		}
		else {
			Car newCar= new ObjectMapper().readValue(request.getInputStream(), Car.class);
			boolean success =service.update(vin,newCar);
			
			response.getWriter().print(success);
			response.setStatus(success?200:500);
		}
		response.setContentType("application/json");
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("delete");
		
		String vin = request.getParameter("vin"); 
		if(vin==null||vin.length()!=17) {	
			response.getWriter().print(false);
			response.setStatus(500);
		}
		else {
			boolean success =service.remove(vin.toUpperCase());
			response.getWriter().print(success);
			response.setStatus(success?200:500);
		}
		response.setContentType("application/json");
		
	}

	//request.getRequestURI();
}
