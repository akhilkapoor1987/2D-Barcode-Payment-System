package sjsu.mobilepayment.client;

public class Order {

	private String titleName;
	private String orderPrice;
	private String orderQuantity;
	
	
	
	public Order(String titleName, String orderPrice, String orderQuantity) {
	this.titleName = titleName;
	this.orderPrice = orderPrice;
	this.orderQuantity = orderQuantity;
	}

	


	public String getTitleName() {
	return titleName;
	}
	
	public String getOrderPrice() {
		return orderPrice;
		}
	
	public String getOrderQuantity() {
		return orderQuantity;
		}

	public void setTitleName(String titleName) {
	this.titleName = titleName;
	}
	
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
		}
	
	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
		}

	}
