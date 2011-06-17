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
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyWallet extends Activity {

	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/Statements";
	private String url2 = "http://10.0.2.2:8080/BarcodePaymentServer/AccountDetails";


	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       // setTitle(R.string.registerWalletWinTitle2);
       
        
        
        ////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

       // setContentView(R.layout.m);
        setContentView(R.layout.mywallet);

        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }
        Bundle extras = getIntent().getExtras();
        final String user=  extras.getString("username");
        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                         "+user+","+ cartlist.size()+" items");   }     
        ////////////////////////////////////////////
        
        final String memberId = extras.getString("memberId");
        
	      
        Button mywallet = (Button)findViewById(R.id.button3);
        Button updateAccount = (Button) findViewById(R.id.button2);  
        Button TransHistory = (Button) findViewById(R.id.button1);  
        
        
        
        
        updateAccount.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent newIntent = new Intent(MyWallet.this,UpdateRegisterDetails.class);

				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httppost  = new HttpPost(url2);
				List< BasicNameValuePair > nvps = new ArrayList< BasicNameValuePair >();
				nvps.add(new BasicNameValuePair("memberId", memberId));
			
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
				Log.i("YYYYYYYYYYYYYYYYYY","entityContents = " + entityContents);
				String[] entityContentsSplit = entityContents.split("\\*");
				String content1 = entityContentsSplit[0];
				String content2 = entityContentsSplit[1];
				Log.i("YYYYYYYYYYYYYYYYYY","entityContents = " + content2.toString());

				String[] content3 = content1.split("\\%");
				Log.i("conentsssssss", content3.toString());
				newIntent.putExtra("firstname",content3[0] );
				newIntent.putExtra("lastname",content3[1] );
				newIntent.putExtra("address", content3[2]);
				newIntent.putExtra("city",content3[3]);
				newIntent.putExtra("state",content3[4] );
				newIntent.putExtra("password",content3[5] );
				newIntent.putExtra("email",content3[6] );
				newIntent.putExtra("memberId",memberId );
			
				newIntent.putExtra("username", user);
			newIntent.putExtra("walletContents", content2);
			startActivity(newIntent);
						
				
			}
        	
        	
        	
        	
        });
        	  
        TransHistory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent newIntent1 = new Intent(MyWallet.this,TransactionHistory.class);
				newIntent1.putExtra("memberId",memberId );
				newIntent1.putExtra("username",user);
				startActivity(newIntent1);
				
				
			}
        	
        	
        	
        	
        });
        mywallet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	
				
		        DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httppost  = new HttpPost(url);
				List< BasicNameValuePair > nvps = new ArrayList< BasicNameValuePair >();
				nvps.add(new BasicNameValuePair("memberId", memberId));
	           
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
				finish();
						
				Intent wallet = new Intent(MyWallet.this,miniStat.class );
				wallet.putExtra("Contents",entityContents);
				wallet.putExtra("memberId", memberId);
				wallet.putExtra("username", user);

				startActivity(wallet);
				
}
		});
	}
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.mainafterlogin1, menu);
	      return true;
	  }

	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
		  Bundle extras = getIntent().getExtras();
		 	 String memberId=  extras.getString("memberId");
		 	String user=  extras.getString("username");
		  switch (item.getItemId()) {
	      
	    
	      /////////// CART  ////////
	          case R.id.cart:    { 
	        	  final List<Device> mCartList = ShoppingCartHelper.getCart();
	        	  if(mCartList.size()==0)  
	        	  {
	        		  Toast.makeText(MyWallet.this,"There are no items in your Cart. Please visit Product Catalog to add Items ",Toast.LENGTH_LONG).show();
	        		  
	        	  }
	        	  else 
	        	  {  Intent Cart = new Intent(MyWallet.this,ShoppingCartActivity.class );
	         	 Cart.putExtra("memberId", memberId);
	         	Cart.putExtra("username",user );
	         	 startActivity(Cart);}
	        	  break; }
	      
	          
	          ///////////  LOGOUT  ////////
	          case R.id.logout:    {
	        	  final List<Device> mCartList = ShoppingCartHelper.getCart();

	    mCartList.clear();
	        Intent login = new Intent(MyWallet.this, main.class );
	        	startActivity(login);
	        	
	        	break; }
	     
	        ///////////  ABOUT US  ////////
	        case R.id.aboutus:   { 
	        	
	        	final Dialog dialog = new Dialog(MyWallet.this);
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
	}

