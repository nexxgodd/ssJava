package com.skillstorm.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
		String vin = request.getParameter("vin"); 
		
		if(vin==null) {
			List<Car> all =service.findAll();
			//all.forEach(System.out::println);
			String json = new ObjectMapper().writeValueAsString(all);
			response.getWriter().print(json);
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
			}
		}
	}

	//add new
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post");
		
		Car newCar= new ObjectMapper().readValue(request.getInputStream(), Car.class);
		boolean success =service.add(newCar);
		
		response.getWriter().print(success);
		//response.setStatus(200);
	}
	
	//update
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("put");

		String vin = request.getParameter("vin"); 
		if(vin==null||vin.length()>17) {	
			response.getWriter().print(false);
		}
		else {
//			boolean success =service.remove(param.toUpperCase());
//			response.getWriter().print(success);
			
			Car newCar= new ObjectMapper().readValue(request.getInputStream(), Car.class);
			boolean success =service.update(vin,newCar);
			
			response.getWriter().print(success);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("delete");
		
		String vin = request.getParameter("vin"); 
		if(vin==null||vin.length()>17) {	
			response.getWriter().print(false);
		}
		else {
			boolean success =service.remove(vin.toUpperCase());
			response.getWriter().print(success);
		}
		
	}

	//request.getRequestURI();
}
