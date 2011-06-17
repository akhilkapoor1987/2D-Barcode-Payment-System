package sjsu.mobilepayment.client;

import java.util.Hashtable;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;




public class Receipt extends Activity {
/** Called when the activity is first created. */
	private List<Device> mcartlist;
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
		    return bitmap;
		 
		}
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
mcartlist= ShoppingCartHelper.getCart();
mcartlist.clear();
////////////setting title bar////////////////
//List<Device> cartlist = ShoppingCartHelper.getCart();
final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

setContentView(R.layout.receipt);


if ( customTitleSupported ) {
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
    }
Bundle extras1 = getIntent().getExtras();
final	String user = extras1.getString("username");
final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
if ( myTitleText != null ) {
    myTitleText.setText("Touch Pay"+"                         "+user+","+ mcartlist.size()+" Items");   }     
////////////////////////////////////////////




final TextView tv0 = (TextView) findViewById(R.id.order);
final TextView tv1 =  (TextView) findViewById(R.id.amount);
final TextView tv2 =  (TextView) findViewById(R.id.wallet);


final Button history_btn = (Button)  findViewById(R.id.history);

Bundle extras = getIntent().getExtras();


final String memId = (extras.getString("memberId"));
String amount = (extras.getString("Amt"));
String walletamt = (extras.getString("newAmt1"));
String order = (extras.getString("orderNum"));
Log.i("order number is ",order);
//final String memberId = extras.getString("memberId");
final String orderDetails = memId+amount+walletamt+order;

Log.i("memId + amoutn + walletamt+ order", memId+ amount+walletamt+order+"");

tv0.setText(order);
tv1.setText(amount);
tv2.setText(walletamt);

final Button button = (Button) findViewById(R.id.button93);
button.setOnClickListener(new OnClickListener() {
    	
    	
    	@Override
    	public void onClick(View v) {
			// TODO Auto-generated method stub
         //  Context mcontext = getApplicationContext();
		final	Dialog dialog = new Dialog(Receipt.this);
            dialog.setContentView(R.layout.productbarcode);
            dialog.setTitle("Barcode for Purchase Receipt");
            dialog.setCancelable(true);
            ImageView img = (ImageView) dialog.findViewById(R.id.barcode);
            String jadoo = "";
                           
            //take ascii values of char in string
            String test = orderDetails;
            for ( int i = 0; i < test.length(); ++i ) {
              char c = test.charAt( i );
              int j = (int) c;
//              System.out.println(j);
              jadoo+=j;
              }
            Log.w("Transaction Activity ID is: ", orderDetails);
            try{        Log.i("taaaaaaaaaaaaaaaaaaaaaaaa",jadoo);

            	img.setImageBitmap(Receipt.this.encodeString(jadoo));
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


history_btn.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		Intent newIntent = new Intent(v.getContext(),TransactionHistory.class);
	newIntent.putExtra("memberId", memId);
	newIntent.putExtra("username",user);
		startActivity(newIntent);
	}
});

//Home_btn.setOnClickListener(new View.OnClickListener() {
//	
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//		Intent newIntent = new Intent(v.getContext(),mainAfterLogin.class);
//		newIntent.putExtra("memberId", memId);
//			startActivity(newIntent);
//		
//	}
//});
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
      	  
      	  Intent Cart = new Intent(Receipt.this ,mainAfterLogin.class);
      	 Cart.putExtra("memberId", memberId);
      	Cart.putExtra("username",user);
      	  startActivity(Cart);
      	  break; }
    
        
        ///////////  LOGOUT  ////////
        case R.id.logout:    {
      final List<Device> mCartList = ShoppingCartHelper.getCart();
  mCartList.clear();
      Intent login = new Intent(Receipt.this, main.class );
      	startActivity(login);
      	
      	break; }
   
      ///////////  ABOUT US  ////////
      case R.id.aboutus:   { 
      	
      	final Dialog dialog = new Dialog(Receipt.this);
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
