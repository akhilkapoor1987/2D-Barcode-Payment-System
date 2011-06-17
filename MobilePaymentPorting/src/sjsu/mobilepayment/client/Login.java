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
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {

//	private static final String TAG = "Login";
	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/LoginServlet";
	String loginmessage = null;
	Thread t;
	ProgressDialog dialog;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle(R.string.loginWinTitle);
        
//        Bundle extras = getIntent().getExtras();
//        final String selectedID = extras.getString("selectedItem");
//        Log.w("Login Activity ID is: ", selectedID);
        
        final EditText userName = (EditText) findViewById(R.id.username);
		final EditText password = (EditText) findViewById(R.id.password);
        
        final Button login_button = (Button) findViewById(R.id.loginbutton);
       

        
        login_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(0);
				
				/*
				t = new Thread(){
					public void run(){tryLogin();}
				};
				t.start();
				*/
				
				String user = userName.getText().toString();
			    String pasw = password.getText().toString();
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httppost  = new HttpPost(url);
				List< BasicNameValuePair > nvps = new ArrayList< BasicNameValuePair >();
	            nvps.add(new BasicNameValuePair("username", user));
	            nvps.add(new BasicNameValuePair("password", pasw));
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
					Log.i("NNNNNNNNNNNNNNNNNNN","content length= " + entityContents.toString());
					Log.i("NNNNNNNNNNNNNNNNNNN","content length= " + entityContents.substring(0).length());
					Log.i("Net","Response = " + response.getEntity());
				}catch(Exception e){
					Log.e("Net","Error in network call",e);
				}
				Log.i("YYYYYYYYYYYYYYYYYY","entityContents = " + entityContents);
				String[] entityContentsSplit = entityContents.split("\\%");

				if (entityContentsSplit[0].trim().equals("Y")){
					
					Bundle extras = getIntent().getExtras();
					if(extras.getString("NoShow").equalsIgnoreCase("0"))
							{
						Log.i("new intent","entityContents = " + entityContents);
						
						Intent newIntent = new Intent(v.getContext(),mainAfterLogin.class);
						newIntent.putExtra("loginCheck", "truth");

						newIntent.putExtra("username", user);
						newIntent.putExtra("memberId",entityContentsSplit[1] );
						startActivity(newIntent);
							}
					else{
					Log.i("new intent","entityContents = " + entityContents);
					
					Intent newIntent = new Intent(v.getContext(),ProductList.class);
					newIntent.putExtra("loginCheck", "truth");

					newIntent.putExtra("username", user);
					newIntent.putExtra("memberId",entityContentsSplit[1] );
					startActivity(newIntent);}
				}else{
					Log.i("old intent","entityContents = " + entityContents);
					AlertDialog alert = new AlertDialog.Builder(Login.this).create();
					alert.setTitle("Login Error");
					alert.setMessage("User name and password do not match. Please try again.");
					alert.setButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							return;
							
						}
					});
					alert.show();
				}
				
				
			}
		});
        
        final Button reset_button = (Button) findViewById(R.id.resetbutton);
        reset_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userName.setText("");
				password.setText("");
			}
		});
        

    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.shoppingcart2, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		  Bundle extras = getIntent().getExtras();
		 	 String memberId=  extras.getString("memberId");
		  
		  switch (item.getItemId()) {
	    
	  
	    /////////// CART  ////////
	        case R.id.home:    { 
	      	  
	      	  Intent Cart = new Intent(Login.this ,main.class);
	      	  startActivity(Cart);
	      	  break; }
	    
	      	   
	      ///////////  ABOUT US  ////////
	      case R.id.aboutus:   { 
	      	
	      	final Dialog dialog = new Dialog(Login.this);
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

    
//	@Override
//    protected Dialog onCreateDialog(int id) {
//          switch (id) {
//                case 0: {
//                      dialog = new ProgressDialog(this);
//                      dialog.setMessage("Please wait while connecting...");
//                      dialog.setIndeterminate(true);
//                      dialog.setCancelable(true);
//                      return dialog;
//                }
//          }
//          return null;
//    }
//	
//	private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//              String loginmsg=(String)msg.obj;
//              if(loginmsg.equals("SUCCESS")) {
//                    removeDialog(0);
//                    Intent intent=new Intent(getApplicationContext(),Transaction.class);
//                    startActivity(intent);
//                    finish();
//              }
//        }
//  }; 
  /*
  public void tryLogin() {
      Log.v(TAG, "Trying to Login");
      EditText etxt_user = (EditText) findViewById(R.id.username);
      EditText etxt_pass = (EditText) findViewById(R.id.password);
      String username = etxt_user.getText().toString();
      String password = etxt_pass.getText().toString();
      
      
      DefaultHttpClient client = new DefaultHttpClient();
      HttpPost httppost = new HttpPost(url);
      List nvps = new ArrayList();
      nvps.add(new BasicNameValuePair("username", username));
      nvps.add(new BasicNameValuePair("password", password));
      try {
            UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
HTTP.UTF_8);
            httppost.setEntity(p_entity);
            HttpResponse response = client.execute(httppost);
            Log.v(TAG, response.getStatusLine().toString());
            HttpEntity responseEntity = response.getEntity();
            Log.v(TAG, "Set response to responseEntity");
            Log.v(TAG, "responseEntity is: " + responseEntity);
            Log.v(TAG, "retrieveInputStream is: " + retrieveInputStream(responseEntity));

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            LoginHandler myLoginHandler = new LoginHandler();
            xr.setContentHandler(myLoginHandler);
            xr.parse(retrieveInputStream(responseEntity));
            
            //ParsedLoginDataSet parsedLoginDataSet = myLoginHandler.getParsedLoginData();
            if (parsedLoginDataSet.getExtractedString().equals("SUCCESS")) {
                  // Store the username and password in SharedPreferences after the successful login
                  //SharedPreferences.Editor editor=mPreferences.edit();
                  //editor.putString("UserName", username);
                  //editor.putString("PassWord", password);
                  //editor.commit();
                  Message myMessage=new Message();
                  myMessage.obj="SUCCESS";
                  handler.sendMessage(myMessage);
            } else if(parsedLoginDataSet.getExtractedString().equals("ERROR")) {
                  //Intent intent = new Intent(getApplicationContext(), LoginError.class);
                  //intent.putExtra("LoginMessage", parsedLoginDataSet.getMessage());
                  //startActivity(intent);
                  removeDialog(0);
            }
      } catch (Exception e)
      {
            //Intent intent = new Intent(getApplicationContext(), LoginError.class);
            //intent.putExtra("LoginMessage", "Unable to login");
            //startActivity(intent);
            removeDialog(0);
      }
}
*/
private InputSource retrieveInputStream(HttpEntity httpEntity) {
      InputSource insrc = null;
      try {
            insrc = new InputSource(httpEntity.getContent());
      } catch (Exception e) {
      }
      return insrc;
} 

}
