package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BuyProduct
 */
@WebServlet("/Statements")
public class Statements extends HttpServlet {
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
	public Statements() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			NullPointerException, NotSerializableException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Connection con1 = null;
		Connection con2 = null;
	

		String memberId = (request.getParameter("memberId"));
	
		 int credit=0;
		 int debit =0 ;
		String date="";
		 String content = "";
		 int Id = 0;
         ArrayList<LinkedList<String>> dataList = new ArrayList<LinkedList<String>>();
         String Items = "";
		try {
			int memberId1 = Integer.parseInt(memberId);

//			con1 = pool.getConnection();
//
//			String ewallet = " select walletId from bmps_wallet where memberId = '"
//					+ memberId1 + "' ";
//			System.out.println(ewallet);
//			// @SuppressWarnings("null")
//			PreparedStatement pst1 = con1.prepareStatement(ewallet);
//			ResultSet rs1 = pst1.executeQuery();
//			rs1 = pst1.getResultSet();
//			while (rs1.next()) {
//				Id = rs1.getInt(1);
//			}
			con2= pool.getConnection();
			
			String wallet = " select credit,debit, trans_date from wallet_statement where member_id = '"+memberId1+ "'";
			System.out.println(wallet);
			// @SuppressWarnings("null")
			PreparedStatement pst2 = con2.prepareStatement(wallet);
			ResultSet rs2 = pst2.executeQuery();
			rs2 = pst2.getResultSet();
			while (rs2.next()) {
				  LinkedList<String> rows = new LinkedList<String>();
		    	  
		    	  rows.add(Integer.toString((rs2.getInt("credit"))));
		        rows.add(Integer.toString((rs2.getInt("debit"))));
		        rows.add(rs2.getString("trans_date"));
		       

		        dataList.add(rows);
		     System.out.println(rows.toString());
			}
			
			
			con2.close();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		 for(LinkedList<String> list : dataList)
			{
			
			while (!list.isEmpty()) {
			Items += list.poll()+"|"+list.poll()+"|"+list.poll()+"%";
			
			}}
		 out.println(Items);
		 System.out.println(Items);
	}

}
