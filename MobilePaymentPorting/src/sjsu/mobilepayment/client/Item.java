package sjsu.mobilepayment.client;

public class Item {

	private String productId;
	private String productName;
	private String price;
	private String MerchantID;
	private String MerchantName;
	private String MerchantURL;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMerchantID() {
		return MerchantID;
	}
	public void setMerchantID(String merchantID) {
		MerchantID = merchantID;
	}
	public String getMerchantName() {
		return MerchantName;
	}
	public void setMerchantName(String merchantName) {
		MerchantName = merchantName;
	}
	public String getMerchantURL() {
		return MerchantURL;
	}
	public void setMerchantURL(String merchantURL) {
		MerchantURL = merchantURL;
	}
	
}
