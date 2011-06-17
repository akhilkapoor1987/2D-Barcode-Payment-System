package sjsu.mobilepayment.client;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import sjsu.mobilepayment.client.ShoppingCartActivity;
import sjsu.mobilepayment.client.RegisterWallet.MyOnItemSelectedListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;




public class TransactionDetailed extends Activity {
/** Called when the activity is first created. */
OrderAdapter customAdapter;
ArrayList<OrderObject> orderList = new ArrayList<OrderObject>();
ArrayList<String> item = new ArrayList<String>();
;
//private String url = "http://10.0.2.2:8080/BarcodePaymentServer/ProductCatalog";

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
Bundle extras = getIntent().getExtras();
String purchaseDate = (extras.getString("purchaseDate"));
String orderTotal = (extras.getString("orderTotal"));
final String transactionID = (extras.getString("transactionID"));
String orderDetails = (extras.getString("orderDetails"));
String memberId3 = extras.getString("memberId");
final String user = extras.getString("username");

final String memberId4 = memberId3;
////////////setting title bar////////////////
List<Device> cartlist = ShoppingCartHelper.getCart();
final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

setContentView(R.layout.transactmain);


if ( customTitleSupported ) {
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
    }

final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
if ( myTitleText != null ) {
    myTitleText.setText("Touch Pay"+"                         "+ cartlist.size()+" items");   }     


///////////////////////////////////////////

final String orderDetails1 = orderDetails;




String[] orderDetailsSplit = orderDetails.split("\\@");


for(int k=0; k<orderDetailsSplit.length; k++)
{
	Log.i("orderDetailSplit",orderDetailsSplit[k]);

	item.add(orderDetailsSplit[k]);
}




final TextView tv0 = (TextView) findViewById(R.id.total7);
final TextView tv1 = (TextView) findViewById(R.id.total2);
final TextView tv2 = (TextView) findViewById(R.id.total1);
tv0.setText(orderTotal);
tv1.setText(purchaseDate);
tv2.setText(transactionID);




final Button Buy_btn = (Button)  findViewById(R.id.finalBuy1);



Buy_btn.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent viewShoppingCartIntent = new Intent(getBaseContext(), TransactionHistory.class);
   	 viewShoppingCartIntent.putExtra("memberId", memberId4);
   	 viewShoppingCartIntent.putExtra("username", user);

	    startActivity(viewShoppingCartIntent);

	    	
	    	
	    	
	}
});


//final Button Buy_btn2 = (Button)  findViewById(R.id.finalBuy2);
//
//
//
//Buy_btn2.setOnClickListener(new View.OnClickListener() {
//	
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//		Intent viewShoppingCartIntent = new Intent(getBaseContext(), mainAfterLogin.class);
//   	 viewShoppingCartIntent.putExtra("memberId", memberId4);
//
//	    startActivity(viewShoppingCartIntent);
//
//	    	
//	    	
//	    	
//	}
//});

final Button button = (Button) findViewById(R.id.button93);
button.setOnClickListener(new OnClickListener() {
   
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
     //  Context mcontext = getApplicationContext();
	final	Dialog dialog = new Dialog(TransactionDetailed.this);
        dialog.setContentView(R.layout.productbarcode);
        dialog.setTitle("Barcode for Transaction no. "+transactionID);
        dialog.setCancelable(true);
        ImageView img = (ImageView) dialog.findViewById(R.id.barcode);
        String jadoo = "";
                       
        //take ascii values of char in string
        String test = orderDetails1;
        for ( int i = 0; i < test.length(); ++i ) {
          char c = test.charAt( i );
          int j = (int) c;
//          System.out.println(j);
          jadoo+=j;
          }
        Log.w("Transaction Activity ID is: ", orderDetails1);
        try{        Log.i("taaaaaaaaaaaaaaaaaaaaaaaa",jadoo);

        	img.setImageBitmap(TransactionDetailed.this.encodeString(jadoo));
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



orderList.add(new OrderObject("PRODUCT","MERCHANT","QUANTITY", "PRICE"));
for(int j=0; j<orderDetailsSplit.length;j++)
  { 
	 
//	 Intent i = getIntent();
//	 Bundle extras = i.getExtras();
	 String[] itemsDetails = item.get(j).split("\\$");

	 Log.i("Product Name" + " " + "Price" + " " + "Quantity",  itemsDetails[0]+" "+itemsDetails[1]+" "+itemsDetails[2]+" "+ itemsDetails[3]);
	 
	 orderList.add(new OrderObject(itemsDetails[0],itemsDetails[1], "       "+itemsDetails[2], "       "+itemsDetails[3]));

	
  } 


customAdapter = new OrderAdapter(this, R.layout.transactioncustomlayout,orderList);

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
	 	String user = extras.getString("username");

	  switch (item.getItemId()) {
    
  
    /////////// CART  ////////
        case R.id.home:    { 
      	  
      	  Intent Cart = new Intent(TransactionDetailed.this ,mainAfterLogin.class);
      	 Cart.putExtra("memberId", memberId);
      	 Cart.putExtra("username", user);

      	  startActivity(Cart);
      	  break; }
    
        
        ///////////  LOGOUT  ////////
        case R.id.logout:    {
      final List<Device> mCartList = ShoppingCartHelper.getCart();
  mCartList.clear();
      Intent login = new Intent(TransactionDetailed.this, main.class );
      	startActivity(login);
      	
      	break; }
   
      ///////////  ABOUT US  ////////
      case R.id.aboutus:   { 
      	
      	final Dialog dialog = new Dialog(TransactionDetailed.this);
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




private class OrderAdapter extends ArrayAdapter<OrderObject> {

private ArrayList<OrderObject> items;

public OrderAdapter(Context context, int resource,
ArrayList<OrderObject> items) {
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
OrderObject o = items.get(position);
if (o != null) {
TextView tt = (TextView) v.findViewById(R.id.TextView01);
if (tt != null) {
tt.setText(o.getProductName()+" "+o.getMerchantName()+" "+o.getQuantity()+" "+o.getPrice());
}
}
return v;
}
}

}

