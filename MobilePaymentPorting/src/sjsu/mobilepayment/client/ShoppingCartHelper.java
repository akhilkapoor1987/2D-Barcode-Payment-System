package sjsu.mobilepayment.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.res.Resources;

public class ShoppingCartHelper {
 
 public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

// private static ArrayList<Device> catalog;
 private static List<Device> cart;
 private static Integer memberId;

 
// public static List<Product> getCatalog(Resources res){
//  if(catalog == null) {
//   catalog = new Vector<Product>();
//   catalog.add(new Product("Dead or Alive", res
//     .getDrawable(R.drawable.deadoralive),
//     "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
//   catalog.add(new Product("Switch", res
//     .getDrawable(R.drawable.switchbook),
//     "Switch by Chip Heath and Dan Heath", 24.99));
//   catalog.add(new Product("Watchmen", res
//     .getDrawable(R.drawable.watchmen),
//     "Watchmen by Alan Moore and Dave Gibbons", 14.99));
//  }
//  
//  return catalog;
// }
 
 public static List<Device> getCart() {
  if(cart == null) {
   cart = new Vector<Device>();
  }
  
  return  cart;
 }
 
 public static Integer getInteger(int memId){
	 memberId = new Integer(memId);
	 return memberId;
	 
 }
 

}
