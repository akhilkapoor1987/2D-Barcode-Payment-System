package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class ShoppingServlet
 */
@WebServlet("/ShoppingServlet")
public class ShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(ServletConfig conf) throws ServletException  {
	    super.init(conf);
	    System.out.println("inside the ShoppingServlet.java servlet");
		try {
			ConnectionManager pool = new ConnectionManager();
			System.out.println("connection instance acquired");
		} catch (Exception e) {
			System.out.println("init fails");
		}
	}	  

    
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	//@SuppressWarnings("null")
	public void doPost (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException ,ClassCastException , NoSuchMethodError {
        PrintWriter out = res.getWriter();
        Vector<CD> buylist= (Vector<CD>)req.getAttribute("com.mobilepayment.servlet");
	 
		 
		 
		 
		 
		/* recent	  HttpSession session = req.getSession(false);
		  if (session == null) {
		    res.sendRedirect("http://localhost:8080/error.html");
		  }
		 String memberId3 =(String)session.getAttribute("memberId");
//		 req.setAttribute("memberId", memberId3);

		 // System.out.println((session.getAttribute("com.mobilepayment.servlet"));
		 System.out.println(" My member id is " +  memberId3);
		 @SuppressWarnings("unchecked")
		Vector<CD> buylist=
		    (Vector<CD>)session.getAttribute("com.mobilepayment.servlet");
	
		 String action3 = req.getParameter("Submit1");
		 System.out.println("********"+action3);
		 String action1= req.getParameter("Submit2");
		 System.out.println("!!!!!!!!!!!!!!!!"+action1);
		 String action= req.getParameter("action");
		 System.out.println("!!!!!!!!!!!!!!!!"+action);
		 if(action3==null)
			  action3="";
		 if(action==null)
			 action="";
		 if (action1 == null) 
		 action1="";
		 
		 if (!action.equals("CHECKOUT")) {
			 
				if (action.equals("DELETE")) {
					action = null;
		      String del = req.getParameter("delindex");
		      int d = (new Integer(del)).intValue();
		      buylist.removeElementAt(d);
		      System.out.println(" Item Deleted");
		      if(buylist.isEmpty())
		      {
		    	  session.setAttribute("com.mobilepayment.servlet", buylist);
		    	  String url="/ProductCatalog";
		    	    ServletContext sc = getServletContext();
		    	    RequestDispatcher rd = sc.getRequestDispatcher(url);
		    	    System.out.println("i am Done from here");
		    	    rd.forward(req, res);      }
				}		    	 
				else if(action1.equals("Product Details"))
				   {action1 = null;   
					System.out.println("Get Details");
				    String pId = (String) req.getParameter("CD");
//					 req.setAttribute("memberId", memberId3);

				   req.setAttribute("CD", pId);
//					       String memberId = (String) req.getAttribute("memberId");
//						  	req.setAttribute("memberID", memberId);
						  	    String url="/ProductDetails.jsp";
						  	    ServletContext sc = getServletContext();
						  	    RequestDispatcher rd1 = sc.getRequestDispatcher(url);
						  	    rd1.forward(req,res);	
						  } recent */
			 //  else if (action3.equals("Add to Cart")) {
		      //any previous buys of same cd?
				//   action3 = null;
	       
		 boolean match=false;
	String memberId3 = "abcd";
		      CD aCD = getCD(req,memberId3);
		      if (buylist==null) {
		        //add first cd to the cart
		    	 buylist = new Vector<CD>(); //first order
		       buylist.addElement(aCD);
		      }
		        else { // not first buy
		        for (int i=0; i< buylist.size(); i++) {
		          CD cd = (CD) buylist.elementAt(i);
		          if (cd.getpdId().equals(aCD.getpdId())) {
		        	  System.out.println("Value from acd object id " +  aCD.getQuantity() );
		        	  
		        	  System.out.println("Value from cd object id " +  cd.getQuantity() );
		        	  
		        	  cd.setQuantity((cd.getQuantity())+ (aCD.getQuantity())  );
		          
		            buylist.setElementAt(cd,i);
		            match = true;
		          } //end of if name matches
		        } // end of for
		        if (!match) 
		          buylist.addElement(aCD);
		      }
			   out.println();
//		    session.setAttribute("com.mobilepayment.servlet", buylist);
//			   
//		    System.out.println(" I am atleast hereeeeeeeeee");
//		    String url="/Cart.jsp";
//		    ServletContext sc = getServletContext();
//		    RequestDispatcher rd3 = sc.getRequestDispatcher(url);
//		    rd3.forward(req, res); 
		  }
	
	
	
	
	
	/*	 else if (action.equals("logout"))
		 {
			 System.out.println("in the logout");
			 session.invalidate();
			  String url="/index.jsp";
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd2 = sc.getRequestDispatcher(url);
			    rd2.forward(req,res);
		 }
		    else if (action.equals("CHECKOUT"))  {
		    	action = null;
		    float total =0;
		    for (int i=0; i< buylist.size();i++) {
		      CD anOrder = (CD) buylist.elementAt(i);
		      float price= anOrder.getprice();
		      int qty = (anOrder.getQuantity());
		      total += (price * (qty));
		    }
		    total += 0.005;
		    String amount = new Float(total).toString();
		    int n = amount.indexOf('.');
		    amount = amount.substring(0,n+3);
		    req.setAttribute("amount",amount);
		    session.setAttribute("com.mobilepayment.servlet", buylist);
		    
		    System.out.println(amount);
		    String url="/Checkout.jsp";
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd2 = sc.getRequestDispatcher(url);
		    rd2.forward(req,res);
		  }
	}*/

	
	  private CD getCD(HttpServletRequest req,String memberId3) {
		    //imagine if all this was in a scriptlet...ugly, eh?
		//    String myCd = req.getParameter("CD");
		//    String qty = req.getParameter("qty");
		//    System.out.println("here are the values:" + myCd + qty);
		//    StringTokenizer t = new StringTokenizer(myCd,"|");
//		    String pdId= t.nextToken();
//		    String pname = t.nextToken();
//		    String pdesc = t.nextToken();
//		    String price = t.nextToken();
//		    price = price.replace('$',' ').trim();
		  String pdId= req.getParameter("pdid");
		    String pname = req.getParameter("pname");
		    String pdesc = req.getParameter("pdesc");
		    String price =  req.getParameter("price");
		    String qty   = req.getParameter("qty");
		  //  price = price.replace('$',' ').trim();
		   
		    CD cd = new CD();
		    cd.setpdId(pdId);
		    cd.setpname(pname);
		    cd.setpdesc(pdesc);
		    cd.setprice((new Float(price)).floatValue());
		    cd.setQuantity((new Integer(qty).intValue()));
		    cd.setMemberId(memberId3);
		    return cd;
		   }


}
