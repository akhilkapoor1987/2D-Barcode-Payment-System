package sjsu.mobilepayment.client;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateRegisterDetails extends Activity {

//	private String url = "http://10.0.2.2:8080/BarcodePaymentServer/UpdateUserDetails";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
////////////setting title bar////////////////
        List<Device> cartlist = ShoppingCartHelper.getCart();
        final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.updateaccount);


        if ( customTitleSupported ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            }
        Bundle extras1 = getIntent().getExtras();
        final String user= extras1.getString("username");
        final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
        if ( myTitleText != null ) {
            myTitleText.setText("Touch Pay"+"                         "+user+","+ cartlist.size()+" items");   }     
        ////////////////////////////////////////////

      
       
        
        Bundle extras = getIntent().getExtras();
         String firstname = extras.getString("firstname");
         String lastname = extras.getString("lastname");
         String address = extras.getString("address");
         String city = extras.getString("city");
         String state = extras.getString("state");
         String password = extras.getString("password");
         String email = extras.getString("email");
       final  String memId = extras.getString("memberId");
      final   String wallet = extras.getString("walletContents");

        
        Button cancel_Button = (Button)findViewById(R.id.detailsCancel1);
        Button next_Button = (Button)findViewById(R.id.detailsNext1);
        final EditText firstname1 = (EditText)findViewById(R.id.firstname1);
        final EditText lastname1 = (EditText)findViewById(R.id.lastname1);
        final EditText address1 = (EditText)findViewById(R.id.address1);
        final EditText city1 = (EditText)findViewById(R.id.city1);
        final EditText state1 = (EditText)findViewById(R.id.state1);
        final EditText email1 = (EditText)findViewById(R.id.email1);
        final EditText password1 = (EditText)findViewById(R.id.password1);
        firstname1.setText(firstname);
        lastname1.setText(lastname);
        address1.setText(address);
        city1.setText(city);
        state1.setText(state);
        email1.setText(email);
        password1.setText(password);
        
        
        cancel_Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent newIntent = new Intent(v.getContext(),mainAfterLogin.class);
				newIntent.putExtra("memberId", memId);
				newIntent.putExtra("username", user);
startActivity(newIntent);
			}
		});
        
        next_Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final String firstname2 = firstname1.getText().toString();
				final String lastname2 = lastname1.getText().toString();
				final String address2 = address1.getText().toString();
				final String city2 = city1.getText().toString();
				final String state2 = state1.getText().toString();
				final String email2 = email1.getText().toString();
				final String password2 = password1.getText().toString();

				
				
				
				Intent newIntent = new Intent(v.getContext(),UpdateRegisterWallet.class);
				newIntent.putExtra("memberId", memId);
				newIntent.putExtra("firstname", firstname2);
				newIntent.putExtra("lastname", lastname2);
				newIntent.putExtra("address", address2);
				newIntent.putExtra("city", city2);
				newIntent.putExtra("state", state2);
				newIntent.putExtra("password", password2);
				newIntent.putExtra("email", email2);
				newIntent.putExtra("walletcontents", wallet);
				newIntent.putExtra("username",user);
				startActivity(newIntent);
			}
		});
        
	}
}
