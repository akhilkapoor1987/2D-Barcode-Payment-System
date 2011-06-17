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

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterUser() {
        super();
    }

    private ConnectionManager pool;
	
	public void init() throws ServletException{
    	System.out.println("inside the register servelets");
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
        
        String name = request.getParameter("username");
        System.out.println("username is "+name);
        
        String query = "select * from bmps_members where login = '" + name + "'";
        String insert = "insert into";
        String returnString = "N";

        try{
			con = pool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			System.out.println("sql is "+query);
			
			ResultSet results = pstmt.executeQuery();
			results.last();
			//System.out.println("results is "+results.getRow());
			if (results.getRow()>=1){
				returnString = "Y";
			} 

			out.println(returnString);
			//out.println(jObj);
		}catch (Exception e)
			{System.out.println("Exception during exacuting sql!");}
        finally {
            if (con != null) pool.returnConnection(con);
            
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
