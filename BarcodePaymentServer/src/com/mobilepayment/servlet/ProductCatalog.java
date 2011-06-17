package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;



/**
 * Servlet implementation class ProductCatalog
 */


@WebServlet("/ProductCatalog")
public class ProductCatalog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ConnectionManager pool;

	public void init() throws ServletException {
		System.out.println("inside the ProductCatalog servlet");
		try {

			pool = new ConnectionManager();

		} catch (Exception e) {
			System.out.println("init fails");
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductCatalog() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Reached Product catalog Servlet");
		// TODO Auto-generated method stub
        PrintWriter out = response.getWriter();

		 String memberId2 = (String) request.getParameter("memberId");
		 System.out.println("memberID:" + memberId2);
//		HttpSession session = request.getSession(true);
//		if(session!=null)
		Connection con = null; 
      ArrayList<LinkedList<String>> dataList = new ArrayList<LinkedList<String>>();
      String Items = "";
		try {

			con = pool.getConnection();
		String query = "select pdId,pname,pdesc,price,qty,image from bmps_product";
	PreparedStatement pstmt = con.prepareStatement(query);
	System.out.println("sql is " + query);

	ResultSet rs = pstmt.executeQuery();
			  
			      rs = pstmt.getResultSet();
			      while (rs.next ()){
			    	  
			    	  
			        //Add records into data list
			        
			    	  LinkedList<String> rows = new LinkedList<String>();
			    	  
			    	  rows.add(rs.getString("pdId"));
			        rows.add(rs.getString("pname"));
			        rows.add(rs.getString("pdesc"));
			        rows.add(rs.getString("Price"));
			        rows.add(rs.getString("qty"));
			        String abc = rs.getString("image");
			        rows.add(abc);
			        dataList.add(rows);
			        System.out.println(rows.toString());
			      }
			      rs.close ();
			      pstmt.close ();
			      }catch(Exception e){
			      System.out.println("Exception is ;"+e);
			      }
			      
//					 request.setAttribute("memberId", memberId);

			      for(LinkedList<String> list : dataList)
					{
					
					while (!list.isEmpty()) {
					Items += list.poll()+"|"+list.poll()+"|"+list.poll()+"|"+list.poll()+"|"+list.poll()+"|"+list.poll()+"%";
					
					}}
						
						
			      
			      
			        System.out.println(Items.toString());

			      out.print(Items);

//			      request.setAttribute("data",dataList);
//					 request.setAttribute("memberId", memberId);

//			      request.getParameter(getServletInfo().)
			      
//			  dataList.equals(request);
			  
//System.out.println("This is the memberId" + q);
			    
			    //Dispatching request
			      //String URL= " /Eshop.jsp";
//			      RequestDispatcher dispatcher = request.getRequestDispatcher("Eshop.jsp");
//			      if (dispatcher != null){
//			        dispatcher.forward(request, response);
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
