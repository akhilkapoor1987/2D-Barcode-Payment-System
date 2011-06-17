package sjsu.mobilepayment.client;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import sjsu.mobilepayment.client.RegisterWallet.MyOnItemSelectedListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class FinalCart1 extends Activity {
	/** Called when the activity is first created. */
	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;
	
	ArrayList<Order> orderList = new ArrayList<Order>();

	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/BuyProduct";
	public String spinnerText = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.cartmain);


        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }
        Bundle extras = getIntent().getExtras();
	final	String user = extras.getString("username");
        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                         "+user+","+ cartlist.size()+" items in Cart");   }     
        ////////////////////////////////////////////
        
      

		final CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
		final CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
;
		final TextView tv0 = (TextView) findViewById(R.id.total);
		final TextView tv1 = (TextView) findViewById(R.id.Scity);
		final TextView tv2 = (TextView) findViewById(R.id.Street);
		final TextView tv3 = (TextView) findViewById(R.id.State);
		final TextView tv4 = (TextView) findViewById(R.id.Zip);
		final EditText et1 = (EditText) findViewById(R.id.ShipStreet);
		final EditText et2 = (EditText) findViewById(R.id.ShipCity);
		final EditText et3 = (EditText) findViewById(R.id.ShipState);
		final EditText et4 = (EditText) findViewById(R.id.ShipZip);

		final TextView tv5 = (TextView) findViewById(R.id.registerWalletBank);
		final TextView tv6 = (TextView) findViewById(R.id.registerWalletBankAcc);

		final Spinner s = (Spinner) findViewById(R.id.creditCardTypeSpinner);
		ArrayAdapter<CharSequence> aa = ArrayAdapter
				.createFromResource(this, R.array.cardType,
						android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(aa);
		s.setOnItemSelectedListener(new MyOnItemSelectedListener());

		final TextView tv8 = (TextView) findViewById(R.id.registerWalletCardNum);
		final TextView tv9 = (TextView) findViewById(R.id.registerWalletExpDate);
		final EditText et5 = (EditText) findViewById(R.id.bankName);
		final EditText et6 = (EditText) findViewById(R.id.bankAcc);
		final EditText et7 = (EditText) findViewById(R.id.creditCardNum);
		final EditText et8 = (EditText) findViewById(R.id.expDate);

		final Button Buy_btn = (Button) findViewById(R.id.finalBuy);
		final Button Catalog = (Button) findViewById(R.id.ProductCatalog);
		final ScrollView scroll = (ScrollView) findViewById(R.id.ScrollView01);
		final ScrollView scroll2 = (ScrollView) findViewById(R.id.ScrollView02);

		
		final String amount = extras.getString("FinalCartAmount");
		String memId = extras.getString("memId");
		final String memberId_recharge = memId;
		tv0.setText(amount);
		
		
		Catalog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Bundle extras = getIntent().getExtras();
				String memId = extras.getString("memId");
String user = extras.getString("username");
				Intent newIntent = new Intent(v.getContext(), ProductList.class);

				newIntent.putExtra("memberId", memId);
		newIntent.putExtra("username",user);
				startActivity(newIntent);
			}
		});

		Buy_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				


				LayoutInflater factory = LayoutInflater.from(FinalCart1.this);
				// final View textEntryView =
				// factory.inflate(R.layout.dialog_login, null);
				AlertDialog.Builder alert = new AlertDialog.Builder(
						FinalCart1.this);
				alert.setTitle("Authorize Payment");
				String cardNumber = et7.getText().toString();

				if (cardNumber.length() < 1) {
					alert.setMessage("Click OK to authorize payment of $"
							+ amount + " from your registered Wallet");

					// alert.setView(textEntryView);

					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									LayoutInflater factory = LayoutInflater
											.from(FinalCart1.this);
									final View textEntryView = factory.inflate(
											R.layout.dialog_login, null);
									AlertDialog.Builder alert = new AlertDialog.Builder(
											FinalCart1.this);
									alert.setTitle("Pin");
									alert.setMessage("Enter 4 digit A/C pin to authorize payment");

									alert.setView(textEntryView);

									alert.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// String value =
													// input.getText().toString();
													EditText mUserText;
													mUserText = (EditText) textEntryView
															.findViewById(R.id.txt_password);
													String strPinCode = mUserText
															.getText()
															.toString();
													Log.i("Pin Value :",
															strPinCode);
													String[] returnData = sendDataToServer(strPinCode);

													if (returnData[0].equals(new String("pinwrong"))) {
														Toast.makeText(
																getBaseContext(),
																"Wrong Pin! Click OK to Re-Enter Pin",
																Toast.LENGTH_LONG)
																.show();
														return;

													}
													
													
													
													else if (returnData[0].equals(new String("balanceless"))) {
														Toast.makeText(
																getBaseContext(),
																"Insufficient Wallet Balance!",
																Toast.LENGTH_LONG)
																.show();
														
														
														Intent newIntent = new Intent(
																getBaseContext(),
																RechargeWallet.class);
														newIntent.putExtra(
																"username",
																user);
														newIntent.putExtra(
																"memberId",
																memberId_recharge);
														startActivityForResult(newIntent,0); //// REMEMBER USING STARTACTIVITY////
                                                        return;

													}

													else {
														Log.i("new intent",
																"entityContents = "
																		+ returnData[3]);

														Intent newIntent = new Intent(
																getBaseContext(),
																Receipt.class);
														newIntent.putExtra(
																"username",
																user);
														
														
														newIntent.putExtra(
																"Amt",
																returnData[0]);
														newIntent.putExtra(
																"newAmt1",
																returnData[1]);
														newIntent.putExtra(
																"orderNum",
																returnData[2]);
														newIntent.putExtra(
																"memberId",
																returnData[3]);
														startActivity(newIntent);
													}
												}
											});

									alert.show();

								}
							});

					alert.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									return;
								}
							});
					alert.show();
				} else {

					alert.setMessage("Click OK to authorize payment of $"
							+ amount
							+ " from your card ending xxxxxxxxxxxx"
							+ cardNumber.substring(cardNumber.length() - 4,
									cardNumber.length() - 1));

					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									LayoutInflater factory = LayoutInflater
											.from(FinalCart1.this);
									final View textEntryView = factory.inflate(
											R.layout.dialog_login, null);
									AlertDialog.Builder alert = new AlertDialog.Builder(
											FinalCart1.this);
									alert.setTitle("Pin");
									alert.setMessage("Enter 4 digit A/C pin to authrize payment");

									alert.setView(textEntryView);

									alert.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// String value =
													// input.getText().toString();
													EditText mUserText;
													mUserText = (EditText) textEntryView
															.findViewById(R.id.txt_password);
													String strPinCode = mUserText
															.getText()
															.toString();
													Log.i("Pin Value :",
															strPinCode);
													String[] returnData = sendDataToServer(strPinCode);

													if (returnData[0].equals(new String("pinwrong"))) {
														Toast.makeText(
																getBaseContext(),
																"Wrong Pin! Click OK to Re-Enter Pin",
																Toast.LENGTH_LONG)
																.show();
														return;

													}
													
													
//													
//													else if (returnData[0].equals(new String("balanceless"))) {
//														Toast.makeText(
//																getBaseContext(),
//																"Insufficient Wallet Balance!",
//																Toast.LENGTH_LONG)
//																.show();
//														
//														
//														Intent newIntent = new Intent(
//																getBaseContext(),
//																RechargeWallet.class);
//														
//														newIntent.putExtra(
//																"memberId",
//																memberId_recharge);
//														
//														startActivityForResult(newIntent,0); //// REMEMBER USING STARTACTIVITY////
//                                                        return;
//													}

													else {
														Log.i("new intent",
																"entityContents = "
																		+ returnData[3]);

														Intent newIntent = new Intent(
																getBaseContext(),
																Receipt.class);
														newIntent.putExtra(
																"username",
																user);
														newIntent.putExtra(
																"Amt",
																returnData[0]);
														newIntent.putExtra(
																"newAmt1",
																returnData[1]);
														newIntent.putExtra(
																"orderNum",
																returnData[2]);
														newIntent.putExtra(
																"memberId",
																returnData[3]);
														startActivity(newIntent);
													}

												}
											});

									alert.show();
								}
							});

					alert.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									return;
								}
							});
					alert.show();
				}

			}
		});
		checkbox.setOnClickListener(new OnClickListener() { // checkbox listener

			public void onClick(View v) {
				// Perform action on clicks, depending on whether it's now
				// checked
				if (((CheckBox) v).isChecked()) {
					scroll.setVisibility(8);
					tv1.setVisibility(8); // visible==0
					tv2.setVisibility(8);
					tv3.setVisibility(8);
					tv4.setVisibility(8);
					et1.setVisibility(8);
					et2.setVisibility(8);
					et3.setVisibility(8);
					et4.setVisibility(8);

				} else if (((CheckBox) v).isChecked() == false) {
					scroll.setVisibility(0);
					tv1.setVisibility(0); // visible==0
					tv2.setVisibility(0);
					tv3.setVisibility(0);
					tv4.setVisibility(0);
					et1.setVisibility(0);
					et2.setVisibility(0);
					et3.setVisibility(0);
					et4.setVisibility(0);
				}
			}
		});

		checkbox1.setOnClickListener(new OnClickListener() { // checkbox
																// listener

					public void onClick(View v) {
						// Perform action on clicks, depending on whether it's
						// now checked
						if (((CheckBox) v).isChecked()) {
							scroll2.setVisibility(8);
							tv5.setVisibility(8); // visible==0
							tv6.setVisibility(8);
							s.setVisibility(8);
							tv8.setVisibility(8);
							tv9.setVisibility(8);

							et5.setVisibility(8);
							et6.setVisibility(8);
							et7.setVisibility(8);
							et8.setVisibility(8);

						} else if (((CheckBox) v).isChecked() == false) {
							scroll2.setVisibility(0);
							tv5.setVisibility(0); // visible==0
							tv6.setVisibility(0);
							s.setVisibility(0);
							tv8.setVisibility(0);
							tv9.setVisibility(0);

							et5.setVisibility(0);
							et6.setVisibility(0);
							et7.setVisibility(0);
							et8.setVisibility(0);
						}
					}
				});

	}

	public String[] sendDataToServer(String pin){

	
		
		Bundle extras = getIntent().getExtras();
		String amount = extras.getString("FinalCartAmount");
		String memId = extras.getString("memId");

		String size = extras.getString("size");
		int s = Integer.parseInt(size);

		// String[] con = new String[s];
		String pid = "";
		// String selqty="";
		for (int j = 0; j <= s - 1; j++) {
			String ID = extras.getString("FinalCartObjectID" + j);

			Device myParcelableObject = (Device) extras
					.getParcelable("FinalCartObject" + ID);

			pid += myParcelableObject.getpdId() + "%"
					+ myParcelableObject.getSelQty() + "@";
			Log.i("pIDDDDDDDDDDD", pid);
		}

		Log.i("memberId in FinalCart1 .java is :", memId);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("Pin", pin)); // ////////////////*********************************
														// Check Pin At Server
														// Side***************/////////////////////
		nvps.add(new BasicNameValuePair("Amount", amount));
		nvps.add(new BasicNameValuePair("ProductId", pid));
		// nvps.add(new BasicNameValuePair("Quantity", selqty));
		nvps.add(new BasicNameValuePair("size", size));
		nvps.add(new BasicNameValuePair("memId", memId));
		
		///////// QR Code Encoding for Data to be sent to server//////////
		BarcodeFormat QR_CODE=BarcodeFormat.QR_CODE;
		Hashtable<EncodeHintType,Object> hints = null;
		String encoding = nvps.toString();
	       Log.i("Encoding", encoding);

	   	if (encoding != null) {
		      hints = new Hashtable<EncodeHintType,Object>(2);
		      hints.put(EncodeHintType.CHARACTER_SET, encoding);
		    }
		    try {
				QRCodeWriter writer = new QRCodeWriter();    
				BitMatrix result = writer.encode(encoding, QR_CODE, 300, 300, hints);
				   Log.i("Result", result.toString());
				   
				   Log.i("Result", result.toString());

				    int width = result.getWidth();
				    int height = result.getHeight();
				    int[] pixels = new int[width * height];
				       Log.i("Result", pixels.toString());

				    // All are 0, or black, by default
				    for (int y = 0; y < height; y++) {
				      int offset = y * width;
				      for (int x = 0; x < width; x++) {
				        pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
				      }
				    }

				    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
				  ByteBuffer abc = ByteBuffer.allocate(4096);
			bitmap.copyPixelsToBuffer(abc);
			

//					nvps.add(new BasicNameValuePair("QRCode", abc.toString()));
			} catch (WriterException e1) {
				// TODO Auto-generated catch block
				e1.getMessage();
			}
				
				
				//////////////////////////////////////----xxxx----///////////////////////////////////////
				
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		String entityContents = null;
		try {
			UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
					HTTP.UTF_8);
			httppost.setEntity(p_entity);
			HttpResponse response = client.execute(httppost);

			InputStream is = null;
			HttpEntity entity = response.getEntity();

			byte buffer[] = new byte[4096];
			is = entity.getContent();
			int numBytes = is.read(buffer);
			is.close();
			entityContents = new String(buffer, 0, numBytes);

			// HttpResponse response = client.execute(httppost);
			Log.i("NNNNNNNNNNNNNNNNNNN",
					"content length= " + entityContents.toString());
			Log.i("NNNNNNNNNNNNNNNNNNN", "content length= "
					+ entityContents.substring(0).length());
			Log.i("Net", "Response = " + response.getEntity());
		} catch (Exception e) {
			Log.e("Net", "Error in network call", e);
		}
		Log.i("YYYYYYYYYYYYYYYYYY", "entityContents = " + entityContents);
		
	
//		if(entityContents.equals(new String("pinwrong")))
//		{
//			
//			Log.i("The pin is wrong", "pinwrong");
//
//			String[] entityContentsSplit = {};
//			entityContentsSplit[0] = "pinwrong";
//		}
//		
//		else if(entityContents.equals(new String("balanceless")))
//			
//		{
//			Log.i("The balance is less", "balanceless");
//
//			String[] entityContentsSplit = {};
//			entityContentsSplit[0] = "balanceless";
//
//		}
//		
//		else{
		String[] entityContentsSplit = entityContents.trim().split("\\*");
		return entityContentsSplit;

		
		// Amt +"*"+newAmt1+"*"+orderNum+"*"+memberId

		

		// Intent newIntent = new Intent(v.getContext(),TransDetails.class);
		// startActivity(newIntent);

	}
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.shoppingcart1, menu);
	     return true;
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		  Bundle extras = getIntent().getExtras();
		 	 String memberId=  extras.getString("memberId");
		 	 String user=  extras.getString("username");

		  switch (item.getItemId()) {
	     
	   
	     /////////// CART  ////////
	         case R.id.home:    { 
	       	  
	       	  Intent Cart = new Intent(FinalCart1.this ,mainAfterLogin.class);
	       	 Cart.putExtra("memberId", memberId);
	       	Cart.putExtra(
					"username",
					user);
	       	 startActivity(Cart);
	       	  break; }
	     
	         
	         ///////////  LOGOUT  ////////
	         case R.id.logout:    {
	       final List<Device> mCartList = ShoppingCartHelper.getCart();
	   mCartList.clear();
	       Intent login = new Intent(FinalCart1.this, main.class );
	       	startActivity(login);
	       	
	       	break; }
	    
	       ///////////  ABOUT US  ////////
	       case R.id.aboutus:   { 
	       	
	       	final Dialog dialog = new Dialog(FinalCart1.this);
				dialog.setContentView(R.layout.aboutus);
				dialog.setTitle("					About Us");
				dialog.setCancelable(true);

				TextView abtUs = (TextView) dialog.findViewById(R.id.textView1);
				abtUs.setText(R.string.aboutus);
				Button Goback = (Button) dialog.findViewById(R.id.button1);
				Goback.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				// now that the dialog is set up, it's time to show it
				dialog.show();
	       	
	       	
	       	break; }
	     }
	     return true;
	 }
	
	

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			spinnerText = parent.getItemAtPosition(pos).toString();
		}

		public void onNothingSelected(AdapterView parent) {

			Toast.makeText(parent.getContext(), "The planet is ",
					Toast.LENGTH_LONG).show();
			// Do nothing.
		}
	}

}
