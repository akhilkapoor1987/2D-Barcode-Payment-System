package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterUserDetails")
public class RegisterUserDetails extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public RegisterUserDetails() {
        super();
    }

    private ConnectionManager pool;
	
	public void init() throws ServletException{
    	System.out.println("inside the register user details servelets");
        try{
            pool = new ConnectionManager();
        }
        catch(Exception e){
        	System.out.println("init fails");
        }
    }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("login");
        System.out.println("username is "+name);
        
        String fname = request.getParameter("FirstName");
        String lname = request.getParameter("LastName");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String  pwd= request.getParameter("password");
        String  email= request.getParameter("email");
        int Id =0;

        String query = "insert into bmps_members (membertype,FirstName,LastName,address,city,state,login,password,email) values ('Mobile User'," +
        		"'" + fname + "','" + lname + "','" + address + "','" + city + "','" + state + "','" + name + "','" + pwd + "','"+ email +"')";
        System.out.println("SQL is "+query);
//        String returnString = "N";

        try{
			con = pool.getConnection();
			java.sql.Statement pstmt = con.createStatement();
			System.out.println("sql is "+query);
			
			pstmt.execute(query);
			
			Connection mem= pool.getConnection();
			String sql= "select memberId from bmps_members where login ='"+name+"' and password = '"+pwd+"' ";
			System.out.println(sql);

			//@SuppressWarnings("null")
			PreparedStatement pst4 = mem.prepareStatement(sql);
			
		ResultSet rs1 = pst4.executeQuery();
//	 	rs1 = pst4.getResultSet();
	 	while(rs1.next())
	 	{
	 	Id= rs1.getInt(1) ;
	  	}
		}catch (Exception e)
			{System.out.println("Exception during exacuting sql!");}
        finally {
            if (con != null) pool.returnConnection(con);
            out.print(Id);
            out.close();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
