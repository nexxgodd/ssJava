package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.skillstorm.beans.Car;

public class FlightDAO {
	
	private static final String URL= "jdbc:mysql://localhost:3306/chinook";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	public static void main(String[] args)   {
		System.out.println("hi");
	}
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("yes sql");
		}
		catch(ClassNotFoundException e) {
			System.out.println("no sql");
			e.printStackTrace();
		}
	}
	
	
	public boolean insert(Car cars) {
		
		try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			String sql = "insert into flights(to,from) values (?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, "one");
			stmt.setString(2, "two");
			
			stmt.executeUpdate();
			
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next();
			int id = keys.getInt(1);
			//flight.setid(id);
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public Car select(int id) {
		try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			String sql = "select to, fro from flights where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			System.out.println(rs.getString(1));
			//flight.setid(id);
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	

}
