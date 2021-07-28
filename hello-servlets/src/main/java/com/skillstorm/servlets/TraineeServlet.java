package com.skillstorm.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.beans.Trainee;
import com.skillstorm.data.TraineeDAO;

@WebServlet(urlPatterns = "/api/trainee")
public class TraineeServlet extends HttpServlet {
	
	// servlets are Singletons...
	TraineeDAO dao = new TraineeDAO();

	// safe : no server state is changed
	// GET /api/trainee?id=___
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("id") != null) {
			String param = req.getParameter("id"); 
			int id = Integer.parseInt(param); 
			Trainee trainee = dao.findById(id); // JDBC
			String json = new ObjectMapper().writeValueAsString(trainee); // converting Java obj -> JSON
			System.out.println(json);
			resp.getWriter().print(json);// write the data to the response
		}else {
			Set<Trainee> trainees = dao.findAll();
			String json = new ObjectMapper().writeValueAsString(trainees);
			resp.getWriter().print(json);
		}
	}
	
	// not SAFE
	// idempotence: subsequent/repetitive calls have an adverse result
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doPost(req, resp); // default is to throw 405
		InputStream requestBody = req.getInputStream();
		// convert the request body into a Trainee.class object
		Trainee trainee = new ObjectMapper().readValue(requestBody, Trainee.class);
		System.out.println(trainee);
		try {
			Trainee updated = dao.create(trainee);
			// return back the updated trainee
			resp.getWriter().print(new ObjectMapper().writeValueAsString(updated));
			resp.setStatus(201); // "return"
			resp.setContentType("application/json"); // Content-Type : application/json (header)
		} catch (SQLException e) {
			e.printStackTrace();
			resp.getWriter().print(new Trainee()); // OR empty object
		}
	}
	
	// not SAFE
	// not idempotent 
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// update your firstname 
		req.getParameter("firstname"); // == Dan
	}
	
	// not idempotent
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// delete record id 1
	}
	
}












