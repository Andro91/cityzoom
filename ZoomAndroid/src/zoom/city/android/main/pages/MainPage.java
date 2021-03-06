package zoom.city.android.main.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import zoom.city.android.main.MyAdActivity;
import zoom.city.android.main.PreviewFavouritesPage;
import zoom.city.android.main.R;
import zoom.city.android.main.adapter.FavouritesAdapter;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.container.ImageContainer;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.pages.adresar.AdresarPage;
import zoom.city.android.main.pages.cityzoom.CityZoomPage;
import zoom.city.android.main.pages.cityzoom.SmestajPage;
import zoom.city.android.main.pages.kulturnivodic.KulturniVodicPage;
import zoom.city.android.main.pages.map.MapPage;
import zoom.city.android.main.pages.nightlife.NightlifePage;
import zoom.city.android.main.pages.pice.PicePage;
import zoom.city.android.main.pages.previewitem.PreviewItemPage;
import zoom.city.android.main.pages.rainbow.RainbowPage;
import zoom.city.android.main.pages.taxisms.TaxiSMSPage;
import zoom.city.android.main.pages.wellness.WellnessAndSpaPage;
import zoom.city.android.main.parser.JsonParser;
import zoom.city.android.main.helper.Notification;

public class MainPage extends AppCompatActivity {

	TextView txtView1, txtView2, txtView3, txtView4, txtView5, txtView6,
			txtView7, txtView8, txtView9, txtView10, txtView12;
	LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, layout7,
			layout8, layout9, layout10, layout11, layout12;
	SliderLayout mDemoSlider;
	SharedPreferences myPrefs;
	Activity activity;
//	InterstitialAd interstitial;
	
	GoogleAnalytics mGa;
	Tracker mTracker;
	AlertDialog.Builder builder;
	public AlertDialog aDialog;
	
	TextView alertDialogText, alertDialogTitle;
	ImageView alertDialogImage;
	
	String notifyLink;
	Handler mHandler;
	Thread mThread;
	int notificationCounter = 0;
	Runnable notificationRunnable, transitRunnable;
	int lastTransit = 1;
	
	public boolean stopped = false;
	
	
	WebView alertDialogWebView;
	
	public ArrayList<Notification> notifications;
	
	private CharSequence mTitle;
	
	AsyncTask<String, String, JSONArray> async1 = null;
	AsyncTask<String, String, JSONArray> async2 = null;
	AsyncTask<String, String, JSONObject> async3 = null;
	
	static boolean active = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_main);
		activity = this;
		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
//		Button but = (Button) findViewById(R.id.button_favourites);
//		but.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = null;
//				intent = YouTubeStandalonePlayer.createVideoIntent(
//				          activity, Helper.YOUTUBE_DEVELOPER_KEY, "QtXby3twMmI", 0, false, true);
//				startActivityForResult(intent, 1);
//			}
//		});
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		
		inicMainDrawer();
       
		//Inicijalizacija slajdera POCETAK
		mDemoSlider = (SliderLayout)findViewById(R.id.slider);
		mDemoSlider.setDuration(8000);
		async1 = new JSONParse().execute();
		//Inicijalizacija slajdera KRAJ
		
		inicComponent();

		onComponentClick();
		
		Helper.inicMainPageActionBar(activity, toolbar);
		
		ActionBarDrawerToggle actionBarDrawerToggle;
	    DrawerLayout drawerLayout;
	    drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
		
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        
		notificationRunnable = new Runnable() {

			@Override
			public void run() {
				if(notifications.size() == 0 || notifications.size() == notificationCounter){
					return;
				}
				Notification n = notifications.get(notificationCounter);
				showAlertDialog(n);
				notificationCounter++;
			}
		};
		
		transitRunnable = new Runnable() {

			@Override
			public void run() {
				
				String transitIndex;
			
				if(lastTransit != 0){
					transitIndex = "1" + "-" + lastTransit;
				}else{
					transitIndex = "1";
				}
				lastTransit++;
				
				long timeSinceLastTransitDisplay = 0;
				if(DataContainer.androTransitTimestampList.get(transitIndex) != null){
					long timeNow = System.currentTimeMillis() / 1000L;
		        	long timeOfLastTransitDisplay = DataContainer.androTransitTimestampList.get(transitIndex);
		        	timeSinceLastTransitDisplay =  timeNow - timeOfLastTransitDisplay;
				}
				
    			Intent i = new Intent(MainPage.this, MyAdActivity.class);
    			i.putExtra("activity_code", 1);
    			i.putExtra("transit_index", transitIndex);
    			
    			if(DataContainer.androTransitImageList.get(transitIndex) != null && timeSinceLastTransitDisplay > 300){
    				mHandler.postDelayed(transitRunnable, 5000);
    				startActivity(i);
    			}
    			
			}
		};
        
        async2 = new JSONParseNotification().execute();
        
        async3 = new JSONParseTransit().execute();
        
        mHandler = new Handler();
        
		sendGoogleAnaliticsData("Main - screen");

	}
	
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        	finish();
            super.onBackPressed();
        }
    }
    

    @SuppressLint("NewApi") 
    public void showAlertDialog(Notification n){
    	if (aDialog != null) {
			aDialog.hide();
		}
    	if(notificationCounter >= notifications.size()){
    		return;
    	}
    	
		builder = new AlertDialog.Builder(MainPage.this, R.style.Theme_AppCompat_Light_Dialog_Alert);

        LayoutInflater inflater = MainPage.this.getLayoutInflater();
        
        View alertDialogView = inflater.inflate(R.layout.dialog, null);
        
		//alertDialogText = (TextView) alertDialogView.findViewById(R.id.dialog_text);
		alertDialogImage = (ImageView) alertDialogView.findViewById(R.id.dialog_image);
		alertDialogTitle = (TextView) alertDialogView.findViewById(R.id.dialog_title);
		alertDialogWebView = (WebView) alertDialogView.findViewById(R.id.dialog_webview);
		
		notifyLink = n.link;
		
		builder.setPositiveButton("MORE", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (!Helper.isBlank(notifyLink)) {
					switch(Helper.linkSwitch(notifyLink)){
						case Helper.LINK_WEB_CODE: {
							Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notifyLink));
							startActivityForResult(browserIntent, 1);
							mHandler.postDelayed(notificationRunnable, 5000);
							return;
						}
						case Helper.LINK_PHONE_CODE: {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + notifyLink));
							startActivityForResult(intent, 1);
							mHandler.postDelayed(notificationRunnable, 5000);
							return;
						}
						case Helper.LINK_EMAIL_CODE: {
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL,
									new String[] { notifyLink });
							i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
							i.putExtra(Intent.EXTRA_TEXT, "");

							startActivityForResult(
									Intent.createChooser(i, "Send mail..."), 1);
							mHandler.postDelayed(notificationRunnable, 5000);
							return;
						}
					}
					
				} else {
					dialog.cancel();
					mHandler.postDelayed(notificationRunnable, 5000);
				}
			}
		});

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	dialog.cancel();
            	mHandler.postDelayed(notificationRunnable, 5000);
            }
        });
        
        builder.setView(alertDialogView);
		//builder.setTitle(n.title);
        
		alertDialogTitle.setText(n.title);
		
		if (!Helper.isBlank(n.image)) {
			Picasso.with(MainPage.this)
				.load(n.image)
				.into(alertDialogImage);
		} else {
			alertDialogImage.setVisibility(View.GONE);
		}
		
		String htmlContent = "<html><body><div style=\"text-align: justify; padding: 0px\">" + n.text + "</div></body></html>";
		
		WebSettings settings = alertDialogWebView.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		alertDialogWebView.loadData(htmlContent, "text/html; charset=utf-8", null);
		
		//alertDialogText.setText(Html.fromHtml(n.text));
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		int width;
		int height;
		
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    	display.getSize(size);
	    	width = size.x;
			height = size.y;
	    } else {
	    	width = display.getWidth();  // deprecated
	    	height = display.getHeight();  // deprecated
	    }

		aDialog = builder.create();
		aDialog.getWindow().setLayout((int) Math.floor(width * 0.85), android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		if (active) {
			aDialog.show();
		}
    }
	
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//    	super.onCreateOptionsMenu(menu);
//    	MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main_fav, menu);
//        return true;
//    }
//    
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//        case android.R.id.home:
//            activity.finish();
//            return true;
//        case R.id.menu_action_favourites: 
//        	Intent i = new Intent(MainPage.this, PreviewFavouritesPage.class);
//			startActivity(i);
//            return true;
//        default:
//            return super.onOptionsItemSelected(item);
//        }
//    }
	
    private class JSONParse extends AsyncTask<String, String, JSONArray> {
    	
    	private volatile boolean running = true;
    	
    	@Override
        protected void onCancelled() {
            running = false;
        }
    	
        @Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    	}

		@Override
		protected JSONArray doInBackground(String... args) {
			JsonParser jParser = new JsonParser();

			if(!running){
				return null;
			} 
			
			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(
					Constant.MAIN_URL + "service/banner?seckey=zoom&country=" + myPrefs.getString("drzavaId", "0")
							+ "&city=" + myPrefs.getString("gradId", "0") + "&id=" + "homepage");
			JSONArray banner = null;
			try {
				banner = json.getJSONArray("data").getJSONObject(0).getJSONArray("banners");

			} catch (JSONException e) {
				e.printStackTrace();
				Log.d("MYTAG", "114: " + e.getMessage());
				if (banner == null) {
					try {
						banner = json.getJSONArray("data");
					} catch (JSONException e1) {
						Log.d("MYTAG", "117: " + e.getMessage());
					}
				}
			}
			return banner;
		}
       
       @Override
    	protected void onPostExecute(JSONArray result) {
    	   if(result == null){
    		   return;
    	   }
    	   //Log.d("MYTAG", "121: "+result.toString());
    	   if(result.length() == 1){
    		   mDemoSlider.setVisibility(View.GONE);
    		   ImageView imageView = (ImageView) findViewById(R.id.imageViewBigBaner1);
    		   ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
    		   progressBar.setVisibility(View.VISIBLE);
    		   imageView.setVisibility(View.VISIBLE);
    		   String image = null;
				try {
					image = result.getJSONObject(0).getString("image");
				} catch (JSONException e) {
					Log.d("MYTAG", "Line 175: Error: " + e.getMessage());
					e.printStackTrace();
				}
   			   ImageContainer.getInstance().getImageDownloader()
				.download(image, imageView);
    	   }else {
    		//mDemoSlider.removeAllSliders();
	   		for(int i = 0; i < result.length(); i++){
	         TextSliderView textSliderView = new TextSliderView(getBaseContext());
	         // initialize a SliderLayout
	         try{
		         JSONObject pom = result.getJSONObject(i);
		         textSliderView
		                 .image(pom.getString("image"))
		                 .setScaleType(BaseSliderView.ScaleType.Fit)
		                 .setOnSliderClickListener(new OnSliderClickListener() {
							
							@Override
							public void onSliderClick(BaseSliderView slider) {
								//Toast.makeText(getBaseContext(),slider.getBundle().get("companyId") + "",Toast.LENGTH_SHORT).show();
								Intent i = new Intent(getBaseContext(), PreviewItemPage.class);
								
								String companyId = slider.getBundle().get("companyId").toString();
								if(!companyId.equals("null")){
								i.putExtra("id", companyId);
								i.putExtra("type", "company");
								i.putExtra("language", myPrefs.getString("jezikId", "0"));
								
								activity.startActivity(i);
								}
							}
						});
		
		         //add your extra information
		         textSliderView.getBundle()
		                 .putString("companyId", pom.getString("id_company"));
	        }catch(JSONException ex){
	        	Log.d("MYTAG", ""+ex.getMessage());
	        }catch(NullPointerException ex){
	        	Log.d("MYTAG", ""+ex.getMessage());
	        }
	        mDemoSlider.addSlider(textSliderView);
			}
    	   }
	    	super.onPostExecute(result);
    	}
   }
    
	private class JSONParseNotification extends AsyncTask<String, String, JSONArray> {
		
		private volatile boolean running = true;
    	
    	@Override
        protected void onCancelled() {
            running = false;
        }
    	
		@Override
		protected void onPreExecute() {
			notifications = new ArrayList<Notification>();
			super.onPreExecute();
		}

		@Override
		protected JSONArray doInBackground(String... args) {
			JsonParser jParser = new JsonParser();

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formatted = simpleDateFormat.format(cal.getTime());
			
			Log.d("MYTAG",formatted);
			
			if(!running){
				return null;
			}
			
			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(Constant.MAIN_URL + "service/notification?seckey=zoom"
					+ "&country=" + myPrefs.getString("drzavaId", "0") 
					+ "&city=" + myPrefs.getString("gradId", "0")
					+ "&date=" + formatted
					+ "&language=" + myPrefs.getString("jezikId", "0"));

			JSONArray notification = null;

			try {
				notification = json.getJSONArray("data");
			} catch (JSONException e) {
				e.printStackTrace();
				Log.d("MYTAG", "321: " + e.getMessage());
			}
			
			return notification;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			if(result == null){
				return;
			}
			super.onPostExecute(result);
			for (int i = 0; i < result.length(); i++) {
				Log.d("LENGTH", ""+result.length());
				
				JSONObject jsonObject;
				try {
					jsonObject = result.getJSONObject(i);
				} catch (JSONException e) {
					continue;
				}
				try {
					notifications.add(new Notification(jsonObject.getString("title"),jsonObject.getString("text"),jsonObject.getString("image"),jsonObject.getString("link")));
				} catch (JSONException ex) {
					Log.d("MYTAG", "JSON");
					continue;
				}
			}
			
			mHandler.postDelayed(notificationRunnable,5000);
		}
	}
	
	private class JSONParseTransit extends AsyncTask<String, String, JSONObject> {
        
		private volatile boolean running = true;
    	
    	@Override
        protected void onCancelled() {
            running = false;
            Log.d("MYTAG","onCancel TransitParse");
        }
		
		@Override
    	protected void onPreExecute() {
    	    if(!myPrefs.getBoolean("MainPageFirstTime", true)){
    	    	//this.cancel(true);
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
			
			int[] transitArray = {1,2,3,4,5,8,9,14};
			for(int i : transitArray){
				if(!running || stopped){
					Log.d("MYTAG", "TRANSIT ASYNC STOPPED!");
					return null;
				}
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
					System.gc();
				}
			}
			return transitPage;
		}
       
       @Override
    	protected void onPostExecute(JSONObject result) {
    	   Editor editor = myPrefs.edit();
    	   editor.putBoolean("MainPageFirstTime", false);
    	   editor.commit();
    	   Log.d("MYTAG","mHandler.transitRunnable == onPostExecute()");
    	   //mHandler.postDelayed(transitRunnable, 8000);
    	   super.onPostExecute(result);
    	}
   }

	@Override
	protected void onResume() {
		stopped = false;
		ComponentInstance.inicGoogleBaner(this,
				myPrefs.getString("nazivGrada", ""),
				"ca-app-pub-1530516813542398/4376661865");

		fillData();
		
		Log.d("MYTAG","mHandler.transitRunnable == OnResume()");
		mHandler.postDelayed(transitRunnable, 5000);

		super.onResume();
	}


	private void onComponentClick() {
		
		layout1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// PREPORUKE

					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_PREPORUKE));
					intent.putExtra("date", "");

					// Preporuke su tipa event
					intent.putExtra("type", "event");

					startActivity(intent);
			}
		});
		layout2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// KULTURNI VODIC
					Intent intent = new Intent(MainPage.this,
							KulturniVodicPage.class);
					startActivityForResult(intent, 1);
			}
		});
		layout3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// CITY ZOOM
					Intent intent = new Intent(MainPage.this,
							CityZoomPage.class);
					startActivityForResult(intent, 1);
			}
		});
		layout4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// ADRESAR
					Intent intent = new Intent(view.getContext(),
							AdresarPage.class);
					startActivity(intent);
			}
		});
		layout5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// ICE I PICE
					Intent intent = new Intent(view.getContext(),
							PicePage.class);
					startActivityForResult(intent, 1);
			}
		});
		layout6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// NIGHTLIFE
					Intent intent = new Intent(view.getContext(),
							NightlifePage.class);
					startActivityForResult(intent, 1);
			}
		});
		layout7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// SHOPPING
					Intent intent = new Intent(view.getContext(),
							PreviewListItemPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_SHOPPING));
					startActivity(intent);
			}
		});
		layout8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TAXI SMS
					Intent intent = new Intent(view.getContext(),
							TaxiSMSPage.class);
					intent.putExtra("title", ComponentInstance
							.getTitleString(ComponentInstance.STRING_TAXI_SMS));
					startActivity(intent);
			}
		});
		layout9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// SMESTAJ
					Intent intent = new Intent(view.getContext(),
							SmestajPage.class);
					startActivityForResult(intent, 1);
			}
		});
		layout10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// WELLNESS
					Intent intent = new Intent(view.getContext(),
							WellnessAndSpaPage.class);
					startActivityForResult(intent, 1);
			}
		});
		layout11.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// RAINBOW
					Intent intent = new Intent(view.getContext(),
							RainbowPage.class);
					startActivityForResult(intent, 1);
			}
		});
		layout12.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// MAP

				Intent intent = new Intent(view.getContext(), MapPage.class);
				startActivity(intent);

			}
		});
	}

	private void fillData() {
		txtView1.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_PREPORUKE));
		txtView2.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC));
		txtView3.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_CITY_ZOOM));
		txtView4.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ADRESAR));
		txtView5.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ICE_PICE));
		txtView6.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_NIGHT_LIFE));
		txtView7.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_SHOPPING));
		txtView8.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_TAXI_SMS));
		txtView9.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_SMESTAJ));
		txtView10.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_WELNES_AND_SPA));
		txtView12.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_MAP));
	}

	private void inicComponent() {
		layout1 = (LinearLayout) findViewById(R.id.linearLayoutIcon1);
		layout2 = (LinearLayout) findViewById(R.id.linearLayoutIcon2);
		layout3 = (LinearLayout) findViewById(R.id.linearLayoutIcon3);
		layout4 = (LinearLayout) findViewById(R.id.linearLayoutIcon4);
		layout5 = (LinearLayout) findViewById(R.id.linearLayoutIcon5);
		layout6 = (LinearLayout) findViewById(R.id.linearLayoutIcon6);
		layout7 = (LinearLayout) findViewById(R.id.linearLayoutIcon7);
		layout8 = (LinearLayout) findViewById(R.id.linearLayoutIcon8);
		layout9 = (LinearLayout) findViewById(R.id.linearLayoutIcon9);
		layout10 = (LinearLayout) findViewById(R.id.linearLayoutIcon10);
		layout11 = (LinearLayout) findViewById(R.id.linearLayoutIcon11);
		layout12 = (LinearLayout) findViewById(R.id.linearLayoutIcon12);

		txtView1 = (TextView) findViewById(R.id.textViewTitle1);
		txtView2 = (TextView) findViewById(R.id.textViewTitle2);
		txtView3 = (TextView) findViewById(R.id.textViewTitle3);
		txtView4 = (TextView) findViewById(R.id.textViewTitle4);
		txtView5 = (TextView) findViewById(R.id.textViewTitle5);
		txtView6 = (TextView) findViewById(R.id.textViewTitle6);
		txtView7 = (TextView) findViewById(R.id.textViewTitle7);
		txtView8 = (TextView) findViewById(R.id.textViewTitle8);
		txtView9 = (TextView) findViewById(R.id.textViewTitle9);
		txtView10 = (TextView) findViewById(R.id.textViewTitle10);
		txtView12 = (TextView) findViewById(R.id.textViewTitle12);
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
		active = true;
		// Send a screen view when the Activity is displayed to the user.
		mTracker.send(MapBuilder.createAppView().build());
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("MYTAG", "MainPage.onPause()");
		mHandler.removeCallbacks(transitRunnable);
		mHandler.removeCallbacksAndMessages(null);
		
		active = false;
	}
	
	@Override
	protected void onStop() {
		stopped = true;
		Log.d("MYTAG", "MainPage.onStop()");
		mDemoSlider.stopAutoCycle();
		mHandler.removeCallbacksAndMessages(null);
		async1.cancel(true);
		async2.cancel(true);
		async1.cancel(true);
		super.onStop();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//mTracker.set(Fields.SCREEN_NAME, "Home Screen");
		mTracker.send(MapBuilder.createAppView().build());
		//mTracker.send(null);
		Editor editor = myPrefs.edit();
 	   	editor.putBoolean("MainPageFirstTime", true);
 	   	editor.putBoolean("CityZoomPageFirstTime", true);
 	   	editor.putBoolean("RainbowPageFirstTime", true);
 	   	editor.commit();
 	   	finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == 1 && data != null) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	int activityCode = data.getIntExtra("activity_code", 0);

	        	if(activityCode == 0) {
	        		return;
	        	}
	        	
	        	long timeSinceLastTransitDisplay = 0;
	        	if (DataContainer.androTransitTimestampList.get(activityCode) != null) {
	        		long timeNow = System.currentTimeMillis() / 1000L;
		        	long timeOfLastTransitDisplay = DataContainer.androTransitTimestampList.get(activityCode);
		        	timeSinceLastTransitDisplay =  timeNow - timeOfLastTransitDisplay;
				}
	        	
    			Intent i = new Intent(MainPage.this, MyAdActivity.class);
    			i.putExtra("activity_code", 1);
    			
    			if(DataContainer.androTransitImageList.get("1") != null && timeSinceLastTransitDisplay > 300){
    				mHandler.postDelayed(transitRunnable, 5000);
    				startActivity(i);
    			}
    			
	        }
	    }
	}

	private void inicMainDrawer(){
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.drawer_cityzoom_clickable);
        RelativeLayout r2 = (RelativeLayout) findViewById(R.id.drawer_busness_clickable);
        RelativeLayout r3 = (RelativeLayout) findViewById(R.id.drawer_favorite_clickable);
        RelativeLayout r4 = (RelativeLayout) findViewById(R.id.drawer_settings_clickable);
        TextView t1 = (TextView) findViewById(R.id.drawer_weblink_webpage);
        TextView t2 = (TextView) findViewById(R.id.drawer_weblink_about);
        TextView t3 = (TextView) findViewById(R.id.drawer_weblink_terms);
        TextView t4 = (TextView) findViewById(R.id.drawer_weblink_contact);
        
        r1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						CityZoomPage.class);
				((Activity) view.getContext()).startActivity(intent);
				//((Activity) view.getContext()).finish();
			}
		});
        r2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						AdresarPage.class);
				((Activity) view.getContext()).startActivity(intent);
				//((Activity) view.getContext()).finish();
			}
		});
        r3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						PreviewFavouritesPage.class);
				((Activity) view.getContext()).startActivity(intent);
				//((Activity) view.getContext()).finish();
			}
		});
		r4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						SettingsPage.class);
				((Activity) view.getContext()).startActivity(intent);
				((Activity) view.getContext()).finish();
			}
		});
		t1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cityzoom.info"));
				startActivity(browserIntent);
			}
		});
		t2.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cityzoom.info/staticpagefrontend/8"));
				startActivity(browserIntent);
			}
		});
		t3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cityzoom.info/staticpagefrontend/28"));
				startActivity(browserIntent);
			}
		});
		t4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cityzoom.info/contact"));
				startActivity(browserIntent);
			}
		});
		
		
		
	}


}
