package com.mobilepayment.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.*;
import java.util.*;
/**
 *
 * @author Akhil
 */
public class ConnectionPool {

    private Hashtable connections = new Hashtable();
    private Properties props;
    private String driverClassName = "com.mysql.jdbc.Driver";
    private String dbURL = "jdbc:mysql://localhost:3306/mobilepayment";
    private String user = "root";
    private String password = "";
    private int initialConnections = 10;

    public ConnectionPool(Properties props, int initialConnections)throws SQLException, ClassNotFoundException{
        this.props = props;
        initializePool(props, initialConnections);
        System.out.println("inside connection pool");
    }


    public ConnectionPool(String mysql) throws SQLException, ClassNotFoundException{
        props = new Properties();
        props.put("connection.driver", driverClassName);
        props.put("connection url", dbURL);
        props.put("user", user);
        props.put("password", password);
        initializePool(props, initialConnections);
        System.out.println("inside connection pool");

    }

    public Connection getConnection() throws SQLException{
        Connection con = null;

        Enumeration cons = connections.keys();
        System.out.println("getting connection");
        synchronized(connections){
            while(cons.hasMoreElements()){
                con = (Connection)cons.nextElement();

                Boolean b = (Boolean)connections.get(con);
                if (b==Boolean.FALSE){
                    try{
                        con.setAutoCommit(true);
                    }
                    catch(SQLException e){
                        connections.remove(con);
                        con = getNewConnection();
                    }
                    connections.put(con,Boolean.TRUE);
                    return con;
                }
            }

            con = getNewConnection();
            connections.put(con, Boolean.FALSE);
            return con;
        }
    }

    public void returnConnection(Connection returned){
        System.out.println("return connection");
        if (connections.containsKey(returned)){
            connections.put(returned, Boolean.FALSE);
        }
    }

    private void initializePool(Properties props, int initialConnections) throws SQLException, ClassNotFoundException{

        Class.forName(props.getProperty("connection.driver"));

        for (int i = 0; i < initialConnections; i++){
            Connection con = getNewConnection();
            connections.put(con, Boolean.FALSE);
        }
    }

    private Connection getNewConnection() throws SQLException{
        return DriverManager.getConnection(props.getProperty("connection.url"),props);
    }
}

