package sjsu.mobilepayment.client;

import java.nio.ByteBuffer;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class ProductDetails extends Activity {

//	private static final String TAG = "Shopping";
	public  String spinnerText  = null;

//	Thread t;
	//private String url = "http://10.0.2.2:8080/mobilepayment/OrderConfirmServlet";

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;
	private Bitmap encodeString(String input) throws WriterException{
		BarcodeFormat QR_CODE=BarcodeFormat.QR_CODE;
		Hashtable<EncodeHintType,Object> hints = null;
		String encoding = input;
	       Log.i("Encoding", encoding);

	   	if (encoding != null) {
		      hints = new Hashtable<EncodeHintType,Object>(2);
		      hints.put(EncodeHintType.CHARACTER_SET, encoding);
		    }
		    QRCodeWriter writer = new QRCodeWriter();    
		    BitMatrix result = writer.encode(input, QR_CODE, 300, 300, hints);
		       Log.i("Result", result.toString());

		    int width = result.getWidth();
		    int height = result.getHeight();
		    int[] pixels = new int[width * height];
		       Log.i("Result", pixels.toString());

		    // All are 0, or black, by default
		    for (int y = 0; y < height; y++) {
		      int offset = y * width;
		      for (int x = 0; x < width; x++) {
		        pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
		      }
		    }

		    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//		  ByteBuffer abc = ByteBuffer.allocate(4096);
//	bitmap.copyPixelsToBuffer(abc);
	
		    return bitmap;
		 
  	}  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		 ////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.productdetails);


        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }

        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                                     "+ cartlist.size()+" items in Cart");   }     
        ////////////////////////////////////////////
		
		
//		setTitle(R.string.shoppingDetailsWinTitle);

		Bundle extras = getIntent().getExtras();
		final String m_szDeviceName = extras.getString("m_szDeviceName");
		final String m_szDeviceAddress = extras.getString("m_szDeviceAddress");
		final int m_nDeviceType = extras.getInt("m_nDeviceType");
		final int m_nDeviceStatus = extras.getInt("m_nDeviceStatus");
		final int m_nDeviceID = extras.getInt("m_nDeviceID");		
		final String pdId = extras.getString("pdId");
		final String qty = extras.getString("qty");
		final String price = extras.getString("price");
//		final String img = extras.getString("itemImage");
		final String memberId = extras.getString("memberId");
		final Spinner s = (Spinner)findViewById(R.id.quantityTypeSpinner);
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.noOfItems, android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(aa);
        s.setOnItemSelectedListener(new MyOnItemSelectedListener());

		final List<Device> cart = ShoppingCartHelper.getCart();

		TextView productID = (TextView) findViewById(R.id.ProductID);
		TextView productName = (TextView) findViewById(R.id.ProductName);
		TextView productPrice = (TextView) findViewById(R.id.ProductPrice);
//		TextView productDescription = (TextView) findViewById(R.id.MerchantName);
		TextView productQty = (TextView) findViewById(R.id.Quantity);
//		Button cancel_button = (Button) findViewById(R.id.ProductCancel);
		Button Add_to_Cart = (Button) findViewById(R.id.ProductBuy);
		Button Click_to_View  = (Button) findViewById(R.id.MerchantName);
		Button viewBarcode1 = (Button) findViewById(R.id.ViewBarcode);
//		ImageView img1=(ImageView)findViewById(R.id.image1) ;
//
//		byte[] imageAsBytes = Base64.decode(img, Base64.NO_WRAP);
//		img1.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,
//				0, imageAsBytes.length));
		
		productID.setText(pdId);
		productName.setText(m_szDeviceName);
		productPrice.setText(price);

	//productDescription.setText(m_szDeviceAddress);
		productQty.setText(qty);
		
Click_to_View.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(ProductDetails.this);
				dialog.setContentView(R.layout.aboutus);
				dialog.setTitle("		Product Description");
				dialog.setCancelable(true);

				TextView abtUs = (TextView) dialog.findViewById(R.id.textView1);
				abtUs.setText(m_szDeviceAddress);
				Button Goback = (Button) dialog.findViewById(R.id.button1);
				Goback.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				// now that the dialog is set up, it's time to show it
				dialog.show();
			
			}
		});
			
		
	    final String prodDesc = pdId+"$"+ m_szDeviceName+"$"+price+"$"+ m_szDeviceAddress +"$"+ qty ;
	
		viewBarcode1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
             //  Context mcontext = getApplicationContext();
			final	Dialog dialog = new Dialog(ProductDetails.this);
                dialog.setContentView(R.layout.productbarcode);
                dialog.setTitle("Barcode for the selected Product");
                dialog.setCancelable(true);
                ImageView img = (ImageView) dialog.findViewById(R.id.barcode);
                String jadoo = "";
                               
                //take ascii values of char in string
                String test = prodDesc;
                for ( int i = 0; i < test.length(); ++i ) {
                  char c = test.charAt( i );
                  int j = (int) c;
//                  System.out.println(j);
                  jadoo+=j;
                  }
                Log.w("Transaction Activity ID is: ", prodDesc);
                try{        Log.i("taaaaaaaaaaaaaaaaaaaaaaaa",jadoo);

                	img.setImageBitmap(ProductDetails.this.encodeString(jadoo));
                	}catch(WriterException we){
                	
                } 
               	Button button = (Button) dialog.findViewById(R.id.goBack);
                button.setOnClickListener(new OnClickListener() {
                @Override
                    public void onClick(View v) {
                       dialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it    
                dialog.show();
				
				
				
			}
		});
		

		  Add_to_Cart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			
//				Bundle extras = getIntent().getExtras();
			   // String boolValue = extras.getString("memberId");

//			    String boolValue1 = extras.getString("memberId");
//				Log.i("memberId in FinalCart1 .java is :",boolValue1);

			    Log.i("NNNNNNNNNNNNNN", memberId);
			    if (!memberId.equalsIgnoreCase("guest")) {
					final String selQty = spinnerText;
Log.i("selQty--is ", selQty);
//			    	extras.putString("memID", memberId);
			    	Device d = new Device(m_szDeviceName,m_szDeviceAddress, m_nDeviceType, m_nDeviceStatus, m_nDeviceID, pdId, qty, price, selQty);
				    cart.add(d);
				    Toast.makeText(getBaseContext(),
							"Item successfully added to cart",
							Toast.LENGTH_LONG).show();
				    finish();

				    Log.i("NNNNNNNNNNNNNN", "just before activity start");

		} else {
					Toast.makeText(getBaseContext(),
							"You must Login before adding items to cart",
							Toast.LENGTH_LONG).show();
					showDialog(0);

					
					Intent newIntent = new Intent(v.getContext(), Login.class);
					newIntent.putExtra("NoShow", "1");

					startActivity(newIntent);
				}
			}
		});
		}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	        spinnerText = parent.getItemAtPosition(pos).toString();}

	    	
	        public void onNothingSelected(AdapterView<?> parent) {
	        	
	        	 Toast.makeText(parent.getContext(), "Nothing Selected", Toast.LENGTH_LONG).show();
	      // Do nothing.
	    }
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
		 	 String memberId=  extras.getString("memberId");
		  
		  switch (item.getItemId()) {
	      
	    
	      /////////// CART  ////////
		  case R.id.cart: {

				if (memberId.equals("guest")) {
					Intent guestUser = new Intent(ProductDetails.this, main.class);
					Toast.makeText(ProductDetails.this,
							"Please Login before visiting Cart", Toast.LENGTH_LONG)
							.show();

					startActivity(guestUser);
				} else {
					final List<Device> mCartList = ShoppingCartHelper.getCart();
					if (mCartList.size() == 0) {
						Toast.makeText(
								ProductDetails.this,
								"There are no items in your Cart. Please visit Product Catalog to add Items ",
								Toast.LENGTH_LONG).show();

					} else {
						Intent Cart = new Intent(ProductDetails.this,
								ShoppingCartActivity.class);
						Cart.putExtra("memId", memberId);

						Log.i("the Memberid passed to shopping cart through product list page",
								memberId);
						startActivity(Cart);
						//

					}
				}
				break;
			}

	      
	          
	          ///////////  LOGOUT  ////////
	          case R.id.logout:    {
	       if(memberId.equals("guest"))
	       {
	    	   Toast.makeText(ProductDetails.this,"You have already been logged out",Toast.LENGTH_LONG).show();
	       }
	       else {  final List<Device> mCartList = ShoppingCartHelper.getCart();
	    mCartList.clear();
	        Intent login = new Intent(ProductDetails.this, main.class );
	        	startActivity(login);
	       }
	        	break; }
	     
	        ///////////  ABOUT US  ////////
	        case R.id.aboutus:   { 
	        	
	        	final Dialog dialog = new Dialog(ProductDetails.this);
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
	        case R.id.home:
	        {
	        	 if(memberId.equals("guest"))
			     {
			 Intent guestUser = new Intent(ProductDetails.this, main.class);
      	 Toast.makeText(ProductDetails.this, "Please Login before visiting Cart", Toast.LENGTH_LONG).show();
			 startActivity(guestUser);
			     }
			     else
			     {
			    	  Intent Cart = new Intent(ProductDetails.this,mainAfterLogin.class );
			    	// String var =  extras.getString("memberId");
                   Log.i("the Memberid passed to shopping cart through product list page", memberId);
			    	 Cart.putExtra("memberId",memberId );
			     	  startActivity(Cart);
			     } 
	        }
	      }
	      return true;
	  }
}
