package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;

/**
 * Servlet implementation class CreateMember
 */
@WebServlet("/CreateMember")
public class CreateMember extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateMember() {
		super();
		// TODO Auto-generated constructor stub
	}

	private ConnectionManager pool;

	public void init() throws ServletException {
		System.out.println("inside the register user servlet");
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

		String memberType = request.getParameter("memberType");
		System.out.println("username is " + memberType);
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		String query = "INSERT INTO bmps_members (memberType,FirstName,LastName,address,city,state,login,password,email) VALUES ('"
				+ memberType
				+ "','"
				+ firstName
				+ "','"
				+ lastName
				+ "','"
				+ address
				+ "','"
				+ city
				+ "','"
				+ state
				+ "','"
				+ login
				+ "','" + password + "','" + email + "')";
		System.out.println("SQL is " + query);
		String returnString = "N";

		try {
			con = pool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			System.out.println("sql is " + query);

			pstmt.executeUpdate(query);
//			 getServletConfig().getServletContext().getRequestDispatcher(
//		        "/JSP/Demo.hello.jsp").forward(request,response);
		//	response.sendRedirect("login.jsp");

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
