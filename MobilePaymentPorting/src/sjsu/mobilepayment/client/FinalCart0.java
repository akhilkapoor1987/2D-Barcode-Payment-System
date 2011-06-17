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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class FinalCart0 extends Activity {
	/** Called when the activity is first created. */
	OrderAdapter customAdapter;
	ArrayList<Order> orderList = new ArrayList<Order>();
	private List<Device> mCartList;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		 ////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        Bundle extras1= getIntent().getExtras();
        final String user = extras1.getString("username");
		setContentView(R.layout.finalcart0);


        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }

        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                         "+user+","+ cartlist.size()+" items");   }     
        ////////////////////////////////////////////
		Bundle extras = getIntent().getExtras();
		final String memId = extras.getString("memId");

		


		final TextView tv0 = (TextView) findViewById(R.id.total);

		final Button Checkout = (Button) findViewById(R.id.ProceedToCheckout);
		final Button Catalog = (Button) findViewById(R.id.ProductCatalog);
		Catalog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent newIntent = new Intent(v.getContext(), ProductList.class);
newIntent.putExtra("username", user);
				newIntent.putExtra("memberId", memId);
				startActivity(newIntent);
			}
		});

		Checkout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Bundle extras = getIntent().getExtras();
				String amount = extras.getString("FinalCartAmount");
				String memId = extras.getString("memId");

				String size = extras.getString("size");
				int s = Integer.parseInt(size);
				mCartList = ShoppingCartHelper.getCart();

				Intent FinalCartDetails = new Intent(getBaseContext(),
						FinalCart1.class);
				FinalCartDetails.putExtra("FinalCartAmount", amount);
				FinalCartDetails.putExtra("size", size);
				FinalCartDetails.putExtra("memId", memId);
				FinalCartDetails.putExtra("username", user);
				int i = 0;
				for (Device d : mCartList) {
					FinalCartDetails.putExtra("FinalCartObjectID" + i,
							Integer.toString(d.getDeviceID()));

					FinalCartDetails.putExtra(
							"FinalCartObject" + d.getDeviceID(), d);
					i++;

					Log.i("Final cart object from shopping cartactivity",
							"FinalCartObject" + d.getDeviceID());
				}

				startActivity(FinalCartDetails);

			}
		});

//		Bundle extras = getIntent().getExtras();
		String finalAmount = (extras.getString("FinalCartAmount"));
		Log.i("amount", finalAmount);
		// Float q= Float.valueOf(finalAmount);
		tv0.setText(finalAmount);
		String size = extras.getString("size");

		Log.i("NNNNNNNNNNNNNNNNNNN", "content length= " + size);
		int s = Integer.parseInt(size);
		String[] con = new String[s];
		String pid = "";
		orderList.add(new Order("PRODUCT", "  PRICE", "  QUANTITY"));
		for (int j = 0; j <= s - 1; j++) {

			// Intent i = getIntent();
			// Bundle extras = i.getExtras();
			String ID = extras.getString("FinalCartObjectID" + j);

			Device myParcelableObject = (Device) extras
					.getParcelable("FinalCartObject" + ID);
			con[j] = myParcelableObject.getDeviceName() + " "
					+ myParcelableObject.getPrice() + " "
					+ myParcelableObject.getSelQty();
			Log.i("Product Name" + " " + "Price" + " " + "Quantity", con[j]);
			orderList.add(new Order(myParcelableObject.getDeviceName(),
					myParcelableObject.getPrice(), myParcelableObject
							.getSelQty()));

			pid += myParcelableObject.getpdId() + "/";

		}

		customAdapter = new OrderAdapter(this, R.layout.customlayout, orderList);

		ListView l = (ListView) findViewById(R.id.ListView01);
		l.setAdapter(customAdapter);
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
	       	  
	       	  Intent Cart = new Intent(FinalCart0.this ,mainAfterLogin.class);
	       	 Cart.putExtra("memberId", memberId);
	       	 Cart.putExtra("username", user);

	       	 startActivity(Cart);
	       	  
	       	  break; }
	     
	         
	         ///////////  LOGOUT  ////////
	         case R.id.logout:    {
	       final List<Device> mCartList = ShoppingCartHelper.getCart();
	   mCartList.clear();
	       Intent login = new Intent(FinalCart0.this, main.class );
	       	startActivity(login);
	       	
	       	break; }
	    
	       ///////////  ABOUT US  ////////
	       case R.id.aboutus:   { 
	       	
	       	final Dialog dialog = new Dialog(FinalCart0.this);
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
	

	private class OrderAdapter extends ArrayAdapter<Order> {

		private ArrayList<Order> items;

		public OrderAdapter(Context context, int resource,
				ArrayList<Order> items) {
			super(context, resource, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				v = vi.inflate(R.layout.customlayout, null);
			}
			Order o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.TextView01);
				if (tt != null) {
					tt.setText(o.getTitleName() + " " + o.getOrderPrice()
							+ "    " + o.getOrderQuantity());
				}
			}
			return v;
		}
	}

}
