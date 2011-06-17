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

import sjsu.mobilepayment.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterDetails extends Activity {

	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/RegisterUserDetails";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerdetail);
        setTitle(R.string.registerDetailsWinTitle);
        
        Bundle extras = getIntent().getExtras();
        final String userName = extras.getString("username");
       
        Button cancel_Button = (Button)findViewById(R.id.detailsCancel);
        Button next_Button = (Button)findViewById(R.id.detailsNext);
        final EditText firstname1 = (EditText)findViewById(R.id.firstname);
        final EditText lastname1 = (EditText)findViewById(R.id.lastname);
        final EditText address1 = (EditText)findViewById(R.id.address);
        final EditText city1 = (EditText)findViewById(R.id.city);
        final EditText state1 = (EditText)findViewById(R.id.state);
        final EditText email1 = (EditText)findViewById(R.id.email);
        final EditText password1 = (EditText)findViewById(R.id.password);

        
        cancel_Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(v.getContext(),RegisterUserID.class);
				startActivity(newIntent);
			}
		});
        
        next_Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final String firstname = firstname1.getText().toString();
				final String lastname = lastname1.getText().toString();
				final String address = address1.getText().toString();
				final String city = city1.getText().toString();
				final String state = state1.getText().toString();
			//	final String zip = zip1.getText().toString();
				final String email = email1.getText().toString();
				final String password = password1.getText().toString();

				
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httppost  = new HttpPost(url);
				List< BasicNameValuePair > nvps = new ArrayList< BasicNameValuePair >();
				nvps.add(new BasicNameValuePair("login", userName));
		           
	            nvps.add(new BasicNameValuePair("FirstName", firstname));
	            nvps.add(new BasicNameValuePair("LastName", lastname));

	            nvps.add(new BasicNameValuePair("address", address));
	            nvps.add(new BasicNameValuePair("city", city));
	            nvps.add(new BasicNameValuePair("state", state));
	        //    nvps.add(new BasicNameValuePair("zip", zip));
	            nvps.add(new BasicNameValuePair("email", email));
	            nvps.add(new BasicNameValuePair("password", password));

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
			String id =	(entityContents);
				Intent newIntent = new Intent(v.getContext(),RegisterWallet.class);
				newIntent.putExtra("Id", id);
				newIntent.putExtra("username", userName);
				startActivity(newIntent);
			}
		});
        
	}
}
