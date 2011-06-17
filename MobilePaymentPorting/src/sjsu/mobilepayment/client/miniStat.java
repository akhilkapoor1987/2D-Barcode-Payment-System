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
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class miniStat extends Activity {

	//private String url = "http://10.0.2.2:8080/BarcodePaymentServer/RechargeWallet1";


	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.);
       // setTitle(R.string.registerWalletWinTitle2);
        Bundle extras = getIntent().getExtras();
        final String contents = extras.getString("Contents");
        final String memberId = extras.getString("memberId");
        final String user = extras.getString("username");
		
///////////////////////////////
////////////setting title bar////////////////
List<Device> cartlist = ShoppingCartHelper.getCart();
final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

setContentView(R.layout.ministat);


if ( customTitleSupported ) {
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
    }

final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
if ( myTitleText != null ) {
    myTitleText.setText("Touch Pay"+"                         "+user+","+ cartlist.size()+" items");   }     
////////////////////////////////////////////
//////////////////////////////////
        Button mywallet = (Button)findViewById(R.id.button3);
    	final Context context = miniStat.this;
    	ListView ls2 = new ListView(context);
    	ls2.setAdapter(null);        


        WalletItem device;
          ArrayList<WalletItem> m_Devices = new ArrayList<WalletItem>();
        String [] content1= contents.split("\\%");
        Log.i("Length of content1", contents);
        
      for(int i=0;i<content1.length-1;i++)
      {String [] content2 = content1[i].split("\\|");
      Log.i("Contents2",content2[0]+" "+content2[1]+" "+content2[2]);
    	  device = new WalletItem(content2[0],content2[1],content2[2],100+i);
	        m_Devices.add(device);

      }
      WalletAdapter lvAdapter =  new WalletAdapter(context, m_Devices);
      ls2.setAdapter(lvAdapter);
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
          	  
          	  Intent Cart = new Intent(miniStat.this ,mainAfterLogin.class);
          	 Cart.putExtra("memberId", memberId);
          	Cart.putExtra("username", user);
         	  
          	 startActivity(Cart);
          	  break; }
        
            
            ///////////  LOGOUT  ////////
            case R.id.logout:    {
          final List<Device> mCartList = ShoppingCartHelper.getCart();
      mCartList.clear();
          Intent login = new Intent(miniStat.this, main.class );
          	startActivity(login);
          	
          	break; }
       
          ///////////  ABOUT US  ////////
          case R.id.aboutus:   { 
          	
          	final Dialog dialog = new Dialog(miniStat.this);
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
	     
        
   
        
        
        
        
       		
	
		
	
	

