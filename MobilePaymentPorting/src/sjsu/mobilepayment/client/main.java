package sjsu.mobilepayment.client;

import java.util.List;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity {
	/** Called when the activity is first created. */
	String contents = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// //////////setting title bar////////////////
		List<Device> cartlist = ShoppingCartHelper.getCart();
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.main);

		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.titlebar);
		}

		final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
		if (myTitleText != null) {
			myTitleText.setText("Touch Pay"
					+ "                                " + cartlist.size()
					+ " items in Cart");
		}
		// //////////////////////////////////////////

		ListView lv = (ListView) findViewById(R.id.RootMenu);
		String[] menu = getResources().getStringArray(R.array.rootMenu);

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.rootmenyview,
				menu);
		lv.setAdapter(aa);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent newIntent;
				switch (position) {

				case 0:
					newIntent = new Intent(view.getContext(),
							RegisterUserID.class);
					startActivity(newIntent);
					break;

				case 1:
					newIntent = new Intent(view.getContext(), ProductList.class);
					newIntent.putExtra("memberId", "guest");
					startActivity(newIntent);
					break;
				case 2:
					newIntent = new Intent(view.getContext(), Login.class);
					newIntent.putExtra("NoShow", "0");
					startActivity(newIntent);
					break;
				case 3:
					Intent intent = new Intent(
							"com.google.zxing.client.android.SCAN");
					intent.setPackage("com.google.zxing.client.android");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(intent, 0);

					Intent ScanDetails = new Intent(main.this,
							ProductScan.class);

//					if (contents.split("\\|") != null) {
//						String[] content1 = contents.split("\\|");
//						ScanDetails.putExtra("pdId", content1[0]);
//						ScanDetails.putExtra("pname", content1[1]);
//						ScanDetails.putExtra("pdesc", content1[2]);
					ScanDetails.putExtra("ProductDetails", contents);
					ScanDetails.putExtra("memberId", "guest");
						startActivity(ScanDetails);
//					} else {
//						Toast.makeText(
//								view.getContext(),
//								"Unknown Barcode, Product not found in Inventory",
//								Toast.LENGTH_LONG).show();
//					}
				}

			}

		});

	}

//	@Override
//	public void onBackPressed() {
//
//		return;
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.aboutus: {
			final Dialog dialog = new Dialog(main.this);
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

		}
		return true;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				contents = intent.getStringExtra("SCAN_RESULT");
	            Toast.makeText(main.this," your contents are : " + contents, 10).show();

//				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}

	}

}