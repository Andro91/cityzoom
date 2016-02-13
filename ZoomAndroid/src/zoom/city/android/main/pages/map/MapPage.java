package zoom.city.android.main.pages.map;

import java.util.ArrayList;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract.Constants;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.pages.previewitem.PreviewItemPage;
import zoom.city.android.main.parser.ParserMap;
import zoom.city.android.main.service.LocationService;

public class MapPage extends AppCompatActivity {

	SharedPreferences myPrefs;
	Thread thread;

	ImageView imageView1, imageView2, imageView3, imageView4, imageView5,
			imageView6, imageView7, imageView8, imageView9, imageView10,
			imageView11;
	CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6,
			checkBox7, checkBox8, checkBox9, checkBox10, checkBox11;
	RadioButton txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10,
			txt11;
	
	private RadioGroup radioGroup;

	LocationService locationService;
	
	private HashMap<String, String> infoList;
	
	private GoogleMap mapView;
	
	AlertDialog categoryDialog;

	
	
	// Handler refreshMap;

	LinearLayout progres;
	Button buttonKategorije;
	AlertDialog.Builder builder;
	
	GoogleAnalytics mGa;
	Tracker mTracker;
	
	List<String> itemsList = new ArrayList<String>();
	String[] items = new String[11];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_map_view);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		inicMapComponent();
		inicComponent();
		fillData();
		
		builder = new AlertDialog.Builder(this);
        builder.setTitle(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KATEGORIJE));
        
        
        
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	
                switch(item)
                {
                case 0:
                	mapView.clear();
            		onCheckedCategory(true, "nightlife", 1);
                    break;
                case 1:
                	mapView.clear();
                	onCheckedCategory(true, "iceipice", 2);
                    break;
                case 2:
                	mapView.clear();
                	onCheckedCategory(true, "shopping", 3);
                    break;
                case 3:
                	mapView.clear();
                	onCheckedCategory(true, "smestaj", 4);
                    break;
                case 4:
                	mapView.clear();
                	onCheckedCategory(true, "wifi", 5);
                    break;
                case 5:
                	mapView.clear();
                	onCheckedCategory(true, "bank", 6);
                    break;
                case 6:
                	mapView.clear();
                	onCheckedCategory(true, "gas", 7);
                    break;
                case 7:
                	mapView.clear();
                	onCheckedCategory(true, "znamenitosti", 8);
                    break;
                case 8:
                	mapView.clear();
                	onCheckedCategory(true, "inspiracija", 9);
                    break;
                case 9:
                	mapView.clear();
                	onCheckedCategory(true, "kultura", 10);
                    break;
                case 10:
                	mapView.clear();
                	onCheckedCategory(true, "rainbow", 11);
                    break;
                }
                categoryDialog.hide();  
            }
        });
        categoryDialog = builder.create();
		
		sendGoogleAnaliticsData("Map - screen");
		
		Helper.inicActionBar(MapPage.this, "MAP");
		
		buttonKategorije.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//categoryDialog = builder.create();
				categoryDialog.show();
			}
		});

	}

	
	private void inicMapComponent() {
		// TODO Auto-generated method stub
		mapView = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		

		locationService = new LocationService(MapPage.this);
		locationService.getLocation();
		
		mapView.setMyLocationEnabled(true);

	    Location location = mapView.getMyLocation();
	    LatLng myLocation = null; //new LatLng(44.8167d, 20.4667d);
	    
		if (location != null) {
	        myLocation = new LatLng(location.getLatitude(),
	                location.getLongitude());
	    } else {
	    	LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String provider = service.getBestProvider(criteria, false);
			Location lastKnownLocation = service.getLastKnownLocation(provider);
			myLocation = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
	    }
		
		mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));
		
		mapView.setPadding(0, 0, 0, 80);
		
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
		
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_NIGHT_LIFE));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ICE_PICE));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_SHOPPING));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_SMESTAJ));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_WI_FI));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_BANKE));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_GAS));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ZNAMENITOSTI));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_INSPIRACIJA));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KULTURNI_CENTRI));
    	itemsList.add(ComponentInstance
				.getTitleString(ComponentInstance.STRING_RAINBOW));
        
        for (int i = 0; i < itemsList.size(); i++) {
			items[i] = itemsList.get(i);
		}
		
		buttonKategorije.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KATEGORIJE));
		
	}


	private void inicComponent() {

//		txt1 = (TextView) findViewById(R.id.textViewKategorije1);
//		txt2 = (TextView) findViewById(R.id.textViewKategorije2);
//		txt3 = (TextView) findViewById(R.id.textViewKategorije3);
//		txt4 = (TextView) findViewById(R.id.textViewKategorije4);
//		txt5 = (TextView) findViewById(R.id.textViewKategorije5);
//		txt6 = (TextView) findViewById(R.id.textViewKategorije6);
//		txt7 = (TextView) findViewById(R.id.textViewKategorije7);
//		txt8 = (TextView) findViewById(R.id.textViewKategorije8);
//		txt9 = (TextView) findViewById(R.id.textViewKategorije9);
//		txt10 = (TextView) findViewById(R.id.textViewKategorije10);
//		txt11 = (TextView) findViewById(R.id.textViewKategorije11);
		
//		txt1 = (RadioButton) findViewById(R.id.radioKategorija1);
//		txt2 = (RadioButton) findViewById(R.id.radioKategorija2);
//		txt3 = (RadioButton) findViewById(R.id.radioKategorija3);
//		txt4 = (RadioButton) findViewById(R.id.radioKategorija4);
//		txt5 = (RadioButton) findViewById(R.id.radioKategorija5);
//		txt6 = (RadioButton) findViewById(R.id.radioKategorija6);
//		txt7 = (RadioButton) findViewById(R.id.radioKategorija7);
//		txt8 = (RadioButton) findViewById(R.id.radioKategorija8);
//		txt9 = (RadioButton) findViewById(R.id.radioKategorija9);
//		txt10 = (RadioButton) findViewById(R.id.radioKategorija10);
//		txt11 = (RadioButton) findViewById(R.id.radioKategorija11);
//
//		checkBox1 = (CheckBox) findViewById(R.id.checkBoxKategorije1);
//		checkBox2 = (CheckBox) findViewById(R.id.checkBoxKategorije2);
//		checkBox3 = (CheckBox) findViewById(R.id.checkBoxKategorije3);
//		checkBox4 = (CheckBox) findViewById(R.id.checkBoxKategorije4);
//		checkBox5 = (CheckBox) findViewById(R.id.checkBoxKategorije5);
//		checkBox6 = (CheckBox) findViewById(R.id.checkBoxKategorije6);
//		checkBox7 = (CheckBox) findViewById(R.id.checkBoxKategorije7);
//		checkBox8 = (CheckBox) findViewById(R.id.checkBoxKategorije8);
//		checkBox9 = (CheckBox) findViewById(R.id.checkBoxKategorije9);
//		checkBox10 = (CheckBox) findViewById(R.id.checkBoxKategorije10);
//		checkBox11 = (CheckBox) findViewById(R.id.checkBoxKategorije11);

		progres = (LinearLayout) findViewById(R.id.linearLayoutProgres);
		
		buttonKategorije = (Button) findViewById(R.id.buttonKategorije);
		
	}

	private void onCheckedCategory(boolean isChecked,
			final String categoryString, final int checkeBoxId) {

		if (isChecked) {
			// checke if data is in container
			// if it is, display it
			// if it is not, start thread, load it, and display
			if (DataContainer.getInstance().getMapDataList().containsKey(checkeBoxId)) {
				// Display data on map
				List<DataItem> pomDataList = DataContainer.getInstance()
						.getMapDataList().get(checkeBoxId);
				// Toast.makeText(MapPage.this, "Show " + pomDataList,
				// Toast.LENGTH_LONG).show();
				showOnMap(pomDataList, checkeBoxId);
			} else {
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

	public class RefreshMapAsyncTask extends AsyncTask<String, Void, Message> {

		@Override
		protected void onPreExecute() {
			progres.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Message msg) {
			progres.setVisibility(View.GONE);

			// String categoryString =
			// msg.getData().getString("categoryString");
			int checkeBoxId = msg.getData().getInt("checkeBoxId");

			List<DataItem> pomDataList = DataContainer.getInstance()
					.getMapDataList().get(checkeBoxId);
			
			showOnMap(pomDataList, checkeBoxId);
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

	private void showOnMap(List<DataItem> pomDataList, int categoryId) {

		if (pomDataList != null) {
			infoList = new HashMap<String, String>();
			for (DataItem pomItem : pomDataList) {
				
				infoList.put(pomItem.getTitle(), pomItem.getId());
				
				mapView.addMarker(new MarkerOptions()
						.position(
								new LatLng(Double.parseDouble(pomItem.getX()),
										Double.parseDouble(pomItem.getY())))
						.title(pomItem.getTitle()));
						//.icon(BitmapDescriptorFactory
							//	.fromResource(getIconResourceId(categoryId))));

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
			try{
			mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(
					(new LatLng(Double.parseDouble(pomDataList.get(0).getX()),
							Double.parseDouble(pomDataList.get(0).getY()))),
					12.0f));
			}catch(IndexOutOfBoundsException indexException){
				Log.d("MYTAG", "index out of bounds!!!");
			}
		}

	}

	private void removeFromMap() {
		mapView.clear();
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
