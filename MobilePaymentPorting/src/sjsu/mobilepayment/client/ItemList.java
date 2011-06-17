//package sjsu.mobilepayment.client;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class ItemList extends Activity {
//
//	public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.itemlist);
//        setTitle(R.string.shoppingWinTitle);
//        
//        ListView lv = (ListView)findViewById(R.id.itemListView);
//        String[] itemList = getResources().getStringArray(R.array.itemList);
//        
//        //ArrayAdapter aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemList);
//        ArrayAdapter aa = new ArrayAdapter<String>(this,R.layout.rootmenyview,itemList);
//        lv.setAdapter(aa);
//        
//        lv.setOnItemClickListener(new OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position,
//					long id) {
//				
//				Intent newIntent = new Intent(view.getContext(),Login.class);
//				newIntent.putExtra("selectedItem", Integer.toString(position));
//				startActivity(newIntent);
//				
//			}
//        	
//        	
//        });
//	}
//}
