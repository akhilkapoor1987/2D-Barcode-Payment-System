
package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterWallet1")
public class RegisterWallet1 extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public RegisterWallet1() {
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
		Connection conupd=null;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        System.out.println("username is "+username);
        String bankNamestr = request.getParameter("bankNamestr");
        String id = request.getParameter("Id");
        String bankAccstr = (request.getParameter("bankAccstr"));
        String creditCardNumstr = (request.getParameter("creditCardNumstr"));
        String expDatestr = request.getParameter("expDatestr");
        String amountstr = request.getParameter("amountstr");
        String paymentmethod = request.getParameter("paymentmethod");
        String pinNumber = request.getParameter("pinNumber");

        float f = Float.valueOf(amountstr.trim()).floatValue();
int Id = Integer.parseInt(id);
        
        String query = "insert into bmps_wallet (memberId,walletAmt,bName,bAccount,cardType,expDate,ccNum,pin) values ('"+Id+"'," +
		"'" + f + "','" + bankNamestr+ "','" + bankAccstr + "','" + paymentmethod + "','" + expDatestr + "','" +creditCardNumstr + "','"+ pinNumber +"')";
        /*
        String query = "insert into bmps_account (ACCTSUBTYPE,MEMBERID,BANKID,ACCOUNTNUMBER,AUTHENTICATIONINFO,EXPDATE,ACCOUNTTYPE,AMOUNT) values ('Saving'," +
        		"'" + username + "','" + bankNamestr + "','" + bankAccstr + "','" + creditCardNumstr + "','" + expDatestr + "','" + paymentmethod + "',"+ f+ ")";
        		*/
        System.out.println("SQL is "+query);
        String returnString = "Y";

        try{
			con = pool.getConnection();
			java.sql.Statement pstmt = con.createStatement();
			System.out.println("sql is "+query);
			
			pstmt.execute(query);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			conupd = pool.getConnection();
			String DebitUpd = "INSERT INTO wallet_statement (member_id,trans_date,credit,debit) VALUES ('"
					+ Id + "','" + dateFormat.format(date).toString()+ "','"+f+"','"+0+"')";

			PreparedStatement pst15 = conupd.prepareStatement(DebitUpd);
			pst15.executeUpdate(DebitUpd);
			
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