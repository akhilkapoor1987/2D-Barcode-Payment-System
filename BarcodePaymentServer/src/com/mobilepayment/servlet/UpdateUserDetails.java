package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.http.message.BasicNameValuePair;

@WebServlet("/UpdateUserDetails")
public class UpdateUserDetails extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public UpdateUserDetails() {
        super();
    }

    private ConnectionManager pool;
	
	public void init() throws ServletException{
    	System.out.println("inside the Update user details servelets");
        try{
            pool = new ConnectionManager();
        }
        catch(Exception e){
        	System.out.println("init fails");
        }
    }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		Connection con1 = null;
		Connection con4 = null;
		Connection conupd = null;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String memId = request.getParameter("memberId");
        System.out.println("username is "+memId);
        
        String fname = request.getParameter("firstname");
        String lname = request.getParameter("lastname");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String  pwd= request.getParameter("password");
        String  email= request.getParameter("email");
         float UPDwallet =0;
        String Bname = request.getParameter("bankNamestr");
        String Bacc = (request.getParameter("bankAccstr"));
     // int bacc = Integer.parseInt(Bacc);
//        String Ccnum = request.getParameter("creditCardNumstr");
        String Ccnum= (request.getParameter("creditCardNumstr")); 
        String expDate = request.getParameter("expDatestr");
        String Paymethod = request.getParameter("paymentmethod");
        String amt = request.getParameter("amountstr");
        float Amt= Float.valueOf(amt);
        int walletId = 0;
        float walletAmt =0;
        try {
        con =  pool.getConnection();
        con1 =  pool.getConnection();  
        con4 = pool.getConnection();  
		//   con= (Connection)pool.getConnection();
        int memId1= Integer.parseInt(memId);
		String q2= "UPDATE bmps_members SET FirstName='"+fname+"',LastName='"+lname+"',address ='"+address+"',city = '"+city+"',state='"+state+"',password='"+pwd+"',email='"+email+"' WHERE memberId = '"+ memId1  +"'";
		System.out.println(q2);

		//@SuppressWarnings("null")
		PreparedStatement stmt = con.prepareStatement(q2);
   		stmt.executeUpdate(q2);
    	
	//--------------------------------------------------------------------------------------------------------->
	
    	String ewallet= " select walletId,walletAmt from bmps_wallet where memberId = '"+ memId1 +"' ";
		System.out.println(ewallet);

		//@SuppressWarnings("null")
		PreparedStatement pst4 = con4.prepareStatement(ewallet);
		
	ResultSet rs1 = pst4.executeQuery();
 	rs1 = pst4.getResultSet();
//	pst.close();
	while (rs1.next()) {
    walletId = rs1.getInt(1);
    walletAmt = rs1.getFloat(2);
	}
	
	   //--------------------------------------------------------------------------------------------------->	
    	
	UPDwallet = Amt+ walletAmt;
    	String q1= "UPDATE bmps_wallet SET walletAmt='"+UPDwallet+"',bName='"+Bname+"',bAccount ='"+Bacc+"',cardType= '"+Paymethod+"',expDate='"+expDate+"',ccNum='"+Ccnum+"' WHERE walletId = '"+ walletId  +"'";
		System.out.println(q1);

		//@SuppressWarnings("null")
		PreparedStatement stmt1 = con.prepareStatement(q1);
   		stmt1.executeUpdate(q1);
   		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		conupd = pool.getConnection();
		String DebitUpd = "INSERT INTO wallet_statement (member_id,trans_date,credit,debit) VALUES ('"
				+ memId1 + "','" + dateFormat.format(date).toString()+ "','"+Amt+"','"+0+"')";

		PreparedStatement pst15 = conupd.prepareStatement(DebitUpd);
		pst15.executeUpdate(DebitUpd);
		pst15.close();
    	con1.close();
    	con4.close();
    	con.close();	
		}catch (Exception e)
			{System.out.println(e.getMessage());}
	       out.println(memId);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
