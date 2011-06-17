package sjsu.mobilepayment.client;

public class WalletItem {

	private String credit;
	private String debit;
	private String date;
	private boolean selected;
	private int deviceID;
	
	public WalletItem(String credit, String debit, String date, int deviceID) {
		super();
		this.credit = credit;
		this.debit = debit;
		this.date = date;
		this.deviceID = deviceID;
		this.selected=false;
	}
	
	
	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	public int getDeviceID() {
		return deviceID;
	}


	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
	}