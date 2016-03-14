package zoom.city.android.main;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import zoom.city.android.main.adapter.FavouritesAdapter;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.database.DatabaseHandler;
import zoom.city.android.main.helper.Helper;

public class PreviewFavouritesPage extends AppCompatActivity {

	String title,titleUp;
	ListView listView;
	Thread thread;
	Handler handler;
	List<DataItem> pomDataItems;
	String jezikId, drzavaId, gradId, date;
	SharedPreferences myPrefs;
	LinearLayout progresLayout;
	String type;
	Activity activity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_preview_list_item);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		activity = this;
		jezikId = myPrefs.getString("jezikId", "0");
		drzavaId = myPrefs.getString("drzavaId", "0");
		gradId = myPrefs.getString("gradId", "0");
				
		inicComponent();
		fillData();
		onComponentCLick();
		
		Helper.inicActionBar(this,"Favourites");

		

		//progresLayout.setVisibility(View.VISIBLE);

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
	
	

	@Override
	protected void onResume() {
		super.onResume();
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listView.setAdapter(new FavouritesAdapter(
						PreviewFavouritesPage.this, R.layout.list_item,
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
					DatabaseHandler db = new DatabaseHandler(activity);
					pomDataItems = db.getAllFavourites();
					Log.d("MYTAG", "pomDataItems count = " + pomDataItems.size());
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					handler.sendEmptyMessage(0);
				}
			}

		};
		thread.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		thread.interrupt();
	}

	private void onComponentCLick() {
		
	}

	private void fillData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			titleUp = extras.getString("titleup");
			title = extras.getString("title");
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
	

}
