package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.skillstorm.beans.Trainee;

// Data Access Object: D.A.O, Dows
// separate algorithmic/brains from low-level data access
// cohesion: each class has one job, does it well
public class TraineeDAO {
	// public interface TraineeDAO{} public class TraineeDAOImpl {} // Java
	// public interface ITraineeDAO{} public class TraineeDAO {} // C#
	private final static String url = "jdbc:mysql://localhost:3306/july_java";
	private final static String username = "root";
	private final static String password = "root";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Something bad happened while loading the driver.");
			e.printStackTrace();
		}
	}
	
	public void batchCreate(List<Trainee> trainees) throws SQLException {
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false); // tx begins
		try {
			for (Trainee trainee : trainees) {
				PreparedStatement stmt = conn.prepareStatement("insert into trainee(name, major) values (?,?)");
				stmt.setString(1, trainee.getName());
				stmt.setString(2, trainee.getMajor());
				stmt.executeUpdate();
				// multiple SQL statements executed as ONE unit of work
			}
		} catch(SQLException e) {
			// rollback if anything goes wrong
			conn.rollback();
		}
		// commit ALL records if everything goes well
		conn.commit();
	}

	public Trainee create(Trainee trainee) throws SQLException {
		// try-with resources Java 7+, any "resources" that implement Autocloseable
		Connection conn = DriverManager.getConnection(url, username, password);
		try{	
			conn.setAutoCommit(false); // "begin transaction"
			String sql = "insert into trainee(name, major) values (?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // generated keys 
			stmt.setString(1, trainee.getName());
			stmt.setString(2, trainee.getMajor());
			// precompiles the SQL within Java, sends compiled code to MySQL
			stmt.executeUpdate(); // key is now generated as the row is inserted
			conn.commit();
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next(); // returns 1 row
			int id = keys.getInt(1); // generated primary key
			trainee.setId(id); // ID is updated in Java
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} 
		return trainee;
	}

	// CRUD:
	// Create, Retrieve, Update, Delete
	public void withoutTryWith(Trainee trainee) {
		// try-with resources Java 7+, any "resources" that implement Autocloseable
		// interface
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			String sql = "insert into trainee(id, name, major) values (?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, trainee.getId());
			stmt.setString(2, trainee.getName());
			stmt.setString(3, trainee.getMajor());
			// precompiles the SQL within Java, sends compiled code to MySQL
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(Trainee trainee) {

	}

	public void delete(Trainee trainee) {

	}

	public Trainee findById(int id) {
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "select id, name, major from trainee where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql); 
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return new Trainee(rs.getInt("id"), rs.getString("name"), rs.getString("major"));
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Set<Trainee> findAll(){
		Set<Trainee> results = new HashSet<>();
		// create Connection
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			// write the sql
			String sql = "select id, name, major from trainee;";
			// create a preparedstatement
			PreparedStatement stmt = conn.prepareStatement(sql);
			// bind values to the parameters in the query (optional)
			// execute query and loop thru the ResultSet
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString(2);
				String major = rs.getString("major");
				// object-relational mapping (Hibernate, Spring Data JPA, JPA, iBatis)
				Trainee trainee = new Trainee(id, name, major);
				results.add(trainee);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	// public Set<Trainee> findByName(String name){}
	// public Set<Trainee> findByMajor(String major){}

}
