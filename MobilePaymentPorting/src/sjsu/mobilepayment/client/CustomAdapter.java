package sjsu.mobilepayment.client;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

class CustomAdapterView extends LinearLayout {
	

	public CustomAdapterView(Context context, Device device) {
		super(context);
		/*
		 * setOnClickListener((OnClickListener) context); setClickable(true);
		 * setFocusable(false);
		 */
		setId(device.getDeviceID());

		// container is a horizontal layer
		setOrientation(LinearLayout.HORIZONTAL);
		setPadding(0, 6, 0, 6);
       
		// image:params
		LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Params.setMargins(6, 0, 6, 0);

		//		String jadoo="";

		// image:itself
		// load image
//		try {
//			ImageView ivLogo = new ImageView(context);
//			String imageString = device.getItemImage();
//			
//			   String test = imageString;
//	            for ( int i = 0; i < test.length(); ++i ) {
//	              char c = test.charAt( i );
//	              int j = (int) c;
////	              System.out.println(j);
//	              jadoo+=j;
//	              }
//
//			byte[] imageAsBytes = Base64.decode(jadoo, Base64.DEFAULT);
//			ivLogo.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,
//					0, imageAsBytes.length));
//
//			addView(ivLogo, Params);
//		} catch (Exception e) {
//			e.getMessage();
//		}
		// vertical layer for text
		Params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		LinearLayout PanelV = new LinearLayout(context);
		PanelV.setOrientation(LinearLayout.VERTICAL);
		PanelV.setGravity(Gravity.BOTTOM);
		PanelV.setBackgroundColor(0xFFEFEFEE);

		TextView textName = new TextView(context);
		textName.setTextSize(16);
		textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textName.setText(device.getDeviceName());
		textName.setTextColor(0xFF000000);
		PanelV.addView(textName);

		TextView textAddress = new TextView(context);
		textAddress.setTextSize(16);
		textAddress.setText(device.getDeviceAddress());
		textAddress.setTextColor(0xFF000000);
		PanelV.addView(textAddress);

		addView(PanelV, Params);
	}
}

public class CustomAdapter extends BaseAdapter /* implements OnClickListener */{

	/*
	 * private class OnItemClickListener implements OnClickListener{ private int
	 * mPosition; OnItemClickListener(int position){ mPosition = position; }
	 * public void onClick(View arg0) { Log.v("ddd", "onItemClick at position" +
	 * mPosition); } }
	 */

	public static final String LOG_TAG = "BI::CA";
	private Context context;
	private List<Device> deviceList;

	public CustomAdapter(Context context, List<Device> deviceList) {
		this.context = context;
		this.deviceList = deviceList;
	}

	public int getCount() {
		return deviceList.size();
	}

	public Object getItem(int position) {
		return deviceList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Device device = deviceList.get(position);
		View v = new CustomAdapterView(this.context, device);

		v.setBackgroundColor((position % 2) == 1 ? Color.GRAY : Color.BLACK);

		/* v.setOnClickListener(new OnItemClickListener(position)); */
		return v;
	}

	/*
	 * public void onClick(View v) { Log.v(LOG_TAG, "Row button clicked"); }
	 */

}
