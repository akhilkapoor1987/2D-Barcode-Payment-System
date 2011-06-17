package sjsu.mobilepayment.client;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.TransactionType;
import net.authorize.aim.Result;
import net.authorize.aim.Transaction;
import net.authorize.data.creditcard.CreditCard;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateRegisterWallet extends Activity {

	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/UpdateUserDetails";
	public  String spinnerText  = null;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.updatewallet);


        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }
        Bundle extras1 = getIntent().getExtras();
	final	String user = extras1.getString("username");
        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                                     "+user+","+ cartlist.size()+" items in Cart");   }     
        ////////////////////////////////////////////
        
      
     
        
        Bundle extras = getIntent().getExtras();
        final String firstname = extras.getString("firstname");
        final String lastname = extras.getString("lastname");
        final String address = extras.getString("address");
        final String city = extras.getString("city");
        final String state = extras.getString("state");
        final String password = extras.getString("password");
        final String email = extras.getString("email");
        final String memId = extras.getString("memberId");
        final String wallet = extras.getString("walletcontents");

Log.i("wallet Contents in Update Register Wallet", wallet);

        final Spinner s = (Spinner)findViewById(R.id.creditCardTypeSpinner1);
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.cardType, android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(aa);
        s.setOnItemSelectedListener(new MyOnItemSelectedListener());

        
        Button cancel_button = (Button)findViewById(R.id.walletCancel1);
        Button confirm_button = (Button)findViewById(R.id.walletConfirm1);
        final EditText bankName = (EditText)findViewById(R.id.bankName1);
        final EditText bankAcc = (EditText)findViewById(R.id.bankAcc1);
        final EditText creditCardNum = (EditText)findViewById(R.id.creditCardNum1);
        final EditText expDate = (EditText)findViewById(R.id.expDate1);
        final EditText amount = (EditText)findViewById(R.id.amount1);
        String []walletDetails = wallet.split("\\%");
        amount.setText(walletDetails[0]);
        bankName.setText(walletDetails[1]);
        bankAcc.setText(walletDetails[2]);
        expDate.setText(walletDetails[3]);
        creditCardNum.setText(walletDetails[4]);

        
        cancel_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(v.getContext(),mainAfterLogin.class);
				newIntent.putExtra("memberId", memId);
				newIntent.putExtra("username",user);
				startActivity(newIntent);
			}
		});
        
        confirm_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        final String bankNamestr = bankName.getEditableText().toString();
		        final String bankAccstr = bankAcc.getEditableText().toString();
		        final String creditCardNumstr = creditCardNum.getEditableText().toString();
		        final String expDatestr = expDate.getEditableText().toString();
		        final String amountstr = amount.getEditableText().toString();
//		        final String paymentmethod = (String) spinnerText.getText().toString();
//		        Log.w("paymentmethod","paymentmethod = " + paymentmethod);
				
		        DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httppost  = new HttpPost(url);
				List< BasicNameValuePair > nvps = new ArrayList< BasicNameValuePair >();
				nvps.add(new BasicNameValuePair("memberId", memId));
	            nvps.add(new BasicNameValuePair("bankNamestr", bankNamestr));
	            nvps.add(new BasicNameValuePair("bankAccstr", bankAccstr));
	            nvps.add(new BasicNameValuePair("creditCardNumstr", creditCardNumstr));
	            nvps.add(new BasicNameValuePair("expDatestr", expDatestr));
	            nvps.add(new BasicNameValuePair("amountstr", amountstr));
	            nvps.add(new BasicNameValuePair("paymentmethod", spinnerText));
	            nvps.add(new BasicNameValuePair("firstname", firstname));
	            nvps.add(new BasicNameValuePair("lastname",lastname ));
	            nvps.add(new BasicNameValuePair("address",address ));
	            nvps.add(new BasicNameValuePair("city",city ));
	            nvps.add(new BasicNameValuePair("state",state ));
	            nvps.add(new BasicNameValuePair("password", password ));
	            nvps.add(new BasicNameValuePair("email", email));

	            String entityContents = null;
				try{
					UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
					httppost.setEntity(p_entity);
					HttpResponse response = client.execute(httppost);
					
					InputStream is = null;
					HttpEntity entity = response.getEntity();
					
					byte buffer[] = new byte[1024];
					is = entity.getContent();
					int numBytes = is.read(buffer);
					is.close();
					entityContents = new String(buffer,0,numBytes);
					
					//HttpResponse response = client.execute(httppost);
					Log.i("NNNNNNNNNNNNNNNNNNN","content length= " + entityContents.toString().length());
					Log.i("Net","Response = " + response.getEntity());
				}catch(Exception e){
					Log.e("Net","Error in network call",e);
				}
			
				

				// /////////////////////////////////////////////Authorize.net////////////////////////////////////////

				String responseMessage = "";

				String apiLoginID = "8X86PaskrUZN";
				String transactionKey = "8r83gXhM26QE2C3j";
				Merchant merchant = Merchant.createMerchant(
						Environment.SANDBOX, apiLoginID, transactionKey);

				// create credit card
				CreditCard creditCard = CreditCard.createCreditCard();
				creditCard.setCreditCardNumber("6011000000000012");
				
				creditCard.setExpirationMonth("11");
				creditCard.setExpirationYear("2018");

				// create transaction
				Transaction authCaptureTransaction = merchant
						.createAIMTransaction(TransactionType.AUTH_CAPTURE,
								new BigDecimal(2));
				authCaptureTransaction.setCreditCard(creditCard);

				Result<Transaction> result = (Result<Transaction>) merchant.postTransaction(authCaptureTransaction);

				if (result.isApproved()) {
					responseMessage = "Approved! \n" + "Transaction Id: "
							+ result.getTarget().getTransactionId()
							+ "\n Authorization Code: "
							+ result.getTarget().getAuthorizationCode();

					
				} else if (result.isDeclined()) {
					responseMessage = "Declined."+result.getReasonResponseCode() + " : "
							+ result.getResponseText();
				} else {
					responseMessage = "Error."+result.getReasonResponseCode() + " : "
					+ result.getResponseText();
				}

				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(UpdateRegisterWallet.this);
				dialog.setContentView(R.layout.aboutus);
				dialog.setTitle("Payment Authorization");
				dialog.setCancelable(true);

				TextView abtUs = (TextView) dialog.findViewById(R.id.textView1);
				abtUs.setText(responseMessage);
				Button Goback = (Button) dialog.findViewById(R.id.button1);
				Goback.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent newIntent = new Intent(v.getContext(),mainAfterLogin.class);
						newIntent.putExtra("memberId", memId);
						newIntent.putExtra("username", user);
						startActivity(newIntent);
										}
				});
				// now that the dialog is set up, it's time to show it
				dialog.show();

				//////////////////////////////////////////////////////////////

				
				
				
				
				
				
				
			}
		});
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	        spinnerText = parent.getItemAtPosition(pos).toString();}

	    	
	        public void onNothingSelected(AdapterView parent) {
	        	
	        	 Toast.makeText(parent.getContext(), "The planet is ", Toast.LENGTH_LONG).show();
	      // Do nothing.
	    }
	}
}
