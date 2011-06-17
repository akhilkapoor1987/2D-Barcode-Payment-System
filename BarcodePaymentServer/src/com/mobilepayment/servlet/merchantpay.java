package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Servlet implementation class BuyProduct
 */
@WebServlet("/merchantpay")
public class merchantpay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ConnectionManager pool;

	public void init() throws ServletException {
         System.out.println("inside the Instant Pay servlet");
         		try {

 			pool = new ConnectionManager();

 		} catch (Exception e) {
 			System.out.println("init fails");
 		}
         }
       
   		
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public merchantpay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,NullPointerException,NotSerializableException {
		// TODO Auto-generated method stub
		  PrintWriter out = response.getWriter();
    		  Connection con4 = null;
			 
			
String Id =(request.getParameter("id"));
String amt =(request.getParameter("Amt"));
String amt1 =(request.getParameter("chargedAmt"));

String walletAmt="";
String walletId="";
String content="";
int id1 =0;
float balance =0;
float updBalance=0;
			
			try {
				  int walletid =Integer.parseInt(Id);	   
		
				
			// ----------------------------------------------------
			
			//-------------------------------------------------------------------------
	
				con4 =  pool.getConnection();
				   
			
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				Random Generator = new Random();
				String orderNum = Integer.toString(Math.abs(Generator.nextInt()));
		String		FullContent = content + "#" + amt1 + "#"
						+ dateFormat.format(date).toString() + "#" + orderNum;
			
		int id = Integer.parseInt(Id);
		String q6 = " select memberId from bmps_wallet where walletId = '"
			+ id + "' ";
	System.out.println(q6);

	// @SuppressWarnings("null")
	PreparedStatement pst6 = con4.prepareStatement(q6);

	ResultSet rs6 = pst6.executeQuery();
	rs6 = pst6.getResultSet();
	// pst.close();
	while (rs6.next()) {
		// memberType = results.getString("memberType");

		 id1 = rs6.getInt(1);
			}
				Connection	con8 = pool.getConnection();
					String InsforHist = "INSERT INTO bmps_transhistory (memerId,BOB) VALUES ('"
							+ id1 + "','" + FullContent + "')";

					PreparedStatement pst12 = con8.prepareStatement(InsforHist);
					pst12.executeUpdate(InsforHist);
					pst12.close();
	//////////////////////////////////////////////Insert for wallet_statement//////////////////////////////////
					
				Connection	conupd = pool.getConnection();
					String DebitUpd = "INSERT INTO wallet_statement (member_id,trans_date,credit,debit) VALUES ('"
							+ id1 + "','" + dateFormat.format(date).toString()+ "','"+0+"','"+amt1+"')";

					PreparedStatement pst15 = conupd.prepareStatement(DebitUpd);
					pst15.executeUpdate(DebitUpd);
					pst15.close();
					
					///////////////Select Amt from Wallet///////////
					Connection con10= pool.getConnection();
					String q10 = " select walletAmt from bmps_wallet where memberId = '"
							+ id1 + "' ";
					System.out.println(q10);
					PreparedStatement pst = con10.prepareStatement(q10);
					ResultSet rs = pst.executeQuery();
					rs = pst.getResultSet();
					while (rs.next()) {
					balance = rs.getFloat(1);
					}
								
					////////////////////////////////////////////////
					float debitAmt = Float.valueOf(amt1);
				updBalance = balance-debitAmt;
					////////////////Update wallet balance after deduction//////////
					Connection walletUpd = pool.getConnection();
					String walletUpd1 = "update bmps_wallet set walletAmt = '"
						+ updBalance
						+ "' where memberId = '"
						+ id1
						+ "'";
				System.out.println(walletUpd1);
				// @SuppressWarnings("null")
				PreparedStatement stmt2 = walletUpd.prepareStatement(walletUpd1);
				stmt2.executeUpdate(walletUpd1);
			}
		 catch(SQLException ex) {
				System.err.println("SQLException: " + ex.getMessage());
			}
		content ="Transaction Accepted";
		out.println(content);
	System.out.println(content);
	}



}


	