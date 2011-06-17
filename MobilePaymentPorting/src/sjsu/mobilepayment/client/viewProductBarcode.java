/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sjsu.mobilepayment.client;

import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * This class does the work of decoding the user's request and extracting all the data
 * to be encoded in a barcode.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class viewProductBarcode extends Activity{
	
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

		public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.productbarcode);


        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }
        Bundle extras1 = getIntent().getExtras();
	final	String user = extras1.getString("username");
        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                         "+user+","+ cartlist.size()+" items");   }     
        ////////////////////////////////////////////
        
        
        
        Bundle extras = getIntent().getExtras();
        ImageView img = (ImageView)findViewById(R.id.barcode);
        Button goBack_button = (Button)findViewById(R.id.goBack);
        
        final String selectedID = extras.getString("productDetails");
      //  final String memberId5 = extras.getString("memberId");
        String orderDetails = selectedID;
        String jadoo = "";
        
        
        //take ascii values of char in string
        String test = orderDetails;
        for ( int i = 0; i < test.length(); ++i ) {
          char c = test.charAt( i );
          int j = (int) c;
//          System.out.println(j);
          jadoo+=j;
          }
        
        
        
        Log.i("taaaaaaaaaaaaaaaaaaaaaaaa",selectedID);
//        String abc = guessAppropriateEncoding(selectedID);
       

        Log.w("Transaction Activity ID is: ", selectedID);
        try{        Log.i("taaaaaaaaaaaaaaaaaaaaaaaa",jadoo);

        	img.setImageBitmap(viewProductBarcode.this.encodeString(jadoo));
        	}catch(WriterException we){
        	
        }

        	goBack_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}