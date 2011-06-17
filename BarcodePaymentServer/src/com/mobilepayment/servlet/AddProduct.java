package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddProduct
 */
@WebServlet("/AddProduct")
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	private ConnectionManager pool;

	public void init() throws ServletException {
		System.out.println("inside the Add product servlet");
		try {
			pool = new ConnectionManager();
			System.out.println("connection instance acquired");
		} catch (Exception e) {
			System.out.println("init fails");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String pname = request.getParameter("pname");
		String pdesc = request.getParameter("pdesc");
		String qty = request.getParameter("qty");
		String price = request.getParameter("price");
		String memberId = request.getParameter("memberId");
		System.out.println("memberId is " + memberId);

		String query = "INSERT INTO bmps_product (pname,pdesc,qty,price,memberId) VALUES ('"
				+ pname
				+ "','"
				+ pdesc
				+ "','"
				+ qty
				+ "','"
				+ price
				+ "','"
				+ memberId + "')";
		System.out.println("SQL is " + query);
		String returnString = "N";

		try {
			con = pool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			System.out.println("sql is " + query);

			pstmt.executeUpdate(query);
			String destination = "/login.jsp";

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					destination);

			rd.include(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception during exacuting sql!");
		} finally {
			if (con != null)
				pool.returnConnection(con);

			out.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
