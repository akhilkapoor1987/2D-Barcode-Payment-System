package sjsu.mobilepayment.client;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
 
 private List<Device> mProductList;
 private LayoutInflater mInflater;
 private boolean mShowCheckbox;
 
 public ProductAdapter(List<Device> list, LayoutInflater inflater, boolean showCheckbox) {
  mProductList = list;
  mInflater = inflater;
  mShowCheckbox = showCheckbox;
 }

 @Override
 public int getCount() {
  return mProductList.size();
 }

 @Override
 public Object getItem(int position) {
  return mProductList.get(position);
 }

 @Override
 public long getItemId(int position) {
  return position;
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
  final ViewItem item;
  
  if (convertView == null) {
   convertView = mInflater.inflate(R.layout.item,
     null);
   item = new ViewItem();
   
//   item.productImageView = (ImageView) convertView
//   .findViewById(R.id.ImageViewItem);
   
//   item.productImageView = (ImageView) convertView.findViewById(R.drawable.icon);

   
   item.productTitle = (TextView) convertView.findViewById(R.id.TextViewItem);
   
   item.productCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelect);
   
   convertView.setTag(item);
  } else {
   item = (ViewItem) convertView.getTag();
  }
  
  Device curProduct = mProductList.get(position);
  
//  item.productImageView.setImageDrawable(item.productImageView.getDrawable());
  item.productTitle.setText(curProduct.getDeviceName());
  
  if(!mShowCheckbox) {
   item.productCheckbox.setVisibility(View.GONE);
  } else {
   if(curProduct.selected == true)
    item.productCheckbox.setChecked(true);
   else
    item.productCheckbox.setChecked(false);
  }
  
  
  return convertView;
 }
 
 
 private class ViewItem {
//  ImageView productImageView;
  TextView productTitle;
  CheckBox productCheckbox;
 }

}

