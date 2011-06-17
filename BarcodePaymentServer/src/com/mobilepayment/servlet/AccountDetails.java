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
@WebServlet("/AccountDetails")
public class AccountDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ConnectionManager pool;

	public void init() throws ServletException {
         System.out.println("inside the Account Details servlet");
         		try {

 			pool = new ConnectionManager();

 		} catch (Exception e) {
 			System.out.println("init fails");
 		}
         }
       
   		
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountDetails() {
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
			 Connection con6 = null;
			
String memberId =(request.getParameter("memberId"));
String Fname="";
String Lname="";
String address="";
String city="";
String state="";
String email="";
String password="";
String walletAmt="";
String Bname="";
String BAcc="";
String Ccnum = "";
String ExpDate="";
String content = "";



			
			try {
				  int memberId1 =Integer.parseInt(memberId);	   
		
				
			// ----------------------------------------------------
			con6 =  pool.getConnection();
			String q6= " select Firstname,LastName,address, city,state,password,email from bmps_members where memberId = '"+ memberId1 +"' ";
			System.out.println(q6);
			//@SuppressWarnings("null")
			PreparedStatement pst6 = con6.prepareStatement(q6);
		ResultSet rs6 = pst6.executeQuery();
     	rs6 = pst6.getResultSet();
		while (rs6.next()) {
Fname = rs6.getString(1);
Lname= rs6.getString(2);
address= rs6.getString(3);
city= rs6.getString(4);
state= rs6.getString(5);
password= rs6.getString(6);
email= rs6.getString(7);


		}
		con6.close();
			//-------------------------------------------------------------------------
	
				con4 =  pool.getConnection();
				   
				
				String ewallet= " select walletAmt,bName,bAccount,expDate,ccNum from bmps_wallet where memberId = '"+ memberId1 +"' ";
				System.out.println(ewallet);
				//@SuppressWarnings("null")
				PreparedStatement pst4 = con4.prepareStatement(ewallet);
			ResultSet rs1 = pst4.executeQuery();
         	rs1 = pst4.getResultSet();
//			pst.close();
			while (rs1.next()) {
			 walletAmt = Integer.toString(rs1.getInt(1));
			 Bname= (rs1.getString(2));
			 BAcc= (rs1.getString(3));
			 ExpDate = Integer.toString(rs1.getInt(4));
			 Ccnum = rs1.getString((5));
			}
			
			
		
			
			} 
		 catch(SQLException ex) {
				System.err.println("SQLException: " + ex.getMessage());
			}
		content = Fname+"%"+Lname+"%"+address+"%"+city+"%"+state+"%"+password+"%"+email+"*"+walletAmt+"%"+Bname+"%"+BAcc+"%"+ExpDate+"%"+Ccnum;
		out.println(content);
	System.out.println(content);
	}



}


	