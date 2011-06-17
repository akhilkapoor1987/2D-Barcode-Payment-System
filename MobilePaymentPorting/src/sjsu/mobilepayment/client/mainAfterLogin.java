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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class mainAfterLogin extends Activity {
    /** Called when the activity is first created. */
	String contents = "";
	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/InstantPay";
	
	 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
   	 final String memberId=  extras.getString("memberId");
		final	String user = extras.getString("username");

   
	if(user==null)
	{
	   	 Toast.makeText(mainAfterLogin.this,"Welcome Back !!! ",Toast.LENGTH_LONG).show();
	}
	else
	{		Toast.makeText(mainAfterLogin.this,"Welcome "+user ,Toast.LENGTH_LONG).show();
	}

        
        ////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.main);


        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }

        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                         "+user+","+ cartlist.size()+" items");   }     
        ////////////////////////////////////////////
        
        
        
        ListView lv = (ListView)findViewById(R.id.RootMenu);
        String[] menu = getResources().getStringArray(R.array.menuAfterLogin);
        
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.rootmenyview, menu);
        lv.setAdapter(aa);
        
		
        
        
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent newIntent;
				switch(position){
				
				case 0:
					newIntent = new Intent(view.getContext(),ProductList.class);
					newIntent.putExtra("memberId",memberId );
					newIntent.putExtra("username",user );
					startActivity(newIntent);
					break;
					
				case 1:
					newIntent = new Intent(view.getContext(),MyWallet.class);
					newIntent.putExtra("memberId",memberId );
					newIntent.putExtra("username",user );
					startActivity(newIntent);
					break;
				
			case 2:
					Intent intent = new Intent(
					"com.google.zxing.client.android.SCAN");
			intent.setPackage("com.google.zxing.client.android");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
			
			Intent ScanDetails= new Intent(view.getContext(), ProductScan.class);
			String [] content19 = contents.split("\\|") ;
			ScanDetails.putExtra("pdId",content19[0]);
			ScanDetails.putExtra("pname",content19[1]);
			ScanDetails.putExtra("pdesc", content19[2]);
			ScanDetails.putExtra("memberId",memberId);
			ScanDetails.putExtra("username",user );
			startActivity(ScanDetails);					
			break;
			case 3:
				
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
										Log.i("Yes","entityContents = " + entityContents);
									Intent	newIntent1 = new Intent(view.getContext(),PayNow.class);

										newIntent1.putExtra("contents",entityContents );
										
										newIntent1.putExtra("memberId",memberId );
										newIntent1.putExtra("username",user);
										
									
									startActivity(newIntent1);
									break;
					
				}
				
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
        		  Toast.makeText(mainAfterLogin.this,"There are no items in your Cart. Please visit Product Catalog to add Items ",Toast.LENGTH_LONG).show();
        		  
        	  }
        	  else 
        	  {  Intent Cart = new Intent(mainAfterLogin.this,ShoppingCartActivity.class );
         	 Cart.putExtra("memberId", memberId);
         	Cart.putExtra("username",user );
         	 startActivity(Cart);}
        	  break; }
      
          
          ///////////  LOGOUT  ////////
          case R.id.logout:    {
        	  final List<Device> mCartList = ShoppingCartHelper.getCart();

    mCartList.clear();
        Intent login = new Intent(mainAfterLogin.this, main.class );
        	startActivity(login);
        	
        	break; }
     
        ///////////  ABOUT US  ////////
        case R.id.aboutus:   { 
        	
        	final Dialog dialog = new Dialog(mainAfterLogin.this);
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				contents = intent.getStringExtra("SCAN_RESULT");
//				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}

	}
}
