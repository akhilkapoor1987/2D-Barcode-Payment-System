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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
//import com.google.zxing.client.android.Contents;
//import com.google.zxing.client.android.Intents;
//import com.google.zxing.client.android.R;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

/**
 * This class does the work of decoding the user's request and extracting all the data
 * to be encoded in a barcode.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class ViewBarcode extends Activity{
	
	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;
	private Bitmap encodeString(String input) throws WriterException{
		BarcodeFormat QR_CODE=BarcodeFormat.QR_CODE;
//      byte[] b = input.getBytes();
//      //convert the byte array into a UTF-8 string
//      String data = "";
//      try {
//          data = new String(b, "ISO-8859-1");
//      }
//      catch (UnsupportedEncodingException e) {
//       //the program shouldn't be able to get here
//       Log.i("The message", "cannot convert to ISO");
//      }
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
        
        setContentView(R.layout.barcodetransaction);
        Bundle extras = getIntent().getExtras();
        ImageView img = (ImageView)findViewById(R.id.barcode);
        Button confirm_button = (Button)findViewById(R.id.nextbutton);
        
        final String selectedID = extras.getString("orderDetails");
        final String memberId5 = extras.getString("memberId");
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

        	img.setImageBitmap(ViewBarcode.this.encodeString(jadoo));
        	}catch(WriterException we){
        	
        }


        
////        final String selected12 = guessAppropriateEncoding("1"); 
//        
//        
//        
//        byte[] b = selectedID.getBytes();
//      //convert the byte array into a UTF-8 string
//      String data;
//      try {
//          data = new String(b, "ISO-8859-1");
//      }
//      catch (UnsupportedEncodingException e) {
//       //the program shouldn't be able to get here
//       return;
//      }
//
//      //get a byte matrix for the data
//      BitMatrix matrix;
//      com.google.zxing.qrcode.QRCodeWriter writer = new QRCodeWriter();
//      try {
//       matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, 1, 1);
//      }
//      catch (com.google.zxing.WriterException e) {
//       //exit the method
//       return;
//      }	
//
//     
//      //create buffered image to draw to
//      Bitmap bm;
//      BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
//      ByteArrayOutputStream os = new ByteArrayOutputStream();
//      try {
//		ImageIO.write(image, "bmp", os);
//		InputStream is = new ByteArrayInputStream(os.toByteArray());
//		bm = BitmapFactory.decodeStream(is); 
//		  img.setImageBitmap(bm);
//		  Log.i("Image Shown", "OMG its a barcode");
//
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		  Log.i("Image Shown", ":(");
//
//	}


      
      
//OutputStream out = new OutputStream();
//		InputStream is = conn.getInputStream();  
//		BufferedInputStream bis = new BufferedInputStream(is);  
//		Bitmap bm = BitmapFactory.decodeStream(bis);  
//
//      //write the image to the output stream
//      ImageIO.write(image, "png", );
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

       
//        
//        Bitmap bitmap;
//        try {
//            bitmap = encodeString(selected12);
//            ImageView view = (ImageView)findViewById(R.id.barcode);
//
//            view.setImageBitmap(bitmap);
//          } catch (WriterException we) {
//            Log.w("2489048", we);
//          }
//        
//       
//        
//        
//        Log.w("Transaction Activity ID is: ", selectedID);
//        

        confirm_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(v.getContext(),TransactionHistory.class);
				newIntent.putExtra("memberId", memberId5);
				startActivity(newIntent);
			}
			
			
			
			
			
		});
	
	}
	
	
//	 private static String guessAppropriateEncoding(CharSequence selectedID) {
//		    // Very crude at the moment
//		    for (int i = 0; i < selectedID.length(); i++) {
//		      if (selectedID.charAt(i) > 0xFF) {
//		        return "ISO-8859-1";
//		      }
//		    }
//		    return null;
//		  }

}