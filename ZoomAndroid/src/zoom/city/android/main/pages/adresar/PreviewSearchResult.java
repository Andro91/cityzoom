package zoom.city.android.main.pages.adresar;

import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import zoom.city.android.main.R;
import zoom.city.android.main.adapter.SearchListViewAdapter;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.parser.ParserSearch;

public class PreviewSearchResult extends AppCompatActivity {

	String title;
	ListView listView;
	Thread thread;
	Handler handler;
	List<DataItem> pomDataItems;
	String jezikId, drzavaId, gradId, date,searchText;
	SharedPreferences myPrefs;
	LinearLayout progresLayout;

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
		inicActionBar();
		//ComponentInstance.inicTitleBar(this, ComponentInstance.getTitleString(ComponentInstance.STRING_ADRESAR));

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listView.setAdapter(new SearchListViewAdapter(
						PreviewSearchResult.this, R.layout.list_item,
						pomDataItems,jezikId));
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
					pomDataItems = ParserSearch.parseSimple(searchText, drzavaId,gradId,jezikId);
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
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_list_menu, menu);
        return true;
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

	}

	private void fillData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			title = extras.getString("title");
			date = extras.getString("date");
			searchText =extras.getString("searchText");
		}
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.listView);
		progresLayout = (LinearLayout) findViewById(R.id.linearLayoutProgres);
	}
	
	public void inicActionBar() {
		try{
			ActionBar actionBar = getSupportActionBar();
			
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayUseLogoEnabled(false);
			if(!isBlank(title)){
				getSupportActionBar().setTitle(" " + title);
			}else{
				getSupportActionBar().setTitle(" " + "BACK");
			}
			}catch(Exception ex){
				Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
			}
	}
	
    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0 || string.equals("null"))
            return true;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!Character.isWhitespace(string.codePointAt(i)))
                return false;
        }
        return true;
    }

}