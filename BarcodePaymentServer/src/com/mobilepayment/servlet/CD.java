package com.mobilepayment.servlet;

public class CD {
	String pdId ;
	String pname ;
	String pdesc ;
	float price;
	int qty ;
	String memberId;
CD() {
    pdId="";
    pname="";
    pdesc="";
    price=0;
    qty =0;
    memberId = "";
  }
  public void setpdId(String id) {
    pdId=id;
  }
  public String getpdId() {
    return pdId;
  }
  public void setpname(String name) {
    pname=name;
  }
  public String getpname() {
    return pname;
  }
  public void setpdesc(String desc) {
	    pdesc=desc;
	  }
	  public String getpdesc() {
	    return pdesc;
	  }
  public void setprice(float cost) {
    price= cost;
  }
  public float getprice() {
    return price;
  }
  
  public void setQuantity(int q) {
    qty=q;
  }
  public int getQuantity() {
    return qty;
  }
  
  public void setMemberId(String id2) {
	    memberId=id2;
	  }
	  public String getMemberId() {
	    return memberId;
	  }
 

}