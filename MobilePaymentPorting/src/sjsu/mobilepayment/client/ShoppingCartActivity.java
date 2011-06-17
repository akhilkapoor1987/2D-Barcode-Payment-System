package sjsu.mobilepayment.client;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShoppingCartActivity extends Activity {
 
 private List<Device> mCartList;
 private ProductAdapter mProductAdapter;
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  
  mCartList = ShoppingCartHelper.getCart();
  
  
  Bundle extras = getIntent().getExtras();
  String user = extras.getString("username");
////////////setting title bar////////////////
//  List<Device> cartlist = ShoppingCartHelper.getCart();
  final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

  setContentView(R.layout.shoppingcart);


  if ( customTitleSupported ) {
      getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
      }

  final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
  if ( myTitleText != null ) {
      myTitleText.setText("Touch Pay"+"                         "+user+"," + mCartList.size()+" items ");   }     
  ////////////////////////////////////////////
  
  // Make sure to clear the selections
  for(int i=0; i<mCartList.size(); i++) {
   mCartList.get(i).selected = false;
  }
  
  // Create the list
  final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
  mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);
  listViewCatalog.setAdapter(mProductAdapter);
  
  listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position,
     long id) {
    
    Device selectedProduct = mCartList.get(position);
    if(selectedProduct.selected == true)
     selectedProduct.selected = false;
    else
     selectedProduct.selected = true;
    
    mProductAdapter.notifyDataSetInvalidated();
    
   }
  });

  Button CheckoutButton = (Button) findViewById(R.id.Checkout);
  CheckoutButton.setOnClickListener(new OnClickListener() {
   @Override
   public void onClick(View v) {
	   
	   float total =0;
	    for (int i=0; i< mCartList.size();i++) {
	      Device anOrder = mCartList.get(i);
	      Float price= Float.valueOf( anOrder.getPrice());
	      int qty = Integer.parseInt(anOrder.getSelQty());
	      total += (price * (qty));
	    }
	    total += 0.005;
	    String amount = new Float(total).toString();
	    int n = amount.indexOf('.');
	    amount = amount.substring(0,n+3);
	   
	    Log.i("amountis ", amount);
	    int q= (mCartList.size());
	    String listSize = Integer.toString(q);
	    Log.i("Quantity is  ", listSize);
	   Bundle extras = getIntent().getExtras();
	   String user = extras.getString("username");
	   String memId = extras.getString("memId");
	   Intent FinalCartDetails = new Intent(getBaseContext(), FinalCart0.class);
	    FinalCartDetails.putExtra("FinalCartAmount", amount);
	    FinalCartDetails.putExtra("size",listSize);
	    FinalCartDetails.putExtra("memId",memId);
	    FinalCartDetails.putExtra("username", user);
	    int i =0;
	    for(Device d:mCartList)
	   { 
		    FinalCartDetails.putExtra("FinalCartObjectID"+i,Integer.toString(d.getDeviceID()));

	    	FinalCartDetails.putExtra("FinalCartObject"+d.getDeviceID(), d);
	    	i++;

	    Log.i("Final cart object from shopping cartactivity","FinalCartObject"+d.getDeviceID());
	   }

	    startActivity(FinalCartDetails);
	   }  });
	   
	   Button removeButton = (Button) findViewById(R.id.ButtonRemoveFromCart);
  removeButton.setOnClickListener(new OnClickListener() {
   @Override
   public void onClick(View v) {
    // Loop through and remove all the products that are selected
    // Loop backwards so that the remove works correctly
    for(int i=mCartList.size()-1; i>=0; i--) {
     
     if(mCartList.get(i).selected) {
      mCartList.remove(i);
     }
    }
    mProductAdapter.notifyDataSetChanged();
    if(mCartList.isEmpty())
    	
    { Bundle extras = getIntent().getExtras();
    String memId = extras.getString(("memId"));
    String user = extras.getString(("username"));
    	Intent productList = new Intent(getBaseContext(), ProductList.class);
   productList.putExtra("memberId", memId);
   productList.putExtra("username", user);
    startActivity(productList);}
   }
  });
  
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
       	  
       	  Intent Cart = new Intent(ShoppingCartActivity.this ,mainAfterLogin.class);
       	 Cart.putExtra("memberId", memberId);
       	 Cart.putExtra("username", user);

       	  startActivity(Cart);
       	  break; }
     
         
         ///////////  LOGOUT  ////////
         case R.id.logout:    {
       final List<Device> mCartList = ShoppingCartHelper.getCart();
   mCartList.clear();
       Intent login = new Intent(ShoppingCartActivity.this, main.class );
       	startActivity(login);
       	
       	break; }
    
       ///////////  ABOUT US  ////////
       case R.id.aboutus:   { 
       	
       	final Dialog dialog = new Dialog(ShoppingCartActivity.this);
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
