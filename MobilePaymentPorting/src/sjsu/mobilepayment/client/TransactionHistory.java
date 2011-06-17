package sjsu.mobilepayment.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import sjsu.mobilepayment.client.CustomAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionHistory extends Activity {
    /** Called when the activity is first created. */

	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/TransactionDetails";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        Log.w("AAAAAAAAAAAAAAAAAAAAAAAAA", "Inside onCreate");
//        Bundle extras = getIntent().getExtras();
//        final String memberId = extras.getString("memberId");
//        final String memberId1 = "123";
        Bundle extras5 = getIntent().getExtras();
        String memberId1 = extras5.getString("memberId");
        String user = extras5.getString("username");
////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

    	setContentView(R.layout.transhistory);


        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }
       
        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                                     "+user+","+ cartlist.size()+" items in Cart");   }     
        ////////////////////////////////////////////
        
        final String memberId2 = memberId1;
        List< BasicNameValuePair > nvps = new ArrayList< BasicNameValuePair >();
        nvps.add(new BasicNameValuePair("memberId", memberId1));
        Log.w("AAAAAAAAAAAAAAAAAAAAAAAAA", "Inside onCreate2");

        DefaultHttpClient client = new DefaultHttpClient();
    	HttpPost httppost  = new HttpPost(url);
       
        String entityContents = null;
    	try{
    		UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
    		httppost.setEntity(p_entity);
    		HttpResponse response = client.execute(httppost);
    		
    		InputStream is = null;
    		HttpEntity entity = response.getEntity();
    		
    		
    		byte buffer[] = new byte[4096];
    		is =  entity.getContent();
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
//        String entityContents = "1|2|3|4%6|7|8|9";
        
		String[] entityContentsSplit = entityContents.split("\\%");
		String[] productDetailsSplit = {};
		String[] productDetailsSplitNew = {};

	
		Log.i("NNNNNNNNNNNNNNNNNNN","content length= " + entityContentsSplit[0]);
    	final Context context = TransactionHistory.this;
    	
        /*ListView ls1 = new ListView(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
       		 context,
       		 android.R.layout.simple_list_item_1,
       		 new String[]{"item1","item2","item3","item4","item5","item6","item7"});
         ls1.setAdapter(adapter);*/
    	
    	ListView ls2 = new ListView(context);
     	/*m_lv1.setClickable(true);
     	m_lv1.setFastScrollEnabled(true);
     	m_lv1.setItemsCanFocus(true);*/
     	
     	// clear previous results in the LV
    	ls2.setAdapter(null);        
   		// populate
    	ArrayList<BuyItem> m_Devices = new ArrayList<BuyItem>();
   		BuyItem device;
   		BuyItem device1;
		Log.i("NNNNNNNNNNNNNNNNNNN","content length= " + "just before for loop");
		int length1 = 0;
//Resources res = new Resources();
	        for (int i=0;i<entityContentsSplit.length;i++) {
	        	productDetailsSplit = entityContentsSplit[i].split("\\|");
	    		Log.i("NNNNNNNNNNNNNNNNNNN","content length= " + " "+productDetailsSplit[2]+" "+productDetailsSplit[1]+" "+productDetailsSplit[0]);
	    		productDetailsSplitNew = productDetailsSplit[2].split("\\#");
	    		length1 = productDetailsSplitNew.length;
Log.i("bbbbbbbbbbbbbbbb", Integer.toString(length1)+" "+productDetailsSplitNew[0]+" "+productDetailsSplitNew[2]+" "+productDetailsSplitNew[1]);
	        device = new BuyItem(productDetailsSplitNew[0],productDetailsSplitNew[2], productDetailsSplitNew[1],  100+i ,productDetailsSplit[0],productDetailsSplit[1]);
	        m_Devices.add(device);
	        }
        CustomAdapterTransact lvAdapter =  new CustomAdapterTransact(context, m_Devices);
        ls2.setAdapter(lvAdapter);
        ls2.setOnItemClickListener(new OnItemClickListener() 
        {
        	public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
        	{
        		
        		Log.i("NNNNNNNNNNNNNNNNNNN","inside on itemclick= " + "just before for loop"+arg2);

                BuyItem device = (BuyItem) arg0.getAdapter().getItem(arg2);
//String usrname2 = "guest";
	Log.i("HelloBird","inside on itemclick= " + "just before for loop"+device.getDeviceID());


        		Toast.makeText(getBaseContext(), "Record Entry Selected"+arg2, Toast.LENGTH_LONG).show();
        		 Bundle extras = getIntent().getExtras();
			        Intent newIntent = new Intent(arg0.getContext(),TransactionDetailed.class);
	        		Log.i("NNNNNNNNNNNNNNNNNNN","orderdetails= "+device.getOrderDetails() );

			        newIntent.putExtra("purchaseDate", device.getPurchaseDate());
				    newIntent.putExtra("orderTotal", device.getOrderTotal());
					newIntent.putExtra("transactionID", device.getTransactionId());		        
				    newIntent.putExtra("orderDetails", device.getOrderDetails());
				    newIntent.putExtra("memberId", memberId2);
				    

			        
				
			
				
			    startActivity(newIntent);
        		
    		}
    	});
        
//        Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
//        viewShoppingCart.setOnClickListener(new OnClickListener() {
//         
//         @Override
//         public void onClick(View v) {
//          Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
//          startActivity(viewShoppingCartIntent);
//         }
//        });
         
         setContentView(ls2);
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
    	  String user = extras.getString("username");
    	  switch (item.getItemId()) {
        
      
        /////////// CART  ////////
            case R.id.home:    { 
          	  
          	  Intent Cart = new Intent(TransactionHistory.this ,mainAfterLogin.class);
          	 Cart.putExtra("memberId", memberId);
          	 Cart.putExtra("username", user);
          	  startActivity(Cart);
          	  break; }
        
            
            ///////////  LOGOUT  ////////
            case R.id.logout:    {
          final List<Device> mCartList = ShoppingCartHelper.getCart();
      mCartList.clear();
          Intent login = new Intent(TransactionHistory.this, main.class );
          	startActivity(login);
          	
          	break; }
       
          ///////////  ABOUT US  ////////
          case R.id.aboutus:   { 
          	
          	final Dialog dialog = new Dialog(TransactionHistory.this);
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