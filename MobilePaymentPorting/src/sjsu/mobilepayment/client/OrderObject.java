package sjsu.mobilepayment.client;


public class OrderObject {

	private String productName;

	private String merchantName;
	private String quantity;
	private String price;
	
	
	
	public OrderObject(String productName, String merchantName, String quantity, String price) {
	
		this.productName = productName;
		this.merchantName = merchantName;
		this.quantity = quantity;
		this.price = price;
	}

	
	
	
	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getMerchantName() {
		return merchantName;
	}



	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}



	public String getQuantity() {
		return quantity;
	}



	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	


	}
