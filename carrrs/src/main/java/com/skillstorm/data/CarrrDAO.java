package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import com.skillstorm.bean.Car;

public class CarrrDAO {
	private final static String URL = "jdbc:mysql://localhost:3306/carrrrs";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "root";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("driver broke");
			e.printStackTrace();
		}
	}
	
	public List<Car> selectAll(){
		List<Car> cars = new LinkedList<>();
		
		try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			ResultSet rs=conn.prepareStatement("select vin, year, make, model, body_style, price, color, available, url from cars;").executeQuery();
			while(rs.next()) {			//vin, 		year, 			make, 			model, 		body_style, 	
				cars.add(new Car(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),
						//	price, 		  color, 		   available, 				url
						rs.getFloat(6),rs.getString(7), rs.getBoolean(8),	rs.getString(9)));
			}
		}catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e);
		}		
		return cars;
	}
	
	public Car select(String vin) {
		try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			String sql="select vin, year, make, model, body_style, price, color, available, url from cars where vin=?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,vin);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {			//vin, 		year, 			make, 			model, 		body_style, 	
				return new Car(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),
						//	price, 		  color, 		   available, 				url
						rs.getFloat(6),rs.getString(7), rs.getBoolean(8),	rs.getString(9));
			}
		}catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		return null;
	}
	

	public boolean insert(Car car) {
		try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			String sql="insert into cars (vin, year, make, model, body_style, price, color, url) values (?,?,?,?,?,?,?,?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,car.getVin());
			stmt.setInt(2,car.getYear());
			stmt.setString(3,car.getMake());
			stmt.setString(4,car.getModel());
			stmt.setString(5,car.getBody_style());
			stmt.setFloat(6,car.getPrice());
			stmt.setString(7,car.getColor());
			stmt.setString(8,car.getUrl());

			return stmt.executeUpdate()==1;
		}catch (SQLException e) {
//			e.printStackTrace();
			System.out.println(e);
		}
		return false;
	}
	

	public boolean updateAvailability(String vin, boolean available) {
		try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			String sql="update cars set available=? where vin=?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1,available);
			stmt.setString(2,vin);

			return stmt.executeUpdate()==1;
		}catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		return false;
	}
	
	public boolean update(String vin, Car car) {
		try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			String sql="update cars set vin=?,year=?,make=?,model=?,body_style=?,price=?,color=?,available=?,url=? where vin=?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,car.getVin());
			stmt.setInt(2,car.getYear());
			stmt.setString(3,car.getMake());
			stmt.setString(4,car.getModel());
			stmt.setString(5,car.getBody_style());
			stmt.setFloat(6,car.getPrice());
			stmt.setString(7,car.getColor());
			stmt.setBoolean(8,car.getAvailable());
			stmt.setString(9,car.getUrl());
			
			stmt.setString(10,vin);

			return stmt.executeUpdate()==1;
		}catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		return false;
	}
	
	public boolean delete(String vin) {

		try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			String sql="delete from cars where vin=?;";
			PreparedStatement stmt = conn.prepareStatement(sql);			
			stmt.setString(1,vin);

			return stmt.executeUpdate()==1;
		}catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		return false;
	}
	
}




