package sjsu.mobilepayment.client;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//////////////////////////

import java.math.BigDecimal;
import java.util.Map;
import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.TransactionType;
import net.authorize.aim.Result;
import net.authorize.aim.Transaction;
import net.authorize.data.*;
import net.authorize.data.creditcard.*;

public class RegisterWallet extends Activity {

	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/RegisterWallet1";
	public String spinnerText = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerwallet);
		setTitle(R.string.registerWalletWinTitle);
		Bundle extras = getIntent().getExtras();
		final String userName = extras.getString("username");
		final String Id = extras.getString("Id");
		final Spinner s = (Spinner) findViewById(R.id.creditCardTypeSpinner);
		ArrayAdapter<CharSequence> aa = ArrayAdapter
				.createFromResource(this, R.array.cardType,
						android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(aa);
		s.setOnItemSelectedListener(new MyOnItemSelectedListener());

		Button cancel_button = (Button) findViewById(R.id.walletCancel);
		Button confirm_button = (Button) findViewById(R.id.walletConfirm);
		final EditText bankName = (EditText) findViewById(R.id.bankName);
		final EditText bankAcc = (EditText) findViewById(R.id.bankAcc);
		final EditText creditCardNum = (EditText) findViewById(R.id.creditCardNum);
		final EditText expDate = (EditText) findViewById(R.id.expDate);
		final EditText amount = (EditText) findViewById(R.id.amount);

		cancel_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(v.getContext(),
						RegisterUserID.class);
				startActivity(newIntent);
			}
		});

		confirm_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String bankNamestr = bankName.getText().toString();
				final String bankAccstr = bankAcc.getText().toString();
				final String creditCardNumstr = creditCardNum.getText()
						.toString();
				final String expDatestr = expDate.getText().toString();
				final String amountstr = amount.getText().toString();
				// final String paymentmethod = (String)
				// spinnerText.getText().toString();
				// Log.w("paymentmethod","paymentmethod = " + paymentmethod);

				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
				nvps.add(new BasicNameValuePair("username", userName));
				nvps.add(new BasicNameValuePair("bankNamestr", bankNamestr));
				nvps.add(new BasicNameValuePair("bankAccstr", bankAccstr));
				nvps.add(new BasicNameValuePair("creditCardNumstr",
						creditCardNumstr));
				nvps.add(new BasicNameValuePair("expDatestr", expDatestr));
				nvps.add(new BasicNameValuePair("amountstr", amountstr));
				nvps.add(new BasicNameValuePair("paymentmethod", spinnerText));
				nvps.add(new BasicNameValuePair("Id",Id));
				String entityContents = null;
				try {
					UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(
							nvps, HTTP.UTF_8);
					httppost.setEntity(p_entity);
					HttpResponse response = client.execute(httppost);

					InputStream is = null;
					HttpEntity entity = response.getEntity();

					byte buffer[] = new byte[1024];
					is = entity.getContent();
					int numBytes = is.read(buffer);
					is.close();
					entityContents = new String(buffer, 0, numBytes);

					// HttpResponse response = client.execute(httppost);
					Log.i("NNNNNNNNNNNNNNNNNNN", "content length= "
							+ entityContents.toString().length());
					Log.i("Net", "Response = " + response.getEntity());
				} catch (Exception e) {
					Log.e("Net", "Error in network call", e);
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

				Result<Transaction> result = (Result<Transaction>) merchant
						.postTransaction(authCaptureTransaction);

				if (result.isApproved()) {
					responseMessage = "Approved!\n" + "Transaction Id: "
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
				final Dialog dialog = new Dialog(RegisterWallet.this);
				dialog.setContentView(R.layout.aboutus);
				dialog.setTitle("Payment Authorization");
				dialog.setCancelable(true);

				TextView abtUs = (TextView) dialog.findViewById(R.id.textView1);
				abtUs.setText(responseMessage);
				Button Goback = (Button) dialog.findViewById(R.id.button1);
				Goback.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent newIntent = new Intent(v.getContext(), main.class);
						startActivity(newIntent);					}
				});
				// now that the dialog is set up, it's time to show it
				dialog.show();

				//////////////////////////////////////////////////////////////


			}
		});
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
