package com.skillstorm.data;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.bean.Car;

/**
 * Servlet implementation class CarrrsServlet
 */
@WebServlet(urlPatterns = "/api/rental")
public class CarrrsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CarrrService service = new CarrrService();
	
	
    public CarrrsServlet() {}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Car> all =service.findAll();
		all.forEach(System.out::println);
		String json = new ObjectMapper().writeValueAsString(all);
		
		response.getWriter().print(json);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getRequestURI();
		doGet(request, response);
	}

}
