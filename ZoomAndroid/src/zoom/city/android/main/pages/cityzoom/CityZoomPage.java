package zoom.city.android.main.pages.cityzoom;

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
import zoom.city.android.main.pages.PreviewListItemPage;
import zoom.city.android.main.parser.JsonParser;

public class CityZoomPage extends AppCompatActivity {

	LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, layout7,
			layout8, layout9;
	TextView txtView1, txtView2, txtView3, txtView4, txtView5, txtView6,
			txtView7, txtView8, txtView9;
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
		setContentView(R.layout.page_city_zoom);
		
		mHandler = new Handler();

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//Generisanje tranzit strane
		if(DataContainer.androTransitImageList.get("3") != null){
			Intent i = new Intent(CityZoomPage.this, MyAdActivity.class);
			i.putExtra("activity_code", 3);
			startActivity(i);
			mHandler.postDelayed(transitRunnable, 8000);
		}
		
		inicComponent();
		onCOmponentClick();
		fillData();
		
		transitRunnable = new Runnable() {

			@Override
			public void run() {
				
				String transitIndex;
				Log.d("MYTAG","transitRunnable");
				if(lastTransit != 0){
					transitIndex = "3" + "-" + lastTransit;
				}else{
					transitIndex = "3";
				}
				lastTransit++;
				
				long timeSinceLastTransitDisplay = 0;
				if(DataContainer.androTransitTimestampList.get(transitIndex) != null){
					long timeNow = System.currentTimeMillis() / 1000L;
		        	long timeOfLastTransitDisplay = DataContainer.androTransitTimestampList.get(transitIndex);
		        	timeSinceLastTransitDisplay =  timeNow - timeOfLastTransitDisplay;
				}
				
    			Intent i = new Intent(CityZoomPage.this, MyAdActivity.class);
    			i.putExtra("activity_code", 3);
    			i.putExtra("transit_index", transitIndex);

    			if(DataContainer.androTransitImageList.get(transitIndex) != null && timeSinceLastTransitDisplay > 300){
    				mHandler.postDelayed(transitRunnable, 8000);
    				startActivity(i);
    			}
    			
			}
		};
		
		new JSONParseTransit().execute();

//		ComponentInstance.inicTitleBar(this, ComponentInstance
//				.getTitleString(ComponentInstance.STRING_CITY_ZOOM));

		// inicijalizacija velikog banera
		SharedPreferences myPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		ComponentInstance.inicBigBaner(this, "cityzoom",
				myPrefs.getString("drzavaId", "0"),
				myPrefs.getString("gradId", "0"));

		ComponentInstance.inicGoogleBaner(this,
				myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/8806861465");
		
		sendGoogleAnaliticsData("City zoom - screen");
		
		Helper.inicActionBar(CityZoomPage.this, ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		interstitial = ComponentInstance.inicFullScreenBaner(this,"ca-app-pub-1530516813542398/8372882669");
		super.onResume();

	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		layout1 = (LinearLayout) findViewById(R.id.linearLayoutIcon1);
		layout2 = (LinearLayout) findViewById(R.id.linearLayoutIcon2);
		layout3 = (LinearLayout) findViewById(R.id.linearLayoutIcon3);
		layout4 = (LinearLayout) findViewById(R.id.linearLayoutIcon4);
		layout5 = (LinearLayout) findViewById(R.id.linearLayoutIcon5);
		layout6 = (LinearLayout) findViewById(R.id.linearLayoutIcon6);
		layout7 = (LinearLayout) findViewById(R.id.linearLayoutIcon7);
		layout8 = (LinearLayout) findViewById(R.id.linearLayoutIcon8);
		layout9 = (LinearLayout) findViewById(R.id.linearLayoutIcon9);

		txtView1 = (TextView) findViewById(R.id.textViewTitle1);
		txtView2 = (TextView) findViewById(R.id.textViewTitle2);
		txtView3 = (TextView) findViewById(R.id.textViewTitle3);
		txtView4 = (TextView) findViewById(R.id.textViewTitle4);
		txtView5 = (TextView) findViewById(R.id.textViewTitle5);
		txtView6 = (TextView) findViewById(R.id.textViewTitle6);
		txtView7 = (TextView) findViewById(R.id.textViewTitle7);
		txtView8 = (TextView) findViewById(R.id.textViewTitle8);
		txtView9 = (TextView) findViewById(R.id.textViewTitle9);
		
		//ComponentInstance.inicTopButton(this);
	}

	private void fillData() {
		// TODO Auto-generated method stub
		txtView1.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ZNAMENITOSTI));
		txtView2.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_INSPIRACIJA));
		txtView3.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_SMESTAJ));
		txtView4.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_PREVOZ));
		txtView5.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_SHOPPING));
		txtView6.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KES));
		txtView7.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_WI_FI));
		txtView8.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_MEDICO));
		txtView9.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_GAS));
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

							Intent intent = new Intent(CityZoomPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_ZNAMENITOSTI));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(CityZoomPage.this,
							PreviewListItemPage.class);
					intent.putExtra(
							"title",
							ComponentInstance
									.getTitleString(ComponentInstance.STRING_ZNAMENITOSTI));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
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

							Intent intent = new Intent(CityZoomPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_INSPIRACIJA));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));

							// INSPIRACIJA je tipa event
							intent.putExtra("type", "event");

							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(CityZoomPage.this,
							PreviewListItemPage.class);
					intent.putExtra(
							"title",
							ComponentInstance
									.getTitleString(ComponentInstance.STRING_INSPIRACIJA));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));

					// INSPIRACIJA je tipa event
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

							Intent intent = new Intent(CityZoomPage.this,
									SmestajPage.class);
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(CityZoomPage.this,
							SmestajPage.class);
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

							Intent intent = new Intent(CityZoomPage.this,
									PrevozPage.class);
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(view.getContext(),
							PrevozPage.class);
					startActivityForResult(intent, 1);

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

							Intent intent = new Intent(CityZoomPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_SHOPPING));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(CityZoomPage.this,
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_SHOPPING));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
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

							Intent intent = new Intent(CityZoomPage.this,
									KesPage.class);
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(CityZoomPage.this, KesPage.class);
					startActivityForResult(intent, 1);

				}

			}
		});
		layout7.setOnClickListener(new View.OnClickListener() {

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

							Intent intent = new Intent(CityZoomPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_WI_FI));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(CityZoomPage.this,
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_WI_FI));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
					startActivity(intent);

				}

			}
		});
		layout8.setOnClickListener(new View.OnClickListener() {

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

							Intent intent = new Intent(CityZoomPage.this,
									MedicoPage.class);
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(CityZoomPage.this,
							MedicoPage.class);
					startActivityForResult(intent, 1);
				}

			}
		});
		layout9.setOnClickListener(new View.OnClickListener() {

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

							Intent intent = new Intent(CityZoomPage.this,
									PreviewListItemPage.class);
							intent.putExtra(
									"title",
									ComponentInstance
											.getTitleString(ComponentInstance.STRING_GAS));
							intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(CityZoomPage.this,
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_GAS));
					intent.putExtra("titleup", ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
					startActivity(intent);
				}

			}
		});
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
        data.putExtra("activity_code", 3);
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
			String title = ComponentInstance.getTitleString(ComponentInstance.STRING_CITY_ZOOM);
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
	        	
    			Intent i = new Intent(CityZoomPage.this, MyAdActivity.class);
    			i.putExtra("activityCode", 3);
    			if(DataContainer.androTransitImageList.get("3") != null){
    				mHandler.postDelayed(transitRunnable, 8000);
    				startActivity(i);
    			}
	        }
	    }
	}
	
	private class JSONParseTransit extends AsyncTask<String, String, JSONObject> {
        @Override
        	protected void onPreExecute() {
        	    if(!myPrefs.getBoolean("CityZoomPageFirstTime", true)){
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
			
			int[] transitArray = {6,7,13};
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
//						Log.d("MYTAG", "Image: " + transitPage.getString("image"));
//						Log.d("MYTAG", "URL: " + transitPage.getString("link_android"));
						
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
					Log.d("MYTAG", "CityZoomPage: OOM " + e.getMessage());
				}
			}
			return transitPage;
		}
       
       @Override
    	protected void onPostExecute(JSONObject result) {
    	   Editor editor = myPrefs.edit();
    	   editor.putBoolean("CityZoomPageFirstTime", false);
    	   editor.commit();
    	   super.onPostExecute(result);
    	}
   }
	
	
	

}
