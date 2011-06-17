package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class loginservlet for user to login
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ConnectionManager pool;

	public void init() throws ServletException {
		System.out.println("inside the member login servelets");
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
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	/*	System.out.println("Request Received at Server");


		if (request.getParameter("submit") != null) {
			HttpSession session = request.getSession(true);
			String name = request.getParameter("login");
			if (name == null || name.length() == 0)
				name = "Anonymous";
			session.putValue("login", name);
			sendPage(request, response, session);
		} else {
			HttpSession session = request.getSession(false);
			if (session != null)
				session.invalidate();
			sendPage(request, response, null);
		}
	}

	// implementation of the rest of the method after the session begins
	private void sendPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Connection con = null;
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("pragma", "no-cache");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("login");
		String password = request.getParameter("password");
		System.out.println("username is " + name);
		System.out.println("password is " + password);
		// System.out.println("selectID is "+selectedID);
		String query = "select * from bmps_members where login = '" + name
				+ "' and password = '" + password + "'";
		String returnString = "N";
		String query2 = "";
		String memberType = "";
		String memberId = "";
		try {
			con = pool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			System.out.println("sql is " + query);

			ResultSet results = pstmt.executeQuery();
			results.last();
			if (results.getRow() >= 1) {
				returnString = "Y";
				memberType = results.getString("memberType");
				memberId = results.getString("memberId"); // add fields in
															// header
				
				
				// System.out.println(memberId);

				if ((results.getString("login").compareTo(name) == 0)
						&& (results.getString("password")).compareTo(password) == 0) {

//					request.setAttribute("memberType", memberType);
//					request.setAttribute("returnString", returnString);
//					request.setAttribute("memberId", memberId);
//					request.setAttribute("session", session.toString());
					out.print(memberType);
					out.print(memberId);
					out.print(returnString);
					out.print(session);
					
//					String destination = "/ProductCatalog";
//							
//					// ////forwarding request,response from one servlet to
//					// another/////////
//					RequestDispatcher rd = getServletContext()
//							.getRequestDispatcher(destination);
//					rd.forward(request, response);
//					
					
				}
			} else
				response.sendRedirect("ForgotPassword.jsp");

			out.println(returnString);
			// out.println(jObj);
		} catch (Exception e) {
			System.out.println("Exception during exacuting sql!");
		} finally {
			if (con != null)
				pool.returnConnection(con);

			out.close();
		}

	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		Connection con = null;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        System.out.println(pass);
       // System.out.println("username is "+name);
        
        String query = "select password, memberId from bmps_members where login = '" + name + "'";
        String insert = "insert into";
        String returnString = "N";

        try{
			con = pool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			System.out.println("sql is "+query);
			
			ResultSet results = pstmt.executeQuery();
			results.first();
			String passwd =results.getString(1);
			System.out.println(passwd);
		    String memId =results.getString(2);
		    System.out.println(memId);
				//System.out.println("results is "+results.getRow());
			if(pass.equalsIgnoreCase(passwd)){
				returnString = "Y";
				System.out.println("memberId for "+name+" is  " + passwd);

			} 

			out.print(returnString+"%"+memId);
			
			//out.println(jObj);
		}catch (Exception e)
			{System.out.println("Exception during exacuting sql!");}
        finally {
            if (con != null) pool.returnConnection(con);
            
            out.close();
        }
	}

	}


