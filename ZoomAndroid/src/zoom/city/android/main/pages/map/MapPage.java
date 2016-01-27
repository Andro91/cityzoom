package zoom.city.android.main.pages.map;

import java.util.HashMap;
import java.util.List;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.pages.previewitem.PreviewItemPage;
import zoom.city.android.main.parser.ParserMap;

public class MapPage extends Activity {

	SharedPreferences myPrefs;
	Thread thread;

	ImageView imageView1, imageView2, imageView3, imageView4, imageView5,
			imageView6, imageView7, imageView8, imageView9, imageView10,
			imageView11;
	CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6,
			checkBox7, checkBox8, checkBox9, checkBox10, checkBox11;
	TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10,
			txt11;

	private HashMap<String, String> infoList;
	
	private GoogleMap mapView;

	// Handler refreshMap;

	LinearLayout progres;
	Button buttonKategorije;
	
	GoogleAnalytics mGa;
	Tracker mTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_map_view);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		inicMapComponent();
		inicComponent();
		fillData();
		onCheckBoxChange();
		
		
		sendGoogleAnaliticsData("Map - screen");

	}

	
	private void inicMapComponent() {
		// TODO Auto-generated method stub
		mapView = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		
		mapView.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				//onMapWindwosClick(marker.getId(),marker.getTitle());
			}
		});
		
		
		
	}

	private void onMapWindwosClick(String id,String title){
		Intent intent = new Intent(this.getBaseContext(),
				PreviewItemPage.class);

		intent.putExtra("id", id);
		intent.putExtra("type", "company");
		intent.putExtra("language", myPrefs.getString("jezikId", "0"));

		startActivity(intent);
	}
	
	private void fillData() {
		txt1.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_NIGHT_LIFE));
		txt2.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ICE_PICE));
		txt3.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_SHOPPING));
		txt4.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_SMESTAJ));
		txt5.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_WI_FI));
		txt6.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_BANKE));
		txt7.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_GAS));
		txt8.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ZNAMENITOSTI));
		txt9.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_INSPIRACIJA));
		txt10.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KULTURNI_CENTRI));
		txt11.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_RAINBOW));
		
		buttonKategorije.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KATEGORIJE));
		
	}

	private void onCheckBoxChange() {
		// TODO Auto-generated method stub
		checkBox1
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "nightlife", 1);
					}
				});
		checkBox2
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "iceipice", 2);
					}
				});
		checkBox3
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "shopping", 3);
					}
				});
		checkBox4
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "smestaj", 4);
					}
				});
		checkBox5
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "wifi", 5);
					}
				});
		checkBox6
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "bank", 6);
					}
				});
		checkBox7
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "gas", 7);
					}
				});
		checkBox8
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "znamenitosti", 8);
					}
				});
		checkBox9
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "inspiracija", 9);
					}
				});
		checkBox10
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "kultura", 10);
					}
				});
		checkBox11
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						onCheckedCategory(isChecked, "rainbow", 11);
					}
				});

	}

	private void inicComponent() {

		txt1 = (TextView) findViewById(R.id.textViewKategorije1);
		txt2 = (TextView) findViewById(R.id.textViewKategorije2);
		txt3 = (TextView) findViewById(R.id.textViewKategorije3);
		txt4 = (TextView) findViewById(R.id.textViewKategorije4);
		txt5 = (TextView) findViewById(R.id.textViewKategorije5);
		txt6 = (TextView) findViewById(R.id.textViewKategorije6);
		txt7 = (TextView) findViewById(R.id.textViewKategorije7);
		txt8 = (TextView) findViewById(R.id.textViewKategorije8);
		txt9 = (TextView) findViewById(R.id.textViewKategorije9);
		txt10 = (TextView) findViewById(R.id.textViewKategorije10);
		txt11 = (TextView) findViewById(R.id.textViewKategorije11);

		checkBox1 = (CheckBox) findViewById(R.id.checkBoxKategorije1);
		checkBox2 = (CheckBox) findViewById(R.id.checkBoxKategorije2);
		checkBox3 = (CheckBox) findViewById(R.id.checkBoxKategorije3);
		checkBox4 = (CheckBox) findViewById(R.id.checkBoxKategorije4);
		checkBox5 = (CheckBox) findViewById(R.id.checkBoxKategorije5);
		checkBox6 = (CheckBox) findViewById(R.id.checkBoxKategorije6);
		checkBox7 = (CheckBox) findViewById(R.id.checkBoxKategorije7);
		checkBox8 = (CheckBox) findViewById(R.id.checkBoxKategorije8);
		checkBox9 = (CheckBox) findViewById(R.id.checkBoxKategorije9);
		checkBox10 = (CheckBox) findViewById(R.id.checkBoxKategorije10);
		checkBox11 = (CheckBox) findViewById(R.id.checkBoxKategorije11);

		progres = (LinearLayout) findViewById(R.id.linearLayoutProgres);
		
		buttonKategorije = (Button) findViewById(R.id.buttonKategorije);
		
	}

	private void onCheckedCategory(boolean isChecked,
			final String categoryString, final int checkeBoxId) {

		if (isChecked) {
			// checke if data is in container
			// if it is, display it
			// if it is not, start thread, load it, and display
			if (DataContainer.getInstance().getMapDataList()
					.containsKey(checkeBoxId)) {
				// Display data on map

				List<DataItem> pomDataList = DataContainer.getInstance()
						.getMapDataList().get(checkeBoxId);
				// Toast.makeText(MapPage.this, "Show " + pomDataList,
				// Toast.LENGTH_LONG).show();
				showOnMap(pomDataList, checkeBoxId);
			}

			else {

				RefreshMapAsyncTask refreshTask = new RefreshMapAsyncTask();

				refreshTask.execute(new String[] { categoryString,
						Integer.toString(checkeBoxId) });

			}

		} else {
			// Checked if data is display on map
			// if it is - remove it
			// Toast.makeText(MapPage.this, "Check data on map",
			// Toast.LENGTH_LONG)
			// .show();

			removeFromMap();
		}
	}

	private boolean getCheckeBoxState(int checkeBoxId) {

		switch (checkeBoxId) {
		case 1:

			return checkBox1.isChecked();
		case 2:

			return checkBox2.isChecked();
		case 3:

			return checkBox3.isChecked();
		case 4:

			return checkBox4.isChecked();
		case 5:

			return checkBox5.isChecked();
		case 6:

			return checkBox6.isChecked();
		case 7:

			return checkBox7.isChecked();
		case 8:

			return checkBox8.isChecked();
		case 9:

			return checkBox9.isChecked();
		case 10:

			return checkBox10.isChecked();
		case 11:

			return checkBox11.isChecked();
		default:
			return false;
		}
	}

	public class RefreshMapAsyncTask extends AsyncTask<String, Void, Message> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			progres.setVisibility(View.VISIBLE);

			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Message msg) {
			// TODO Auto-generated method stub

			progres.setVisibility(View.GONE);

			// String categoryString =
			// msg.getData().getString("categoryString");
			int checkeBoxId = msg.getData().getInt("checkeBoxId");

			List<DataItem> pomDataList = DataContainer.getInstance()
					.getMapDataList().get(checkeBoxId);

			if (getCheckeBoxState(checkeBoxId)) {
				// show data
				// Toast.makeText(MapPage.this, "Show " + pomDataList,
				// Toast.LENGTH_LONG).show();

				showOnMap(pomDataList, checkeBoxId);

			} else {
				// remove data
				// Toast.makeText(MapPage.this, "Hide " + pomDataList,
				// Toast.LENGTH_LONG).show();
				removeFromMap();
			}

			super.onPostExecute(msg);
		}

		@Override
		protected Message doInBackground(String... params) {

			Message message = new Message();

			try {
				// Load data and put it in list
				DataContainer
						.getInstance()
						.getMapDataList()
						.put(Integer.parseInt(params[1]),
								ParserMap.parse(params[0],
										myPrefs.getString("gradId", "0"),
										myPrefs.getString("jezikId", "0")));

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				// Send message to handler
				// put kategory string in bundle

				Bundle bundle = new Bundle();
				// bundle.putString("categoryString", params[0]);
				bundle.putInt("checkeBoxId", Integer.parseInt((params[1])));

				message.setData(bundle);

			}

			return message;
		}

	}

	private int getIconResourceId(int categoryId) {

		switch (categoryId) {
		case 1:

			return R.drawable.map_night_life_icon;
		case 2:

			return R.drawable.map_ice_pice_icon;
		case 3:

			return R.drawable.map_shoping_icon;
		case 4:

			return R.drawable.map_smestaj_icon;
		case 5:

			return R.drawable.map_wi_fi_icon;
		case 6:

			return R.drawable.map_bank_icon;
		case 7:

			return R.drawable.map_gas_icon;
		case 8:

			return R.drawable.map_znamenitosti_icon;
		case 9:

			return R.drawable.map_inspiracija_icon;
		case 10:

			return R.drawable.map_kultura_icon;
		case 11:

			return R.drawable.map_rainbow_icon;

		default:
			return R.drawable.map_night_life_icon;
		}
	}

	private void showOnMap(List<DataItem> pomDataList, int categoryId) {

		if (pomDataList != null) {
			infoList = new HashMap<String, String>();
			for (DataItem pomItem : pomDataList) {
				
				infoList.put(pomItem.getTitle(), pomItem.getId());
				
				mapView.addMarker(new MarkerOptions()
						.position(
								new LatLng(Double.parseDouble(pomItem.getX()),
										Double.parseDouble(pomItem.getY())))
						.title(pomItem.getTitle())
						.icon(BitmapDescriptorFactory
								.fromResource(getIconResourceId(categoryId))));

				mapView.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

					@Override
					public void onInfoWindowClick(Marker marker) {
						// TODO Auto-generated method stub
						/*Intent intent = new Intent(MapPage.this,
								PreviewItemPage.class);

						intent.putExtra("id", dataItem.getId());
						intent.putExtra("type", type);
						intent.putExtra("language", language);

						((Activity) context).startActivity(intent);*/
						Log.d("ASD", marker.getTitle());
						String markerItemId = infoList.get(marker.getTitle());
						onMapWindwosClick(markerItemId, marker.getTitle());
					}
				});

			}

			mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(
					(new LatLng(Double.parseDouble(pomDataList.get(0).getX()),
							Double.parseDouble(pomDataList.get(0).getY()))),
					12.0f));
		}

	}

	private void removeFromMap() {
		mapView.clear();

		for (int i = 1; i < 12; i++) {

			if (getCheckeBoxState(i)) {
				showOnMap(DataContainer.getInstance().getMapDataList().get(i),
						i);
			}

		}

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
}
