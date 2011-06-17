package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ForgotPassword
 */
@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ConnectionManager pool;

	public void init() throws ServletException {
		System.out.println("inside forgot password servlets");
		try {
			// System.out.println("before building a pool");
			pool = new ConnectionManager();

			// System.out.println("finish building a pool");
		} catch (Exception e) {
			System.out.println("init fails");
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForgotPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Connection con = null;
		response.setContentType("text/html;charset=UTF-8");
		String email = request.getParameter("email");
		String query = "select * from bmps_members where email = '" + email
				+ "'";

		try {
			con = pool.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet results = stmt.executeQuery();

			while (results.next()) {
				if (results.getString("email").compareTo(email) == 0) {
					System.out.println("Inside result set");

					PrintWriter out = response.getWriter();
					String title = "Password Reminder Sent to Email Provided";
					System.out.println("Inside result set");

					String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
							+ "Transitional//EN\">\n";
					out.println(docType + "<HTML>\n" + "<HEAD><TITLE>" + title
							+ "</TITLE></HEAD>\n"
							+ "<BODY BGCOLOR=\"#FFF00\">\n" + "<H1>" + title
							+ "</H1>");
					out.println("</BODY></HTML>");
					System.out.println("Inside result set");

				}

				else
					response.sendRedirect("ForgotPassword.jsp");
			}
			results.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null)
				pool.returnConnection(con);

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
