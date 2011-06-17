package com.mobilepayment.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ProductDetails
 */
@WebServlet("/ProductDetails")
public class ProductDetailsse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		System.out.println("inside the ProductDetails servlet");

	}
	/**
     * @see HttpServlet#HttpServlet()
     */
	
	
    public ProductDetailsse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);
		  if (session == null) {
		    res.sendRedirect("http://localhost:8080/login.jsp");
		  }
		  else
		  {
		  String pId =  (String)req.getAttribute("CD");
		  System.out.println("here are the values from Product Details pages:" + pId ); 
		  String action1 = req.getParameter("action1");
		  if(action1.equals("DETAILS"))
		   {  
		    			req.setAttribute("CD", pId);
				  	    String url="/ProductDetails.jsp";
				  	    ServletContext sc = getServletContext();
				  	    RequestDispatcher rd1 = sc.getRequestDispatcher(url);
				  	    rd1.forward(req,res);	
		   }
		  }
	  }
}
