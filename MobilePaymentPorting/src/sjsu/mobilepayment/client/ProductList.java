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

public class ProductList extends Activity {
	/** Called when the activity is first created. */
	//private List<Device> mCartList;

	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/ProductCatalog";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// //////////setting title bar////////////////
//		List<Device> cartlist = ShoppingCartHelper.getCart();
		
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.productlist);
	
		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.titlebar);
		}

		final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
		if (myTitleText != null) {
			myTitleText.setText("Touch Pay");
//					+ "                                     " + cartlist.size()
//					+ " items in Cart");
		}
		// //////////////////////////////////////////

		Log.w("AAAAAAAAAAAAAAAAAAAAAAAAA", "Inside onCreate");
		Bundle extras = getIntent().getExtras();
		final String memberId1 = extras.getString("memberId");
		// final String memberId1 = "123";
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("memberId", memberId1));
		Log.w("AAAAAAAAAAAAAAAAAAAAAAAAA", "Inside onCreate2");

		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		String entityContents = null;
		try {
			UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
					HTTP.UTF_8);
			httppost.setEntity(p_entity);
			HttpResponse response = client.execute(httppost);

			InputStream is = null;
			HttpEntity entity = response.getEntity();

			byte buffer[] = new byte[4096];
			is = entity.getContent();
			int numBytes = is.read(buffer);
			is.close();
			entityContents = new String(buffer, 0, numBytes);

			// HttpResponse response = client.execute(httppost);
			Log.i("NNNNNNNNNNNNNNNNNNN",
					"content length= " + entityContents.toString());
			Log.i("NNNNNNNNNNNNNNNNNNN", "content length= "
					+ entityContents.substring(0).length());
			Log.i("Net", "Response = " + response.getEntity());
		} catch (Exception e) {
			Log.e("Net", "Error in network call", e);
		}
		// String entityContents = "1|2|3|4%6|7|8|9";

		String[] entityContentsSplit = entityContents.split("\\%");
		String[] productDetailsSplit = {};

		Log.i("NNNNNNNNNNNNNNNNNNN", "content length= "
				+ entityContentsSplit[0]);
		final Context context = ProductList.this;

		/*
		 * ListView ls1 = new ListView(context); ArrayAdapter<String> adapter =
		 * new ArrayAdapter<String>( context,
		 * android.R.layout.simple_list_item_1, new
		 * String[]{"item1","item2","item3","item4","item5","item6","item7"});
		 * ls1.setAdapter(adapter);
		 */

		ListView ls2 = new ListView(context);
		/*
		 * m_lv1.setClickable(true); m_lv1.setFastScrollEnabled(true);
		 * m_lv1.setItemsCanFocus(true);
		 */

		// clear previous results in the LV
		ls2.setAdapter(null);
		// populate
		ArrayList<Device> m_Devices = new ArrayList<Device>();
		Device device;
		Log.i("NNNNNNNNNNNNNNNNNNN", "content length= "
				+ "just before for loop");
		// Resources res = new Resources();
		for (int i = 0; i < entityContentsSplit.length; i++) {
			productDetailsSplit = entityContentsSplit[i].split("\\|");
			Log.i("NNNNNNNNNNNNNNNNNNN", "content length= "
					+ productDetailsSplit[3] + productDetailsSplit[2]
					+ productDetailsSplit[1] + productDetailsSplit[0]);

			device = new Device(productDetailsSplit[1], productDetailsSplit[2]
					+ "\n$" + productDetailsSplit[3], 1, 1, (100 + i),
					productDetailsSplit[0], productDetailsSplit[4],
					productDetailsSplit[3], productDetailsSplit[5]/* , res.getDrawable(R.drawable.icon) */);
			m_Devices.add(device);
		}
		CustomAdapter lvAdapter = new CustomAdapter(context, m_Devices);
		ls2.setAdapter(lvAdapter);
		ls2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Device device = (Device) arg0.getAdapter().getItem(arg2);
				String memberID = "guest";

				Toast.makeText(getBaseContext(), "Item Succesfully Selected",
						Toast.LENGTH_LONG).show();
				Bundle extras = getIntent().getExtras();
				Intent newIntent = new Intent(arg0.getContext(),
						ProductDetails.class);
				final	String user = extras.getString("username");

				if (extras.get(memberId1) == "guest") {
					newIntent.putExtra("memberId", memberID);
				} else {
					String var = extras.getString("memberId");
					Log.i("NNNNNNNNNNNNNNNNNNN",
							"inside on itemclick= " + "just before for loop"
									+ device.getDeviceType() + " "
									+ device.getDeviceStatus() + " "
									+ device.getDeviceID());

					newIntent.putExtra("memberId", var);
				}
				newIntent.putExtra("m_szDeviceName", device.getDeviceName());
				newIntent.putExtra("m_szDeviceAddress",
						device.getDeviceAddress());
				newIntent.putExtra("m_nDeviceType", device.getDeviceType());
				newIntent.putExtra("m_nDeviceStatus", device.getDeviceStatus());
				newIntent.putExtra("m_nDeviceID", device.getDeviceID());
				newIntent.putExtra("pdId", device.getpdId());
				newIntent.putExtra("qty", device.getqty());
				newIntent.putExtra("price", device.getPrice());
				newIntent.putExtra("itemImage", device.getItemImage());
				newIntent.putExtra("username",user);
				startActivity(newIntent);

			}
		});

		setContentView(ls2);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.productlist1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Bundle extras = getIntent().getExtras();
		String memberId = extras.getString("memberId");

		switch (item.getItemId()) {

		// ///////// CART ////////
		case R.id.cart: {

			if (memberId.equals("guest")) {
				Intent guestUser = new Intent(ProductList.this, main.class);
				Toast.makeText(ProductList.this,
						"Please Login before visiting Cart", Toast.LENGTH_LONG)
						.show();

				startActivity(guestUser);
			} else {
				final List<Device> mCartList = ShoppingCartHelper.getCart();
				if (mCartList.size() == 0) {
					Toast.makeText(
							ProductList.this,
							"There are no items in your Cart. Please visit Product Catalog to add Items ",
							Toast.LENGTH_LONG).show();

				} else {
					Intent Cart = new Intent(ProductList.this,
							ShoppingCartActivity.class);
					Cart.putExtra("memId", memberId);
					String user=  extras.getString("username");
					Cart.putExtra("username", user);
					
					Log.i("the Memberid passed to shopping cart through product list page",
							memberId);
					startActivity(Cart);
					//

				}
			}
			break;
		}

			// ///////// LOGOUT ////////
		case R.id.logout: {
			if (memberId.equals("guest")) {
				Toast.makeText(ProductList.this,
						"You have already been logged out", Toast.LENGTH_LONG)
						.show();
			} else {
				final List<Device> mCartList = ShoppingCartHelper.getCart();
				mCartList.clear();
				Intent login = new Intent(ProductList.this, main.class);
				startActivity(login);
			}
			break;
		}

			// ///////// ABOUT US ////////
		case R.id.aboutus: {

			final Dialog dialog = new Dialog(ProductList.this);
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

			break;
		}
		case R.id.home: {
			if (memberId.equals("guest")) {
				Intent guestUser = new Intent(ProductList.this, main.class);
				startActivity(guestUser);
			} else {
				Intent Cart = new Intent(ProductList.this, mainAfterLogin.class);
				// String var = extras.getString("memberId");
				Log.i("the Memberid passed to shopping cart through product list page",
						memberId);
				Cart.putExtra("memberId", memberId);
				String user=  extras.getString("username");
				Cart.putExtra("username", user);
				startActivity(Cart);
			}
		}
		}
		return true;
	}

}