package zoom.city.android.main.pages.smallbaners;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
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
import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.parser.Parser;

public class SmallBanersPreviewPage extends AppCompatActivity {

	String title;
	ListView listView;
	Thread thread;
	Handler handler;
	List<DataItem> pomDataItems;
	String jezikId, drzavaId, gradId, date,id;
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

		jezikId = myPrefs.getString("jezikId", "0");
		drzavaId = myPrefs.getString("drzavaId", "0");
		gradId = myPrefs.getString("gradId", "0");
		
		activity = this;

		inicComponent();
		fillData();
		onComponentCLick();

		//ComponentInstance.inicTitleBar(this, title);

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listView.setAdapter(new ListViewAdapter(
						SmallBanersPreviewPage.this, R.layout.list_item,
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
					pomDataItems = Parser.parseItems(Constant.MAIN_URL
							+ "service/events?seckey=zoom&country="+drzavaId+"&city="+gradId+"&language="+jezikId+"&id="+id+"&date="+date);
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					handler.sendEmptyMessage(0);
				}
			}

		};
		thread.start();

		progresLayout.setVisibility(View.VISIBLE);
		
		Helper.inicActionBar(SmallBanersPreviewPage.this, title);

	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            activity.finish();
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
			id = extras.getString("id");
			title = extras.getString("title");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");			
			date = sdf.format(Calendar.getInstance().getTime());

			type ="event";
			
		
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
			if(!Helper.isBlank(title)){
				getSupportActionBar().setTitle(" " + title);
			}else{
				getSupportActionBar().setTitle(" " + "BACK");
			}
//			getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
			}catch(Exception ex){
				Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
			}
	}

}