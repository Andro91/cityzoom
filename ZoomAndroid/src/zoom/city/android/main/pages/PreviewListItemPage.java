package zoom.city.android.main.pages;

import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.adapter.ListViewAdapter;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.parser.Parser;

public class PreviewListItemPage extends AppCompatActivity {

	String title,titleUp;
	ListView listView;
	Thread thread;
	Handler handler;
	List<DataItem> pomDataItems;
	String jezikId, drzavaId, gradId, date;
	SharedPreferences myPrefs;
	LinearLayout progresLayout;
	String type;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_preview_list_item);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		jezikId = myPrefs.getString("jezikId", "0");
		drzavaId = myPrefs.getString("drzavaId", "0");
		gradId = myPrefs.getString("gradId", "0");
				
		inicComponent();
		fillData();
		onComponentCLick();
		
		Helper.inicActionBar(PreviewListItemPage.this, title);

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listView.setAdapter(new ListViewAdapter(
						PreviewListItemPage.this, R.layout.list_item,
						pomDataItems, type, jezikId, title));
				progresLayout.setVisibility(View.GONE);
				super.handleMessage(msg);
			}

		};

		thread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				try {
					pomDataItems = Parser.parse(title, drzavaId, gradId,
							jezikId, date);
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					handler.sendEmptyMessage(0);
				}
			}

		};
		thread.start();

		progresLayout.setVisibility(View.VISIBLE);

	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        }
        return true;
	}
	

	private void onComponentCLick() {
		// TODO Auto-generated method stub
/*
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> adapterView, View arg1,
					final int position, long arg3) {
				
				Thread thread = new Thread(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						
						DataItem dataItem =(DataItem) adapterView.getAdapter().getItem(position);
						ParserItem.parseSendData(dataItem.getId(), jezikId, "event");
					}
					
				};
				
				thread.start();

			}
		});
*/
	}

	private void fillData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			titleUp = extras.getString("titleup");
			Log.d("MYTAG", ""+extras.getString("titleup"));
			title = extras.getString("title");
			date = extras.getString("date");
			type = extras.getString("type");
			
			if(type==null){
				type="company";
			}
		}
		
		//ComponentInstance.inicTopButton(this);
		//ComponentInstance.inicTitleBar(this, title);
		//ComponentInstance.inicBackTitleBar(this, titleUp);
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.listView);
		progresLayout = (LinearLayout) findViewById(R.id.linearLayoutProgres);
	}
	
	
//	public void inicActionBar() {
//		try{
//			Toolbar toolbar = null;
//			if(findViewById(R.id.toolbar) != null){
//				toolbar = (Toolbar) findViewById(R.id.toolbar); 
//				if(!Helper.isBlank(title)){
//					((TextView)toolbar.findViewById(R.id.toolbar_title)).setText(title);
//				}else{
//					((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("");
//				}
//				
//			}else{
//				Log.d("MYTAG","Null toolbar");
//			}
//			setSupportActionBar(toolbar); 
//			
//			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//			getSupportActionBar().setDisplayShowTitleEnabled(false);
//			}catch(Exception ex){
//				Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
//			}
//	}

}
