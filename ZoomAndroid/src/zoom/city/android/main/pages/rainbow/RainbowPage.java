package zoom.city.android.main.pages.rainbow;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.helper.Helper;

public class RainbowPage extends AppCompatActivity {

	LinearLayout layout1, layout2, layout3, layout4;
	TextView txtView1, txtView2, txtView3, txtView4;

	InterstitialAd interstitial;
	
	GoogleAnalytics mGa;
	Tracker mTracker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_rainbow);

		inicComponent();
		fillData();
		onCOmponentClick();
		Helper.inicActionBar(RainbowPage.this, ComponentInstance.getTitleString(ComponentInstance.STRING_RAINBOW));
		
//		ComponentInstance.inicTitleBar(this, ComponentInstance
//				.getTitleString(ComponentInstance.STRING_RAINBOW));

		// inicijalizacija velikog banera
		SharedPreferences myPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		ComponentInstance.inicBigBaner(this, "rainbow",
				myPrefs.getString("drzavaId", "0"),
				myPrefs.getString("gradId", "0"));
		
		ComponentInstance.inicGoogleBaner(this,myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/7050926665");
	
		sendGoogleAnaliticsData("Rainbow - screen");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		interstitial = ComponentInstance.inicFullScreenBaner(this,"ca-app-pub-1530516813542398/9710015060");
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

							Intent intent = new Intent(RainbowPage.this,
									PreviewRainbowItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_MUST_TO_DO));
							intent.putExtra("titleup", ComponentInstance.STRING_RAINBOW);
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewRainbowItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_MUST_TO_DO));
					intent.putExtra("titleup", ComponentInstance.STRING_RAINBOW);
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

							Intent intent = new Intent(RainbowPage.this,
									RainbowIcePicePage.class);
							
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							RainbowIcePicePage.class);
					
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

							Intent intent = new Intent(RainbowPage.this,
									RainbowNightlifePage.class);
							
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							RainbowNightlifePage.class);
					
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

							Intent intent = new Intent(RainbowPage.this,
									RainbowWellnesPage.class);
							
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							RainbowWellnesPage.class);
					
					startActivity(intent);
				}
				
			}
		});
	}

	private void fillData() {
		// TODO Auto-generated method stub
		txtView1.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_MUST_TO_DO));
		txtView2.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ICE_PICE));
		txtView3.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_NIGHT_LIFE));
		txtView4.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_WELNES_AND_SPA));
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		layout1 = (LinearLayout) findViewById(R.id.linearLayoutIcon1);
		layout2 = (LinearLayout) findViewById(R.id.linearLayoutIcon2);
		layout3 = (LinearLayout) findViewById(R.id.linearLayoutIcon3);
		layout4 = (LinearLayout) findViewById(R.id.linearLayoutIcon4);

		txtView1 = (TextView) findViewById(R.id.textViewIcon1);
		txtView2 = (TextView) findViewById(R.id.textViewIcon2);
		txtView3 = (TextView) findViewById(R.id.textViewIcon3);
		txtView4 = (TextView) findViewById(R.id.textViewIcon4);

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