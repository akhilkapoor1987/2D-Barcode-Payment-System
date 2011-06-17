package com.mobilepayment.servlet;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
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

import com.google.zxing.common.BitMatrix;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Servlet implementation class BuyProduct
 */
@WebServlet("/BuyProduct")
public class BuyProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ConnectionManager pool;

	public void init() throws ServletException {
		System.out.println("inside the Buy Product servlet");
		try {

			pool = new ConnectionManager();

		} catch (Exception e) {
			System.out.println("init fails");
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BuyProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			NullPointerException, NotSerializableException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Connection con = null;
		Connection con2 = null;
		// Connection con3 = null;
		Connection con4 = null;
		Connection con5 = null;
		Connection con6 = null;
		Connection con8 = null;
		Connection con9 = null;
		Connection con10 = null;
		Connection con11 = null;
		Connection conupd=null;
//		String QRCode = request.getParameter("QRCode");
//		System.out.println("Encoded Message from Client"+ QRCode);
//		  ByteBuffer abc = ByteBuffer.allocate(4096);
//          abc = ByteBuffer.wrap(QRCode.getBytes());
//          System.out.println("ByteBuffer to string :"+abc.toString());
//          System.out.println("ByteBuffer to char array string:"+abc.array().toString());

          
          
//		BitMatrix  QRCode1 = QRCode;
		String Amt = request.getParameter("Amount");
		float amt1 = Float.valueOf(Amt);
		int s = Integer.parseInt((request.getParameter("size")));
		String memberId = (request.getParameter("memId"));
		String pin = (request.getParameter("Pin"));
		String pin1 = "";
		boolean noPin = false;
		float walletAmt = 0;
		String pinwrong = "";
		String balanceless = "";

		try {

			// Connection con= null;
			con10 = pool.getConnection();

			// con= (Connection)pool.getConnection();
			String q10 = " select walletAmt,pin from bmps_wallet where memberId = '"
					+ memberId + "' ";
			System.out.println(q10);

			// @SuppressWarnings("null")
			PreparedStatement pst = con10.prepareStatement(q10);

			ResultSet rs = pst.executeQuery();
			rs = pst.getResultSet();
		

			while (rs.next()) {
				// memberType = results.getString("memberType");

				walletAmt = rs.getFloat(1);
				pin1 = rs.getString(2);
			}
			System.out.println("available pinis" + pin1);
			System.out.println("available walletAmt is" + walletAmt);

			con10.close();

		} catch (SQLException ex) {
			System.err.println("SQLException in pin and balance check: "
					+ ex.getMessage());
		}

		if (pin1.equalsIgnoreCase(pin) && (amt1 < walletAmt))

		{

			String n = "bbb";
			int newQty = 0;
			int newAmt = 0;
			String pname = "";
			String memberID = "";
			String Fname = "";
			String Lname = "";
			String FullName = "";
			String content = "";
			String FullContent = "";
			int price = 0;
			String newAmt1 = "";
			String PID = (request.getParameter("ProductId"));

			String[] words = PID.split("\\@");
			System.out.println(PID);
			for (int i = 0; i <= s - 1; i++) {
				String[] word = words[i].split("\\%");
				int pid = Integer.parseInt(word[0]);
				int selqty = Integer.parseInt(word[1]);

				// String query =
				// "(update bmps_product set quantity = "+q+" where pdId = "+p+")";
				try {

					// Connection con= null;
					con = pool.getConnection();

					// con= (Connection)pool.getConnection();
					String q2 = " select pname,qty,price, memberid from bmps_product where pdId = '"
							+ pid + "' ";
					System.out.println(q2);

					// @SuppressWarnings("null")
					PreparedStatement pst = con.prepareStatement(q2);

					ResultSet rs = pst.executeQuery();
					rs = pst.getResultSet();
					// pst.close();
					while (rs.next()) {
						// memberType = results.getString("memberType");

						pname = rs.getString(1);
						newQty = rs.getInt(2);
						price = rs.getInt(3);
						memberID = Integer.toString(rs.getInt(4));
					}
					System.out.println("available quantity for selected id is "
							+ newQty);
					con.close();

					// ----------------------------------------------------
					con6 = pool.getConnection();

					// con= (Connection)pool.getConnection();
					String q6 = " select Firstname,LastName from bmps_members where memberId = '"
							+ memberID + "' ";
					System.out.println(q6);

					// @SuppressWarnings("null")
					PreparedStatement pst6 = con6.prepareStatement(q6);

					ResultSet rs6 = pst6.executeQuery();
					rs6 = pst6.getResultSet();
					// pst.close();
					while (rs6.next()) {
						// memberType = results.getString("memberType");

						Fname = rs6.getString(1);
						Lname = rs6.getString(2);
						FullName = Fname + "" + Lname;
					}
					System.out.println("FullName for selected id is "
							+ FullName);
					con6.close();

					// -------------------------------------------------------------------------
					con2 = pool.getConnection();
					if (newQty < selqty) {
						out.println(n);

					} else {
						newQty -= selqty;
						String query = "update bmps_product set qty = '"
								+ newQty + "' where pdId = '" + pid + "'";
						System.out.println(query);
						// @SuppressWarnings("null")
						PreparedStatement stmt = con2.prepareStatement(query);
						stmt.executeUpdate(query);
						// stmt.close();
						// rs.close();
						con2.close();
						// rs.close();

						System.out
								.println(" I am done with updating bmps_product database");

						con4 = pool.getConnection();

						System.out.println(" the final amount is " + amt1);
						// con= (Connection)pool.getConnection();
						int memberId1 = Integer.parseInt(memberId);
						String ewallet = " select walletAmt from bmps_wallet where memberId = '"
								+ memberId1 + "' ";
						System.out.println(ewallet);

						// @SuppressWarnings("null")
						PreparedStatement pst4 = con4.prepareStatement(ewallet);

						ResultSet rs1 = pst4.executeQuery();
						rs1 = pst4.getResultSet();
						// pst.close();
						while (rs1.next()) {
							newAmt = rs1.getInt(1);
						}
						System.out.println(+newAmt);
						con4.close();

						con5 = pool.getConnection();
						newAmt -= amt1;
						System.out.println(memberId);

						String updwallet = "update bmps_wallet set walletAmt = '"
								+ newAmt
								+ "' where memberId = '"
								+ memberId
								+ "'";
						System.out.println(updwallet);
						// @SuppressWarnings("null")
						PreparedStatement stmt2 = con5
								.prepareStatement(updwallet);
						stmt2.executeUpdate(updwallet);
						// stmt.close();
						// rs.close();
						con5.close();
						// rs.close();

						System.out
								.println(" it has been updated in the system");

					}

				} catch (SQLException ex) {
					System.err.println("SQLException: " + ex.getMessage());
				}
				if (i >= 1) {
					content += "@" + pname + "$" + FullName + "$" + selqty
							+ "$" + price;

				} else {
					content += pname + "$" + FullName + "$" + selqty + "$"
							+ price;
				}
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Random Generator = new Random();
			String orderNum = Integer.toString(Math.abs(Generator.nextInt()));
			FullContent = content + "#" + Amt + "#"
					+ dateFormat.format(date).toString() + "#" + orderNum;

			try {
				con8 = pool.getConnection();
				String InsforHist = "INSERT INTO bmps_transhistory (memerId,BOB) VALUES ('"
						+ memberId + "','" + FullContent + "')";

				PreparedStatement pst12 = con8.prepareStatement(InsforHist);
				pst12.executeUpdate(InsforHist);
				pst12.close();
//////////////////////////////////////////////Insert for wallet_statement//////////////////////////////////
				
				conupd = pool.getConnection();
				String DebitUpd = "INSERT INTO wallet_statement (member_id,trans_date,credit,debit) VALUES ('"
						+ memberId + "','" + dateFormat.format(date).toString()+ "','"+0+"','"+Amt+"')";

				PreparedStatement pst15 = conupd.prepareStatement(DebitUpd);
				pst15.executeUpdate(DebitUpd);
				pst15.close();
					
/////////////////////////////////////////////////////////////////////////////////////////////////////////				
				con9 = pool.getConnection();
				String q2 = " select walletAmt from bmps_wallet where memberId = '"
						+ memberId + "' ";
				System.out.println(q2);
				PreparedStatement pst = con9.prepareStatement(q2);
				ResultSet rs = pst.executeQuery();
				rs = pst.getResultSet();
				while (rs.next()) {
				
					newAmt1 = Integer.toString(rs.getInt(1));
				}
				pst.close();
				con8.close();
				con9.close();
			}

			catch (SQLException ex) {
				System.err.println("SQLException: " + ex.getMessage());
			}

			out.println(Amt + "*" + newAmt1 + "*" + orderNum + "*" + memberId);
		}

		else if (!pin1.equals(pin)) {
			out.println("pinwrong");
			System.out.println("pinwrong");
		}

		else {
			out.println("balanceless");
			System.out.println("balanceless");
		}

	}
}
