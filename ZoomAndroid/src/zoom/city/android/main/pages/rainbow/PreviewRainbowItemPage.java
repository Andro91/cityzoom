package zoom.city.android.main.pages.rainbow;

import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import zoom.city.android.main.R;
import zoom.city.android.main.adapter.ListViewAdapter;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.parser.Parser;

public class PreviewRainbowItemPage extends AppCompatActivity {

	String title;
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
		
		Helper.inicActionBar(PreviewRainbowItemPage.this, ComponentInstance.getTitleString(ComponentInstance.STRING_RAINBOW));
		
		try{
			ComponentInstance.inicTitleBar(this, title.replaceAll("_RAINBOW", ""));
		}catch(NullPointerException ex){
			Log.d("MYTAG", "PrewiewRainbowItemPage:53 ERROR: " + ex.getMessage());
		}
		
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listView.setAdapter(new ListViewAdapter(
						PreviewRainbowItemPage.this, R.layout.list_item,
						pomDataItems, type,jezikId,title));
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
			title = extras.getString("title")+"_RAINBOW";
			date = extras.getString("date");
			type = extras.getString("type");
			
			if(type==null){
				type="company";
			}
		}
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.listView);
		progresLayout = (LinearLayout) findViewById(R.id.linearLayoutProgres);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish(); break;
            }
        return true;
    }
	
	public void inicActionBar() {
		try{
			ActionBar actionBar = getSupportActionBar();
			String title = ComponentInstance.getTitleString(ComponentInstance.STRING_RAINBOW);
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayUseLogoEnabled(false);
			if(!Helper.isBlank(title)){
				getSupportActionBar().setTitle(" " + title);
			}else{
				getSupportActionBar().setTitle(" " + "BACK");
			}
			}catch(Exception ex){
				Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
			}
	}

}