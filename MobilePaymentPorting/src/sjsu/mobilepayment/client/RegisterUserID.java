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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUserID extends Activity {

	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/RegisterUser";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerid);
        setTitle(R.string.registerWinTitle);
        
        Button cancel_Button = (Button)findViewById(R.id.Cancel);
        Button next_Button = (Button)findViewById(R.id.Next);
        final EditText username = (EditText)findViewById(R.id.UserID);
        

        cancel_Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(v.getContext(),main.class);
				startActivity(newIntent);
			}
		});
        
        next_Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			
			public void onClick(View v) {
				final String userNameStr = username.getText().toString();
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httppost  = new HttpPost(url);
				List< BasicNameValuePair > nvps = new ArrayList< BasicNameValuePair >();
	            nvps.add(new BasicNameValuePair("username", userNameStr));
	            Log.i("MMMMMMMMMMMMMMMMMMMMMMMMMM ",userNameStr);

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
				
				if (entityContents.trim().equals("Y")){
					Log.i("User exist","entityContents = " + entityContents);
					AlertDialog alert = new AlertDialog.Builder(RegisterUserID.this).create();
					alert.setTitle("Register Error");
					alert.setMessage("User name already exists.");
					alert.setButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							return;
							
						}
					});
					alert.show();
				}else{
					Intent newIntent = new Intent(v.getContext(),RegisterDetails.class);
					newIntent.putExtra("username", userNameStr);
					startActivity(newIntent);
					Log.i("Next Stept","entityContents = " + entityContents);
					
				}
				//Intent newIntent = new Intent(v.getContext(),RegisterDetails.class);

				
			}
		});
	}
}
