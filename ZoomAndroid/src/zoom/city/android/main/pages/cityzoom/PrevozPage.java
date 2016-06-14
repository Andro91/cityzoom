package zoom.city.android.main.pages.cityzoom;

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
import zoom.city.android.main.pages.PreviewListItemPage;

public class PrevozPage extends AppCompatActivity {

	LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8;
	TextView txtView1, txtView2, txtView3, txtView4, txtView5, txtView6,
			txtView7, txtView8;
	
	InterstitialAd interstitial;
	
	GoogleAnalytics mGa;
	Tracker mTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_prevoz);

		//Generisanje tranzit strane
		if(DataContainer.androTransitImageList.get("13") != null){
			Intent i = new Intent(PrevozPage.this, MyAdActivity.class);
			i.putExtra("activity_code", 13);
			startActivity(i);
		}
		
		inicComponent();
		fillData();
		onCOmponentClick();

//		ComponentInstance.inicTitleBar(this, ComponentInstance
//				.getTitleString(ComponentInstance.STRING_PREVOZ));

		// inicijalizacija velikog banera
		SharedPreferences myPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		ComponentInstance.inicBigBaner(this, "traffic",
				myPrefs.getString("drzavaId", "0"),
				myPrefs.getString("gradId", "0"));
		
		Helper.inicActionBar(PrevozPage.this, ComponentInstance.getTitleString(ComponentInstance.STRING_PREVOZ));
		
		ComponentInstance.inicGoogleBaner(this,myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/4237061060");
	
		sendGoogleAnaliticsData("Prevoz - screen");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		interstitial = ComponentInstance.inicFullScreenBaner(this,"ca-app-pub-1530516813542398/2186748267");
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

							Intent intent = new Intent(PrevozPage.this,
									PreviewListItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_BUS));
							startActivity(intent);
						}
					});
				} else {

					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_BUS));
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

							Intent intent = new Intent(PrevozPage.this,
									PreviewListItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_TAXI));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_TAXI));
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

							Intent intent = new Intent(PrevozPage.this,
									PreviewListItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_RENT_CAR));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_RENT_CAR));
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

							Intent intent = new Intent(PrevozPage.this,
									PreviewListItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_RENT_SCUTER));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_RENT_SCUTER));
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

							Intent intent = new Intent(PrevozPage.this,
									PreviewListItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_AVIO));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_AVIO));
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
							Intent intent = new Intent(PrevozPage.this,
									PreviewListItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_VOZ));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_VOZ));
					startActivity(intent);
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
							Intent intent = new Intent(PrevozPage.this,
									PreviewListItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_RENT_BOAT));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_RENT_BOAT));
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
							Intent intent = new Intent(PrevozPage.this,
									PreviewListItemPage.class);
							intent.putExtra("title", ComponentInstance
									.getTitleString(ComponentInstance.STRING_METRO));
							startActivity(intent);
						}
					});
				} else {
					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_METRO));
					startActivity(intent);
				}
				
			}
		});
	}

	private void fillData() {
		// TODO Auto-generated method stub
		txtView1.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_BUS));
		txtView2.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_TAXI));
		txtView3.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_RENT_CAR));
		txtView4.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_RENT_SCUTER));
		txtView5.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_AVIO));
		txtView6.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_VOZ));
		txtView7.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_RENT_BOAT));
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

		txtView1 = (TextView) findViewById(R.id.textViewTitle1);
		txtView2 = (TextView) findViewById(R.id.textViewTitle2);
		txtView3 = (TextView) findViewById(R.id.textViewTitle3);
		txtView4 = (TextView) findViewById(R.id.textViewTitle4);
		txtView5 = (TextView) findViewById(R.id.textViewTitle5);
		txtView6 = (TextView) findViewById(R.id.textViewTitle6);
		txtView7 = (TextView) findViewById(R.id.textViewTitle7);
		txtView8 = (TextView) findViewById(R.id.textViewTitle8);

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
        data.putExtra("activity_code", 13);
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

}
