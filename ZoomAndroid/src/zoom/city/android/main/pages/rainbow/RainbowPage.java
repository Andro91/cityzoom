package zoom.city.android.main.pages.rainbow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.pages.MainPage;
import zoom.city.android.main.pages.cityzoom.CityZoomPage;
import zoom.city.android.main.pages.nightlife.NightlifePage;
import zoom.city.android.main.parser.JsonParser;

public class RainbowPage extends AppCompatActivity {

	LinearLayout layout1, layout2, layout3, layout4, layout5;
	TextView txtView1, txtView2, txtView3, txtView4, txtView5;

	InterstitialAd interstitial;
	SharedPreferences myPrefs;
	GoogleAnalytics mGa;
	Tracker mTracker;
	Handler mHandler;
	Runnable transitRunnable;
	int lastTransit = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_rainbow);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		mHandler = new Handler();
		
		//Generisanje tranzit strane
		if(DataContainer.androTransitImageList.get("9") != null){
			Intent i = new Intent(RainbowPage.this, MyAdActivity.class);
			i.putExtra("activity_code", 9);
			startActivity(i);
			mHandler.postDelayed(transitRunnable, 8000);
		}
		
		
		
		transitRunnable = new Runnable() {

			@Override
			public void run() {
				
				String transitIndex;
				
				if(lastTransit != 0){
					transitIndex = "9" + "-" + lastTransit;
				}else{
					transitIndex = "9";
				}
				lastTransit++;
				
				long timeSinceLastTransitDisplay = 0;
				if(DataContainer.androTransitTimestampList.get(transitIndex) != null){
					long timeNow = System.currentTimeMillis() / 1000L;
		        	long timeOfLastTransitDisplay = DataContainer.androTransitTimestampList.get(transitIndex);
		        	timeSinceLastTransitDisplay =  timeNow - timeOfLastTransitDisplay;
				}
				
    			Intent i = new Intent(RainbowPage.this, MyAdActivity.class);
    			i.putExtra("activity_code", 9);
    			i.putExtra("transit_index", transitIndex);

    			if(DataContainer.androTransitImageList.get(transitIndex) != null && timeSinceLastTransitDisplay < 300){
    				mHandler.postDelayed(transitRunnable, 8000);
    				startActivity(i);
    			}
    			
			}
		};
		
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
		
		new JSONParseTransit().execute();
		
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

							Intent intent = new Intent(RainbowPage.this,
									PreviewRainbowItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_CRUISING));
							intent.putExtra("titleup", ComponentInstance.STRING_RAINBOW);
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewRainbowItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_CRUISING));
					intent.putExtra("titleup", ComponentInstance.STRING_RAINBOW);
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
		txtView5.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_CRUISING));
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		layout1 = (LinearLayout) findViewById(R.id.linearLayoutIcon1);
		layout2 = (LinearLayout) findViewById(R.id.linearLayoutIcon2);
		layout3 = (LinearLayout) findViewById(R.id.linearLayoutIcon3);
		layout4 = (LinearLayout) findViewById(R.id.linearLayoutIcon4);
		layout5 = (LinearLayout) findViewById(R.id.linearLayoutIcon5);

		txtView1 = (TextView) findViewById(R.id.textViewIcon1);
		txtView2 = (TextView) findViewById(R.id.textViewIcon2);
		txtView3 = (TextView) findViewById(R.id.textViewIcon3);
		txtView4 = (TextView) findViewById(R.id.textViewIcon4);
		txtView5 = (TextView) findViewById(R.id.textViewIcon5);

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
	
	public void finishActivity(){
		Intent data = new Intent();
        data.putExtra("activity_code", 9);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == 1) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	int activityCode = data.getIntExtra("activity_code", 0);

	        	if(activityCode == 0) {
	        		return;
	        	}
	        	
    			Intent i = new Intent(RainbowPage.this, MyAdActivity.class);
    			i.putExtra("activityCode", 9);
    			if(DataContainer.androTransitImageList.get("9") != null){
    				startActivity(i);
    			}
	        }
	    }
	}
	
	private class JSONParseTransit extends AsyncTask<String, String, JSONObject> {
        @Override
        	protected void onPreExecute() {
        	    if(!myPrefs.getBoolean("RainbowPageFirstTime", true)){
        	    	this.cancel(true);
        	    }
        		super.onPreExecute();
        	}

		@Override
		protected JSONObject doInBackground(String... args) {
			JsonParser jParser = new JsonParser();

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formatted = simpleDateFormat.format(cal.getTime());
			
			if(DataContainer.androTransitImageList == null){
				DataContainer.androTransitImageList = new HashMap<String, Bitmap>();
			}
			
			JSONObject transitPage = null;
			
			int[] transitArray = {10,11,12};
			for(int i : transitArray){
				JSONObject json = jParser.getJSONFromUrl(
						Constant.MAIN_URL + "service/transit?seckey=zoom" 
								+ "&country=" + myPrefs.getString("drzavaId", "0")
								+ "&city=" + myPrefs.getString("gradId", "0") 
								+ "&date=" + formatted 
								+ "&page=" + i);
				
				try {
					for(int j = 0; j < json.getJSONArray("data").length(); j++){
						transitPage = json.getJSONArray("data").getJSONObject(j);
						
						String a = (j == 0) ? "" : "-" + j;
						DataContainer.androTransitImageList.put("" + i + a,Helper.getBitmapFromURL(transitPage.getString("image")));
						DataContainer.androTransitUrlList.put("" + i + a, transitPage.getString("link_android"));
						DataContainer.androTransitTimestampList.put("" + i + a, 0L);
						
						Log.d("MYTAG", "Transit index: " + "" + i + a);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("MYTAG", "526: " + "index " + i + " " + e.getMessage());
				} catch (OutOfMemoryError e) {
					Log.d("MYTAG", "RainbowPage: OOM " + e.getMessage());
				}
			}
			return transitPage;
		}
       
       @Override
    	protected void onPostExecute(JSONObject result) {
    	   Editor editor = myPrefs.edit();
    	   editor.putBoolean("RainbowPageFirstTime", false);
    	   editor.commit();
    	   super.onPostExecute(result);
    	}
   }


}
