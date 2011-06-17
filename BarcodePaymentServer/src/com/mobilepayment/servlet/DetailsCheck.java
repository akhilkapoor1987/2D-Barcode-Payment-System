package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BuyProduct
 */
@WebServlet("/DetailsCheck")
public class DetailsCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ConnectionManager pool;

	public void init() throws ServletException {
		System.out.println("inside the Details Check  servlet");
		try {

			pool = new ConnectionManager();

		} catch (Exception e) {
			System.out.println("init fails");
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailsCheck() {
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
	Connection con6 = null;

		String pdId = (request.getParameter("pdId"));
		String pname = (request.getParameter("pname"));
		int qty=0;
        int price=0;
		try {
			int pdId1 = Integer.parseInt(pdId);
			con6 = pool.getConnection();
			String q6 = " select qty,price from bmps_product where pdId = '"
					+ pdId1 + "' and pname = '"+pname+"' ";
			System.out.println(q6);
			// @SuppressWarnings("null")
			PreparedStatement pst6 = con6.prepareStatement(q6);
			ResultSet rs6 = pst6.executeQuery();
			rs6 = pst6.getResultSet();
			while (rs6.next()) {
				qty = rs6.getInt(1);
				price = rs6.getInt(2);
				}
			con6.close();
			pst6.close();
			out.println(qty+"$"+price);
			
	}
catch(Exception e)
{ e.getMessage();
	}
}
}
