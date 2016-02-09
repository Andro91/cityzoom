package zoom.city.android.main.pages.adresar;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import zoom.city.android.main.R;
import zoom.city.android.main.adapter.SearchListViewAdapter;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.parser.ParserSearch;

public class PreviewAdvancedSearchPage extends AppCompatActivity {

	String title;
	ListView listView;
	Thread thread;
	Handler handler;
	List<DataItem> pomDataItems;
	String jezikId, drzavaId, gradId, date, searchText, street, township,
			category, subcategory;
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

		inicComponent();
		fillData();
		onComponentCLick();
		
		Helper.inicActionBar(PreviewAdvancedSearchPage.this, ComponentInstance
				.getTitleString(ComponentInstance.STRING_NAPREDNA_PRETAGA));

//		ComponentInstance.inicTitleBar(this, ComponentInstance
//				.getTitleString(ComponentInstance.STRING_NAPREDNA_PRETAGA));

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listView.setAdapter(new SearchListViewAdapter(
						PreviewAdvancedSearchPage.this, R.layout.list_item,
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
					pomDataItems = ParserSearch.parseAdvance(searchText,
							drzavaId, jezikId, street, gradId, township,
							category, subcategory);
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

	}

	private void fillData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			title = extras.getString("title");
			date = extras.getString("date");
			searchText = extras.getString("searchText");
			street = extras.getString("street");
			township = extras.getString("township");
			category = extras.getString("category");
			subcategory = extras.getString("subcategory");
			gradId = extras.getString("gradId");
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
            finish();
            return true;
        }
        return true;
	}

}