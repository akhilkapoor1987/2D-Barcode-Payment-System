package com.mobilepayment.servlet;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.LinkedList;
import java.sql.*;

public class ConnectionManager {
	/** URL to connect to the database. */
	private static final String DB_URL = "jdbc:mysql://localhost:3306/mobilepayment";
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private final Queue<Connection> connPool = new LinkedList<Connection>();

	/**
	 * Gets a database connection.
	 * 
	 * @return Connection object representing a database connection.
	 */
	public Connection getConnection() {
		synchronized (connPool) { // Return a connection from the pool if any
			if (!connPool.isEmpty()) {
				return connPool.remove();
			}
		}

		// Create a new connection
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, "root", "");
			System.out.println("Created a new database connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Returns the given connection to the pool of free connections.
	 * 
	 * @param connection
	 *            Connection to be returned.
	 */
	public void returnConnection(Connection connection) {
		synchronized (connPool) {
			connPool.add(connection);
		}
	}
}
