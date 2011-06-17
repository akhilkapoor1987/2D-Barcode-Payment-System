package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Servlet implementation class BuyProduct
 */
@WebServlet("/InstantPay")
public class InstantPay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ConnectionManager pool;

	public void init() throws ServletException {
         System.out.println("inside the Instant Pay servlet");
         		try {

 			pool = new ConnectionManager();

 		} catch (Exception e) {
 			System.out.println("init fails");
 		}
         }
       
   		
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InstantPay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,NullPointerException,NotSerializableException {
		// TODO Auto-generated method stub
		  PrintWriter out = response.getWriter();
    		  Connection con4 = null;
			 
			
String memberId =(request.getParameter("memberId"));
String walletAmt="";
String walletId="";
String content="";


			
			try {
				  int memberId1 =Integer.parseInt(memberId);	   
		
				
			// ----------------------------------------------------
			
			//-------------------------------------------------------------------------
	
				con4 =  pool.getConnection();
				   
				
				String ewallet= " select walletAmt, walletId from bmps_wallet where memberId = '"+ memberId1 +"' ";
				System.out.println(ewallet);
				//@SuppressWarnings("null")
				PreparedStatement pst4 = con4.prepareStatement(ewallet);
			ResultSet rs1 = pst4.executeQuery();
         	rs1 = pst4.getResultSet();
//			pst.close();
			while (rs1.next()) {
			 walletAmt = Integer.toString(rs1.getInt(1));
			walletId=Integer.toString(rs1.getInt(2));
			}
			
			
		
			
			} 
		 catch(SQLException ex) {
				System.err.println("SQLException: " + ex.getMessage());
			}
		content = walletId+"*"+walletAmt;
		out.println(content);
	System.out.println(content);
	}



}


	