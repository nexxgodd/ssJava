package com.skillstorm.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 1xx: Informational
 * 2xx: good
 * 		200: OK
 * 		201: created
 * 		204: no content
 * 3xx: redirect
 * 4xx: client-side error
 * 		400: bad request (validation error) 
 * 		401: unauthorized (login)
 * 		403: forbidden (logged in but not allowed)
 * 		404: not found 
 * 5xx: server-side error
 * 		500: internal server error (stack trace) 
 */

@WebServlet(urlPatterns = "/api/process")
public class HelloServlet extends HttpServlet {

	// GET request to "/api/process"
	// route, mapping
	// HTTP is a plain-text protocol
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("GET method");
		PrintWriter out = resp.getWriter(); // write data to the HTTP response
		out.println("{ \"data\" : { }}");
	}
	
	// HttpServlet has methods to handle each type of HTTP request method: GET, POST, PUT, DELETE
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("POST method");
		// HTTP request body can store data
		String line = null;
		BufferedReader reader = req.getReader(); // request body
		while((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("PUT method");
	}
	
	// /api/process?param=value&param2=value2
	//   localhost:8080/api/process?id=101
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("DELETE method");
		System.out.println(req.getParameter("id"));
	}
	
}









