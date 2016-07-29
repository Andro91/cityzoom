package zoom.city.android.main.pages.kulturnivodic;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import zoom.city.android.main.MyAdActivity;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.pages.PreviewListItemPage;
import zoom.city.android.main.pages.cityzoom.CityZoomPage;
import zoom.city.android.main.pages.cityzoom.SmestajPage;
import zoom.city.android.main.pages.kalendar.KalendarPickerPage;

public class KulturniVodicPage extends AppCompatActivity {

	TextView txtView1, txtView2, txtView3, txtView4, txtView5, txtView6;
	TextView txtDateMounth, txtDateDay;
	LinearLayout layout1, layout2, layout3, layout4, layout5, layout6;

	String date;
	SharedPreferences myPrefs;

	InterstitialAd interstitial;
	
	GoogleAnalytics mGa;
	Tracker mTracker;
	Handler mHandler;
	Runnable transitRunnable;
	int lastTransit = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_kulturni_vodic);

		mHandler = new Handler();
		
		//Generisanje tranzit strane
		if(DataContainer.androTransitImageList.get("2") != null){
			Intent i = new Intent(KulturniVodicPage.this, MyAdActivity.class);
			i.putExtra("activity_code", 4);
			startActivity(i);
			mHandler.postDelayed(transitRunnable, 8000);
		}
		
		
		
		transitRunnable = new Runnable() {

			@Override
			public void run() {
				
				String transitIndex;
				
				if(lastTransit != 0){
					transitIndex = "4" + "-" + lastTransit;
				}else{
					transitIndex = "4";
				}
				lastTransit++;
				
				long timeSinceLastTransitDisplay = 0;
				if(DataContainer.androTransitTimestampList.get(transitIndex) != null){
					long timeNow = System.currentTimeMillis() / 1000L;
		        	long timeOfLastTransitDisplay = DataContainer.androTransitTimestampList.get(transitIndex);
		        	timeSinceLastTransitDisplay =  timeNow - timeOfLastTransitDisplay;
				}
				
    			Intent i = new Intent(KulturniVodicPage.this, MyAdActivity.class);
    			i.putExtra("activity_code", 4);
    			i.putExtra("transit_index", transitIndex);

    			if(DataContainer.androTransitImageList.get(transitIndex) != null && timeSinceLastTransitDisplay > 300){
    				mHandler.postDelayed(transitRunnable, 8000);
    				startActivity(i);
    			}
    			
			}
		};
		
		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

//		ComponentInstance.inicTitleBar(this, ComponentInstance
//				.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));

		

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//date = sdf.format(Calendar.getInstance().getTime());
		//date = "";

		inicComponent();
		fillData();
		onComponentClick();
		
		Helper.inicActionBar(KulturniVodicPage.this, ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));

		// inicijalizacija malih banera
				ComponentInstance.inicSmallBaner(this, "kulturnivodic",
						myPrefs.getString("drzavaId", "0"),
						myPrefs.getString("gradId", "0"),
						myPrefs.getString("jezikId", "0"),
						"");
		
		ComponentInstance.inicGoogleBaner(this,
				myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/7330128266");
		
		sendGoogleAnaliticsData("Kulturni vodic - screen");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		interstitial = ComponentInstance.inicFullScreenBaner(this,"ca-app-pub-1530516813542398/5419416260");
		super.onResume();
	}
	

	

	private void onComponentClick() {
		layout1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Kalendar
				if (interstitial.isLoaded()) {
					interstitial.show();
					interstitial.setAdListener(new AdListener() {

						@Override
						public void onAdClosed() {
							super.onAdClosed();

							Intent intent = new Intent(KulturniVodicPage.this,
									KalendarPickerPage.class);
							intent.putExtra("title",ComponentInstance
											.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(KulturniVodicPage.this,
							KalendarPickerPage.class);
					intent.putExtra("title",ComponentInstance
									.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
					startActivity(intent);

				}

			}
		});
		layout2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (interstitial.isLoaded()) {
					interstitial.show();
					interstitial.setAdListener(new AdListener() {

						@Override
						public void onAdClosed() {
							// TODO Auto-generated method stub
							super.onAdClosed();

							Intent intent = new Intent(KulturniVodicPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_POZORISTA));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
							//intent.putExtra("date", date);
							intent.putExtra("date", "");

							// Kulturni vodicu je tipa event
							intent.putExtra("type", "event");

							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(KulturniVodicPage.this,
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_POZORISTA));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
					//intent.putExtra("date", date);
					intent.putExtra("date", "");

					// Kulturni vodicu je tipa event
					intent.putExtra("type", "event");

					startActivity(intent);

				}

			}
		});
		layout3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (interstitial.isLoaded()) {
					interstitial.show();
					interstitial.setAdListener(new AdListener() {

						@Override
						public void onAdClosed() {
							// TODO Auto-generated method stub
							super.onAdClosed();

							Intent intent = new Intent(KulturniVodicPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_KONCERTI));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
							//intent.putExtra("date", date);
							intent.putExtra("date", "");

							// Kulturni vodicu je tipa event
							intent.putExtra("type", "event");

							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(KulturniVodicPage.this,
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_KONCERTI));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
					//intent.putExtra("date", date);
					intent.putExtra("date", "");

					// Kulturni vodicu je tipa event
					intent.putExtra("type", "event");

					startActivity(intent);

				}

			}
		});
		layout4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (interstitial.isLoaded()) {
					interstitial.show();
					interstitial.setAdListener(new AdListener() {

						@Override
						public void onAdClosed() {
							// TODO Auto-generated method stub
							super.onAdClosed();

							Intent intent = new Intent(KulturniVodicPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_MUZEJI_GALERIJE));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
							//intent.putExtra("date", date);
							intent.putExtra("date", "");

							// Kulturni vodicu je tipa event
							intent.putExtra("type", "event");

							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(KulturniVodicPage.this,
							PreviewListItemPage.class);
					intent.putExtra(
							"title",
							ComponentInstance
									.getTitleString(ComponentInstance.STRING_MUZEJI_GALERIJE));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
					//intent.putExtra("date", date);
					intent.putExtra("date", "");

					// Kulturni vodicu je tipa event
					intent.putExtra("type", "event");

					startActivity(intent);

				}

			}
		});
		layout5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (interstitial.isLoaded()) {
					interstitial.show();
					interstitial.setAdListener(new AdListener() {

						@Override
						public void onAdClosed() {
							// TODO Auto-generated method stub
							super.onAdClosed();

							Intent intent = new Intent(KulturniVodicPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_KULTURNI_CENTRI));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
							//intent.putExtra("date", date);
							intent.putExtra("date", "");

							// Kulturni vodicu je tipa event
							intent.putExtra("type", "event");

							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(KulturniVodicPage.this,
							PreviewListItemPage.class);
					intent.putExtra(
							"title",
							ComponentInstance
									.getTitleString(ComponentInstance.STRING_KULTURNI_CENTRI));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
					//intent.putExtra("date", date);
					intent.putExtra("date", "");

					// Kulturni vodicu je tipa event
					intent.putExtra("type", "event");

					startActivity(intent);

				}

			}
		});
		layout6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (interstitial.isLoaded()) {
					interstitial.show();
					interstitial.setAdListener(new AdListener() {

						@Override
						public void onAdClosed() {
							// TODO Auto-generated method stub
							super.onAdClosed();

							Intent intent = new Intent(KulturniVodicPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_BIBLIOTEKE));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
							//intent.putExtra("date", date);
							intent.putExtra("date", "");

							// Kulturni vodicu je tipa event
							intent.putExtra("type", "event");

							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(KulturniVodicPage.this,
							PreviewListItemPage.class);
					intent.putExtra(
							"title",
							ComponentInstance
									.getTitleString(ComponentInstance.STRING_BIBLIOTEKE));
					intent.putExtra("titleup", ComponentInstance
									.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
					//intent.putExtra("date", date);
					intent.putExtra("date", "");

					// Kulturni vodicu je tipa event
					intent.putExtra("type", "event");

					startActivity(intent);

				}

			}
		});
	}

	private void fillData() {
		// TODO Auto-generated method stub
		txtView1.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KALENDAR));
		txtView2.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_POZORISTA));
		txtView3.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KONCERTI));
		txtView4.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_MUZEJI_GALERIJE));
		txtView5.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KULTURNI_CENTRI));
		txtView6.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_BIBLIOTEKE));

		txtDateDay.setText(""
				+ ComponentInstance.getAppCalendar().getTime().getDate());
		SimpleDateFormat sf = new SimpleDateFormat("MMMM yyyy.");
		txtDateMounth.setText(sf.format(ComponentInstance.getAppCalendar()
				.getTime()));
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		txtView1 = (TextView) findViewById(R.id.textViewIcon1);
		txtView2 = (TextView) findViewById(R.id.textViewIcon2);
		txtView3 = (TextView) findViewById(R.id.textViewIcon3);
		txtView4 = (TextView) findViewById(R.id.textViewIcon4);
		txtView5 = (TextView) findViewById(R.id.textViewIcon5);
		txtView6 = (TextView) findViewById(R.id.textViewIcon6);

		txtDateDay = (TextView) findViewById(R.id.textViewDateDay);
		txtDateMounth = (TextView) findViewById(R.id.textViewDateMounth);

		layout1 = (LinearLayout) findViewById(R.id.linearLayoutIcon1);
		layout2 = (LinearLayout) findViewById(R.id.linearLayoutIcon2);
		layout3 = (LinearLayout) findViewById(R.id.linearLayoutIcon3);
		layout4 = (LinearLayout) findViewById(R.id.linearLayoutIcon4);
		layout5 = (LinearLayout) findViewById(R.id.linearLayoutIcon5);
		layout6 = (LinearLayout) findViewById(R.id.linearLayoutIcon6);
		
		//ComponentInstance.inicTopButton(this);
	}
	
	private void sendGoogleAnaliticsData(String title) {

		/*
		 * Tracker tracker = GoogleAnalytics.getInstance(this).getTracker(
		 * "UA-43704233-1");
		 * 
		 * HashMap<String, String> hitParameters = new HashMap<String,
		 * String>(); hitParameters.put(Fields.HIT_TYPE, "appview");
		 * hitParameters.put(Fields.SCREEN_NAME, "News Sreen - " +title);
		 * 
		 * tracker.send(hitParameters);
		 */
		String GA_PROPERTY_ID = "UA-52812931-2";

		// Dispatch period in seconds.
		int GA_DISPATCH_PERIOD = 10;

		// Prevent hits from being sent to reports, i.e. during testing.
		boolean GA_IS_DRY_RUN = false;

		// GA Logger verbosity.
		LogLevel GA_LOG_VERBOSITY = LogLevel.INFO;

		// Key used to store a user's tracking preferences in SharedPreferences.
		final String TRACKING_PREF_KEY = "trackingPreference";

		mGa = GoogleAnalytics.getInstance(this.getApplicationContext());
		mTracker = mGa.getTracker(GA_PROPERTY_ID);

		// Set dispatch period.
		GAServiceManager.getInstance().setLocalDispatchPeriod(
				GA_DISPATCH_PERIOD);

		// Set dryRun flag.
		mGa.setDryRun(GA_IS_DRY_RUN);

		// Set Logger verbosity.
		mGa.getLogger().setLogLevel(GA_LOG_VERBOSITY);

		// Set the opt out flag when user updates a tracking preference.
		SharedPreferences userPrefs = PreferenceManager
				.getDefaultSharedPreferences(this.getApplicationContext());
		userPrefs
				.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
					@Override
					public void onSharedPreferenceChanged(
							SharedPreferences sharedPreferences, String key) {
						if (key.equals(TRACKING_PREF_KEY)) {
							GoogleAnalytics
									.getInstance(getApplicationContext())
									.setAppOptOut(
											sharedPreferences.getBoolean(key,
													false));
						}
					}
				});

		mTracker.set(Fields.SCREEN_NAME, "Sreen - " + title);


	}

	@Override
	public void onStart() {
		super.onStart();
		// Send a screen view when the Activity is displayed to the user.
		mTracker.send(MapBuilder.createAppView().build());
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//mTracker.set(Fields.SCREEN_NAME, "Home Screen");
		mTracker.send(MapBuilder.createAppView().build());
		//mTracker.send(null);
	}
	
    @Override
    public void onBackPressed() {
       finishActivity();
       super.onBackPressed();
    }
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home: {
        	finishActivity();
        	finish();
        	break;
        	}
        }
            return true;
    }
	
	public void inicActionBar() {
		try{
			String title = ComponentInstance.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC);
			Toolbar toolbar = null;
			if(findViewById(R.id.toolbar) != null){
				toolbar = (Toolbar) findViewById(R.id.toolbar); 
				if(!Helper.isBlank(title)){
					((TextView)toolbar.findViewById(R.id.toolbar_title)).setText(title);
				}else{
					((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("");
				}
			}else{
				Log.d("MYTAG","Null toolbar");
			}
			setSupportActionBar(toolbar); 
			
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			}catch(Exception ex){
				Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
			}
	}
	
	public void finishActivity(){
		Intent data = new Intent();
        data.putExtra("activity_code", 4);
        setResult(Activity.RESULT_OK, data);
	}
	

}
