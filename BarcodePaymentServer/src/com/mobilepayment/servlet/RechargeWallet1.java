package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RechargeWallet1")
public class RechargeWallet1 extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public RechargeWallet1() {
        super();
    }

    private ConnectionManager pool;
	
	public void init() throws ServletException{
    	System.out.println("inside the RegisterWallet servelets");
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
		Connection conupd =null;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String memberId = request.getParameter("memberId");
        
        System.out.println("username is "+memberId);
        String bankNamestr = request.getParameter("bankNamestr");
        
        String bankAccstr = (request.getParameter("bankAccstr"));
        String creditCardNumstr = (request.getParameter("creditCardNumstr"));
        String expDatestr = request.getParameter("expDatestr");
        String amountstr = request.getParameter("amountstr");
        String paymentmethod = request.getParameter("paymentmethod");
        String pinNumber = request.getParameter("pinNumber");

        float WalAmt = Float.valueOf(amountstr.trim()).floatValue();

//		String q2= "UPDATE bmps_members SET FirstName='"+fname+"',LastName='"+lname+"',address ='"+address+"',city = '"+city+"',state='"+state+"',password='"+pwd+"',email='"+email+"' WHERE memberId = '"+ memId1  +"'";

        String query = "UPDATE  bmps_wallet SET walletAmt='"+ WalAmt +"',bName='"+ bankNamestr +"',bAccount='"+ bankAccstr +"',cardType='"+ paymentmethod +"',expDate='"+ expDatestr +"',ccNum='"+ creditCardNumstr +"',pin='"+ pinNumber +"' WHERE memberId='"+ memberId +"' ";
        /*
        String query = "insert into bmps_account (ACCTSUBTYPE,MEMBERID,BANKID,ACCOUNTNUMBER,AUTHENTICATIONINFO,EXPDATE,ACCOUNTTYPE,AMOUNT) values ('Saving'," +
        		"'" + username + "','" + bankNamestr + "','" + bankAccstr + "','" + creditCardNumstr + "','" + expDatestr + "','" + paymentmethod + "',"+ f+ ")";
        		*/
        System.out.println("SQL is "+query);
        String returnString = "Y";

        try{
        	
        	con = pool.getConnection();
        	PreparedStatement stmt = con.prepareStatement(query);
       		stmt.executeUpdate(query);
       		
       		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			conupd = pool.getConnection();
			String DebitUpd = "INSERT INTO wallet_statement (member_id,trans_date,credit,debit) VALUES ('"
					+ memberId + "','" + dateFormat.format(date).toString()+ "','"+WalAmt+"','"+0+"')";

			PreparedStatement pst15 = conupd.prepareStatement(DebitUpd);
			pst15.executeUpdate(DebitUpd);
			pst15.close();
//        	con = pool.getConnection();
//			java.sql.Statement pstmt = con.createStatement();
//			System.out.println("sql is "+query);
//			
//			pstmt.execute(query);
//			
			
//			String query2 = "update bmps_members set amount = " + f + " where login = '" + username +"'";
//			System.out.println("the update sql is " + query2);
//			pstmt.execute(query2);
//			
		}catch (Exception e)
			{System.out.println("Exception during executing sql!");
			e.getMessage();
			}
        finally {
            if (con != null) pool.returnConnection(con);
            out.println("y");
            out.close();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}