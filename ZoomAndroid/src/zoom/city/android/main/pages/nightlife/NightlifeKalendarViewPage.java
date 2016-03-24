package zoom.city.android.main.pages.nightlife;

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
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.adapter.ListViewAdapter;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.parser.Parser;

public class NightlifeKalendarViewPage extends AppCompatActivity {

	String title, dateTitle;
	String jezikId, drzavaId, gradId, date;

	LinearLayout layout1, layout2, layout3;
	TextView txtView1, txtView2, txtView3;

	ListView listView;
	LinearLayout progresLayout;

	Thread thread;
	Handler handler;

	List<DataItem> pomDataItems;
	SharedPreferences myPrefs;
	
	String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_nightlife_kalendar_view);

		inicComponent();
		fillData();
		onComponentClick();

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		jezikId = myPrefs.getString("jezikId", "0");
		drzavaId = myPrefs.getString("drzavaId", "0");
		gradId = myPrefs.getString("gradId", "0");

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			title = extras.getString("title");
			date = extras.getString("date");
			dateTitle = extras.getString("dateTitle");
		}

		//ComponentInstance.inicTitleBar(this, dateTitle);
		
		Helper.inicActionBar(NightlifeKalendarViewPage.this, dateTitle);

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listView.setAdapter(new ListViewAdapter(
						NightlifeKalendarViewPage.this, R.layout.list_item,
						pomDataItems,type,jezikId,title));
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
					pomDataItems = Parser.parse(ComponentInstance
							.getTitleString(ComponentInstance.STRING_EVENTS),
							drzavaId, gradId, jezikId, date);
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

	private void onComponentClick() {

	}

	private void fillData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			title = extras.getString("title");
			date = extras.getString("date");
			
			//NightLife calendar je uvek event
			type = "event";
		}
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.listView);
		progresLayout = (LinearLayout) findViewById(R.id.linearLayoutProgres);
	}

}
