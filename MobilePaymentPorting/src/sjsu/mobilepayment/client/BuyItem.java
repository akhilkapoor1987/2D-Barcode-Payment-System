package sjsu.mobilepayment.client;

public class BuyItem {

	private String orderDetails;
	private String purchaseDate;
	private String orderTotal;
	private boolean selected;
	private int deviceID;
	private String transactionId;
	private String memberId;


	public BuyItem(String orderDetails, String purchaseDate, String orderTotal,
			int deviceID, String transactionId, String memberId)

	{

		this.purchaseDate = purchaseDate;

		this.orderTotal = orderTotal;
		this.selected = false;
		this.deviceID = deviceID;
		this.transactionId = transactionId;
		this.memberId = memberId;
		this.orderDetails = orderDetails;

	}

	// public BuyItem(String transactionImage, String purchaseDate,
	// String orderTotal, int deviceID, String transactionId, String memberId) {
	// this.orderTotal = orderTotal;
	// this.transactionImage = transactionImage;
	// this.purchaseDate = purchaseDate;
	// this.deviceID = deviceID;
	// this.transactionId = transactionId;
	// this.memberId = memberId;
	//
	// }

	public String getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}

	

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public int getDeviceID() {
		return deviceID;
	}

}