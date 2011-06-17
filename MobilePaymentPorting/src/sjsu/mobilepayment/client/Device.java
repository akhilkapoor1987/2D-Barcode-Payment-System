package sjsu.mobilepayment.client;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

	private String m_szDeviceName;
	private String m_szDeviceAddress;
	private int m_nDeviceType;
	private int m_nDeviceStatus;
	private int m_nDeviceID;
	private String pdId;
	private String qty;
	private String price;
	private String selQty;
	public Drawable productImage;
	public boolean selected;
	public String itemImage;

	public Device(String deviceName, String deviceAddress, int deviceType,
			int deviceStatus, int deviceID, String pdId, String qty,
			String price, String selQty/* , Drawable productImage */) {
		this.m_szDeviceName = deviceName;
		this.m_szDeviceAddress = deviceAddress;
		this.m_nDeviceType = deviceType;
		this.m_nDeviceStatus = deviceStatus;
		this.m_nDeviceID = deviceID;
		this.pdId = pdId;
		this.qty = qty;
		this.price = price;
		// this.productImage = R.drawable.icon;
		this.selected = false;
		this.selQty = selQty;

	}

	public Device( String pdId,String pname,String pdesc, String qty,String price , String selQty){
		this.m_szDeviceName = pname;
		this.m_szDeviceAddress = pdesc;
		this.pdId = pdId;
		this.qty = qty;
		this.price = price;
		this.selected = false;
		this.selQty = selQty;
	}

	public int describeContents() {
		return 0;
	}

	// write your object's data to the passed-in Parcel
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(selQty);
		out.writeString(price);
		out.writeString(m_szDeviceName);
		out.writeString(pdId);

	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
		public Device createFromParcel(Parcel in) {
			return new Device(in);
		}

		public Device[] newArray(int size) {
			return new Device[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	private Device(Parcel in) {
		selQty = in.readString();
		price = in.readString();
		m_szDeviceName = in.readString();
		pdId = in.readString();

	}

	

	
	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getDeviceName() {
		return m_szDeviceName;
	}

	public void setDeviceName(String deviceName) {
		this.m_szDeviceName = deviceName;
	}

	public String getDeviceAddress() {
		return m_szDeviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.m_szDeviceAddress = deviceAddress;
	}

	public int getDeviceType() {
		return m_nDeviceType;
	}

	public void setDeviceType(int deviceType) {
		this.m_nDeviceType = deviceType;
	}

	public int getDeviceStatus() {
		return m_nDeviceStatus;
	}

	public void setDeviceStatus(int deviceStatus) {
		this.m_nDeviceStatus = deviceStatus;
	}

	public int getDeviceID() {
		return m_nDeviceID;
	}

	public void setDeviceID(int deviceID) {
		this.m_nDeviceID = deviceID;
	}

	public String getpdId() {
		return this.pdId;
	}

	public String getqty() {
		return this.qty;
	}

	public String getPrice() {
		return this.price;
	}

	public String getSelQty() {
		return this.selQty;
	}

}