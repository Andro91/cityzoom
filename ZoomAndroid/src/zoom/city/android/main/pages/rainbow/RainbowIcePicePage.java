package zoom.city.android.main.pages.rainbow;

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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import zoom.city.android.main.pages.cityzoom.CityZoomPage;

public class RainbowIcePicePage extends AppCompatActivity {

	LinearLayout layout1, layout2;
	TextView txtView1, txtView2;
	
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
		setContentView(R.layout.page_rainbow_pice);
		
		mHandler = new Handler();
		
		//Generisanje tranzit strane
		if(DataContainer.androTransitImageList.get("10") != null){
			Intent i = new Intent(RainbowIcePicePage.this, MyAdActivity.class);
			i.putExtra("activity_code", 10);
			startActivity(i);
			mHandler.postDelayed(transitRunnable, 8000);
		}
		
		
		
		transitRunnable = new Runnable() {

			@Override
			public void run() {
				
				String transitIndex;
				
				if(lastTransit != 0){
					transitIndex = "10" + "-" + lastTransit;
				}else{
					transitIndex = "10";
				}
				lastTransit++;
				
				long timeSinceLastTransitDisplay = 0;
				if(DataContainer.androTransitTimestampList.get(transitIndex) != null){
					long timeNow = System.currentTimeMillis() / 1000L;
		        	long timeOfLastTransitDisplay = DataContainer.androTransitTimestampList.get(transitIndex);
		        	timeSinceLastTransitDisplay =  timeNow - timeOfLastTransitDisplay;
				}
				
    			Intent i = new Intent(RainbowIcePicePage.this, MyAdActivity.class);
    			i.putExtra("activity_code", 10);
    			i.putExtra("transit_index", transitIndex);

    			if(DataContainer.androTransitImageList.get(transitIndex) != null && timeSinceLastTransitDisplay > 300){
    				mHandler.postDelayed(transitRunnable, 8000);
    				startActivity(i);
    			}
    			
			}
		};
		
		inicComponent();
		fillData();
		onCOmponentClick();
		
		Helper.inicActionBar(RainbowIcePicePage.this, ComponentInstance.getTitleString(ComponentInstance.STRING_NIGHT_LIFE));

//		ComponentInstance.inicTitleBar(this, ComponentInstance
//				.getTitleString(ComponentInstance.STRING_ICE_PICE));

		// inicijalizacija velikog banera
		SharedPreferences myPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		ComponentInstance.inicBigBaner(this, "rainbow-drinkfood",
				myPrefs.getString("drzavaId", "0"),
				myPrefs.getString("gradId", "0"));
		
		ComponentInstance.inicGoogleBaner(this,myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/8527659867");
	
		sendGoogleAnaliticsData("Rainbow Pice - screen");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		interstitial = ComponentInstance.inicFullScreenBaner(this,"ca-app-pub-1530516813542398/6616947868");
		super.onResume();

	}

	private void onCOmponentClick() {
		// TODO Auto-generated method stub
		layout1.setOnClickListener(new View.OnClickListener() {

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
							Intent intent = new Intent(RainbowIcePicePage.this,
									PreviewRainbowItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_CAFEI));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewRainbowItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_CAFEI));
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
							Intent intent = new Intent(RainbowIcePicePage.this,
									PreviewRainbowItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_RESTORANI));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewRainbowItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_RESTORANI));
					startActivity(intent);
				}
				
			}
		});
		
	}

	private void fillData() {
		// TODO Auto-generated method stub
		txtView1.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_CAFEI));
		txtView2.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_RESTORANI));
		
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		layout1 = (LinearLayout) findViewById(R.id.linearLayoutIcon1);
		layout2 = (LinearLayout) findViewById(R.id.linearLayoutIcon2);
		

		txtView1 = (TextView) findViewById(R.id.textViewTitle1);
		txtView2 = (TextView) findViewById(R.id.textViewTitle2);

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
	
	public void finishActivity(){
		Intent data = new Intent();
        data.putExtra("activity_code", 10);
        setResult(Activity.RESULT_OK, data);
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
			ActionBar actionBar = getSupportActionBar();
			String title = ComponentInstance.getTitleString(ComponentInstance.STRING_NIGHT_LIFE);
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
