package zoom.city.android.main.pages.previewitem;

import java.util.Timer;
import java.util.TimerTask;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import zoom.city.android.main.R;
import zoom.city.android.main.adapter.AndroSliderAdapter;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.container.ImageContainer;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.database.DatabaseHandler;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.imageview.DinamicImageView;
import zoom.city.android.main.parser.ParserDistance;
import zoom.city.android.main.parser.ParserItem;
import zoom.city.android.main.service.LocationService;


//@SuppressLint("NewApi")
public class PreviewItemPage extends AppCompatActivity {

	private String id, language, type, title, category;
	private Thread mainThread;
	private Handler handler;
	private DataItem dataItem;
	LocationService locationService;

	TextView txtTitle, txtGenre, txtAuthor, txtDescription, txtTime,
			txtCompany, txtMicrolocation, txtCategory, txtWeb, txtPhone1, txtPhone2, txtFax, txtAdrress, txtStreet,
			txtCity, txtEmail, txtDistance, txtFacebook, txtTimeDate;
	DinamicImageView imageView, imageView2;
	ImageView buttonDirection, buttonShare, buttonLike, buttonUp, butPhone,
			butFacebook, butWebSite, butMail, butFavourite, butPlus;
	View separatorAddress, separatorPhone, separatorEmail, separatorWeb;
	Button mapButton, webButton;
	GoogleMap mapView;
	SliderLayout mDemoSlider;
	SliderLayout mDemoSlider2 = null;
	String[] mDemoSlider2Content;
	Activity activity;
	SharedPreferences myPrefs;
	FrameLayout flphone, flmail, flfacebook, flwebsite;
	String dist = "No GPS!";
	//AndroSlider items
	AndroSliderAdapter androSliderAdapter;
	int currentPage;
	public Timer swipeTimer;
	//AndroSlider items
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_preview_item_first_page);
		activity = this;
		locationService = new LocationService(PreviewItemPage.this);
		locationService.getLocation();
		
		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		//AndroSlider initialization
		DataContainer.androSliderUrlList.clear();
		androSliderAdapter = new AndroSliderAdapter(this);
		
		Log.d("MYTAG", "PreviewItemPage");
		
		getData();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (myPrefs == null) {
			myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		}
		if (ComponentInstance.getMyPrefs() == null) {
			ComponentInstance.setMyPrefs(myPrefs);
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	
	    }
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	    try {
//	    	WebView webView = (WebView) this.findViewById(R.id.webview);
//	        Class.forName("android.webkit.WebView")
//	                .getMethod("onPause", (Class[]) null)
//	                            .invoke(webView, (Object[]) null);
//	        DataContainer.androSliderUrlList.clear();
//			androSliderAdapter.fetchData();
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
    @Override
	protected void onStop() {
		super.onStop();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	if (type.equalsIgnoreCase("company")) {
			if (dataItem.getPreviewtype().equalsIgnoreCase("0")) {
//				Log.d("MENU", "1");
				inflater.inflate(R.menu.item_list_menu, menu);
			}else{
//				Log.d("MENU", "2");
//				Log.d("MENU", "2 " + dataItem.getPreviewtype());
				inflater.inflate(R.menu.action_menu, menu);
			}
    	}else{
//    		Log.d("MENU", "3");
			inflater.inflate(R.menu.action_menu, menu);
		}
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            activity.finish();
            return true;
        case R.id.menu_action_directions: {
        	if(!locationService.canGetLocation()){
				Toast.makeText(PreviewItemPage.this, "No GPS signal!", Toast.LENGTH_LONG).show();
			}
			
			String longMy, latMy, longTo, latTo;

			longMy = Double.toString(locationService.getLongitude());
			latMy = Double.toString(locationService.getLatitude());

			longTo = dataItem.getY();
			latTo = dataItem.getX();

			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?saddr=" + latMy
							+ "," + longMy + "&daddr=" + latTo + ","
							+ longTo));
			startActivityForResult(intent, 1);
            return true; }
        case R.id.menu_action_share: {
        	Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					dataItem.getTitle() + ": " + dataItem.getShare());

			startActivityForResult(Intent.createChooser(sharingIntent,
					dataItem.getTitle()), 1);
            return true; }
        default:
            return super.onOptionsItemSelected(item);
        }
    }	

	private void getData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			id = extras.getString("id");
			language = extras.getString("language");
			type = extras.getString("type");
			title = extras.getString("title");
			if (extras.getString("category") != null) {
				category = extras.getString("category");
			}
			Log.d("MYTAG", "Intent extra: title = " + title);
			Log.d("MYTAG", "Intent extra: type = " + type);
			Log.d("MYTAG", "Intent extra: language = " + language);
			Log.d("MYTAG", "Intent extra: id = " + id);
		}
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				// PREVIEW FOR EVENT
				if (type.equalsIgnoreCase("event")) {
					Log.d("MYTAG", "event data type");
					setContentView(R.layout.page_preview_item_event);

					inicComponent(type, "");
					fillData(type, "");
					Helper.inicActionBar(PreviewItemPage.this, title);
				}

				// PREVIEW FOR COMPANY
				// FOUR TYPE OF PREVIEW
				else if (type.equalsIgnoreCase("company")) {

					if (dataItem.getPreviewtype().equalsIgnoreCase("0")) {
						setContentView(R.layout.page_preview_item_company_0);
					} else if (dataItem.getPreviewtype().equalsIgnoreCase("1")) {
						setContentView(R.layout.page_preview_item_company_1);
					} else if (dataItem.getPreviewtype().equalsIgnoreCase("2")) {
						setContentView(R.layout.page_preview_item_company_2);
					} else if (dataItem.getPreviewtype().equalsIgnoreCase("3")) {
						setContentView(R.layout.page_preview_item_company_3);
					}

					inicComponent(type, dataItem.getPreviewtype());
					fillData(type, dataItem.getPreviewtype());
					Helper.inicActionBar(PreviewItemPage.this, title);
				}
				// ERROR
				else {

					Toast.makeText(PreviewItemPage.this, "Network error.",
							Toast.LENGTH_LONG).show();
					finish();
				}

			}

		};

		mainThread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				try {
					Log.d("MYTAG", "Thread");
					dataItem = ParserItem.parseSendData(id, language, type);
					dist = ParserDistance.uzmiDistancu(Double.toString(locationService.getLatitude()),
							Double.toString(locationService.getLongitude()),dataItem.getX(),dataItem.getY());
					if ((dist=="")||(dist == null)) dist = "No GPS!";

				} catch (Exception e) {
					Log.d("ERROR", "Line 290 " + e.getMessage());
				} finally {
					handler.sendEmptyMessage(0);
				}
			}

		};

		mainThread.start();
	}

	public void inicComponent(String type, String previewType) {

		if (type.equalsIgnoreCase("event")) {

			txtTitle = (TextView) findViewById(R.id.textViewTitle);
			txtDistance = (TextView) findViewById(R.id.textDistanca);
			//imageView = (DinamicImageView) findViewById(R.id.imageView);
			txtGenre = (TextView) findViewById(R.id.textViewGenre);
			//txtAuthor = (TextView) findViewById(R.id.textViewAuthor);
			txtCategory = (TextView) findViewById(R.id.textViewCategory);
			txtDescription = (TextView) findViewById(R.id.textViewDescription);
			txtTime = (TextView) findViewById(R.id.textViewTime);
			txtCompany = (TextView) findViewById(R.id.textViewCompany);
			txtMicrolocation = (TextView) findViewById(R.id.textViewMikrolokacija);
			txtTimeDate = (TextView) findViewById(R.id.textViewTimeDate);
			webButton = (Button) findViewById(R.id.textViewWebButton);
			butFavourite = (ImageView) findViewById(R.id.favourite_button);
			butPlus = (ImageView) findViewById(R.id.plus_button);
			DatabaseHandler db = new DatabaseHandler(activity);
			if(db.checkFavouriteStatus(dataItem.getTitle())){
				butFavourite.setImageResource(R.drawable.favourite_active);
			}else{
				butFavourite.setImageResource(R.drawable.favourite_inactive);
			}

			//inicBottomButton();
		}

		else if (type.equalsIgnoreCase("company")) {

			if (previewType.equalsIgnoreCase("0")) {

				Log.d("MYTAG", "previewType: 0");
				
				txtTitle = (TextView) findViewById(R.id.textViewTitle);
				txtPhone1 = (TextView) findViewById(R.id.textViewPhone1);
				txtPhone1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone_icon_red, 0, 0, 0);
				txtPhone1.setCompoundDrawablePadding(20);
				separatorPhone = findViewById(R.id.separatorPhone);
				txtEmail = (TextView) findViewById(R.id.textViewEmail);
				txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_icon_red, 0, 0, 0);
				txtEmail.setCompoundDrawablePadding(20);
				separatorEmail = findViewById(R.id.separatorEmail);
				txtWeb = (TextView) findViewById(R.id.textViewWeb);
				txtWeb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.website_icon_red, 0, 0, 0);
				txtWeb.setCompoundDrawablePadding(20);
				separatorWeb = findViewById(R.id.separatorWeb);
				txtAdrress = (TextView) findViewById(R.id.textViewAdrress);
				txtAdrress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.distance_icon_red, 0, 0, 0);
				txtAdrress.setCompoundDrawablePadding(20);
				separatorAddress = findViewById(R.id.separatorAddress);
				txtCity = (TextView) findViewById(R.id.textViewCity);
				txtCity.setVisibility(View.GONE);

			} else if (previewType.equalsIgnoreCase("1")) {

				Log.d("MYTAG", "previewType: 1");
				
				txtTitle = (TextView) findViewById(R.id.textViewTitle);
				txtDistance = (TextView) findViewById(R.id.textDistanca);
				txtPhone1 = (TextView) findViewById(R.id.textViewPhone1);
				txtPhone1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone_icon_red, 0, 0, 0);
				txtPhone1.setCompoundDrawablePadding(20);
				txtPhone2 = (TextView) findViewById(R.id.textViewPhone2);
//				txtPhone2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone, 0, 0, 0);
				txtPhone2.setCompoundDrawablePadding(20);
				txtFax = (TextView) findViewById(R.id.textViewFax);
				txtFax.setCompoundDrawablePadding(20);
				txtEmail = (TextView) findViewById(R.id.textViewEmail);
				txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_icon_red, 0, 0, 0);
				txtEmail.setCompoundDrawablePadding(20);
				txtWeb = (TextView) findViewById(R.id.textViewWeb);
				txtWeb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.website_icon_red, 0, 0, 0);
				txtWeb.setCompoundDrawablePadding(20);
				txtAdrress = (TextView) findViewById(R.id.textViewAdrress);
				txtAdrress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.distance_icon_red, 0, 0, 0);
				txtAdrress.setCompoundDrawablePadding(20);
				txtCity = (TextView) findViewById(R.id.textViewCity);
				txtCity.setVisibility(View.GONE);
				txtFacebook = (TextView) findViewById(R.id.textViewFacebook);
				txtFacebook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.facebook_icon_red, 0, 0, 0);
				txtFacebook.setCompoundDrawablePadding(20);
				butPhone = (ImageView) findViewById(R.id.phone_button);
				butFacebook = (ImageView) findViewById(R.id.facebook_button);
				butMail = (ImageView) findViewById(R.id.mail_button);
				butWebSite = (ImageView) findViewById(R.id.website_button);
				flphone = (FrameLayout) findViewById(R.id.flphone);
				flmail = (FrameLayout) findViewById(R.id.flmail);
				flwebsite = (FrameLayout) findViewById(R.id.flwebsite);
				flfacebook = (FrameLayout) findViewById(R.id.flfacebook);
				separatorAddress = findViewById(R.id.separatorAddress);
				separatorPhone = findViewById(R.id.separatorPhone);
				separatorEmail = findViewById(R.id.separatorEmail);
				separatorWeb = findViewById(R.id.separatorWeb);
				
				mDemoSlider = (SliderLayout) findViewById(R.id.slider);
				mDemoSlider.setDuration(7000);
				
				butFavourite = (ImageView) findViewById(R.id.favourite_button);
				DatabaseHandler db = new DatabaseHandler(activity);
				if(db.checkFavouriteStatus(dataItem.getTitle())){
					butFavourite.setImageResource(R.drawable.favourite_active);
				}else{
					butFavourite.setImageResource(R.drawable.favourite_inactive);
				}
				
				mapButton = (Button) findViewById(R.id.buttonMap);

				mapView = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();

				//inicBottomButton();

			} else if (previewType.equalsIgnoreCase("2")) {

				Log.d("MYTAG", "previewType: 2");
				
				txtTitle = (TextView) findViewById(R.id.textViewTitle);
				txtDistance = (TextView) findViewById(R.id.textDistanca);
				txtPhone1 = (TextView) findViewById(R.id.textViewPhone1);
				txtPhone1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone_icon_red, 0, 0, 0);
				txtPhone1.setCompoundDrawablePadding(20);
				txtPhone2 = (TextView) findViewById(R.id.textViewPhone2);
//				txtPhone2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone, 0, 0, 0);
				txtPhone2.setCompoundDrawablePadding(20);
				txtFax = (TextView) findViewById(R.id.textViewFax);
				txtFax.setCompoundDrawablePadding(20);
				txtEmail = (TextView) findViewById(R.id.textViewEmail);
				txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_icon_red, 0, 0, 0);
				txtEmail.setCompoundDrawablePadding(20);
				txtWeb = (TextView) findViewById(R.id.textViewWeb);
				txtWeb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.website_icon_red, 0, 0, 0);
				txtWeb.setCompoundDrawablePadding(20);
				txtAdrress = (TextView) findViewById(R.id.textViewAdrress);
				txtAdrress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.distance_icon_red, 0, 0, 0);
				txtAdrress.setCompoundDrawablePadding(20);
				txtCity = (TextView) findViewById(R.id.textViewCity);
				txtCity.setVisibility(View.GONE);
				txtFacebook = (TextView) findViewById(R.id.textViewFacebook);
				txtFacebook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.facebook_icon_red, 0, 0, 0);
				txtFacebook.setCompoundDrawablePadding(20);
				butPhone = (ImageView) findViewById(R.id.phone_button);
				butFacebook = (ImageView) findViewById(R.id.facebook_button);
				butMail = (ImageView) findViewById(R.id.mail_button);
				butWebSite = (ImageView) findViewById(R.id.website_button);
				flphone = (FrameLayout) findViewById(R.id.flphone);
				flmail = (FrameLayout) findViewById(R.id.flmail);
				flwebsite = (FrameLayout) findViewById(R.id.flwebsite);
				flfacebook = (FrameLayout) findViewById(R.id.flfacebook);

				separatorAddress = findViewById(R.id.separatorAddress);
				separatorPhone = findViewById(R.id.separatorPhone);
				separatorEmail = findViewById(R.id.separatorEmail);
				separatorWeb = findViewById(R.id.separatorWeb);
				
				mDemoSlider = (SliderLayout) findViewById(R.id.slider);
				mDemoSlider.setDuration(7000);
				
				butFavourite = (ImageView) findViewById(R.id.favourite_button);
				DatabaseHandler db = new DatabaseHandler(activity);
				if(db.checkFavouriteStatus(dataItem.getTitle())){
					butFavourite.setImageResource(R.drawable.favourite_active);
				}else{
					butFavourite.setImageResource(R.drawable.favourite_inactive);
				}

				imageView = (DinamicImageView) findViewById(R.id.imageView1);

				txtDescription = (TextView) findViewById(R.id.textViewDescription);

				mapButton = (Button) findViewById(R.id.buttonMap);

				mapView = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();

				//inicBottomButton();

			} else if (previewType.equalsIgnoreCase("3")) {

				Log.d("MYTAG", "previewType: 3");
				
				txtTitle = (TextView) findViewById(R.id.textViewTitle);
				txtDistance = (TextView) findViewById(R.id.textDistanca);
				txtPhone1 = (TextView) findViewById(R.id.textViewPhone1);
				txtPhone1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone_icon_red, 0, 0, 0);
				txtPhone1.setCompoundDrawablePadding(20);
				txtPhone2 = (TextView) findViewById(R.id.textViewPhone2);
//				txtPhone2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone, 0, 0, 0);
				txtPhone2.setCompoundDrawablePadding(20);
				txtFax = (TextView) findViewById(R.id.textViewFax);
				txtFax.setCompoundDrawablePadding(20);
				txtEmail = (TextView) findViewById(R.id.textViewEmail);
				txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_icon_red, 0, 0, 0);
				txtEmail.setCompoundDrawablePadding(20);
				txtWeb = (TextView) findViewById(R.id.textViewWeb);
				txtWeb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.website_icon_red, 0, 0, 0);
				txtWeb.setCompoundDrawablePadding(20);
				txtAdrress = (TextView) findViewById(R.id.textViewAdrress);
				txtAdrress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.distance_icon_red, 0, 0, 0);
				txtAdrress.setCompoundDrawablePadding(20);
				txtCity = (TextView) findViewById(R.id.textViewCity);
				txtCity.setVisibility(View.GONE);
				txtFacebook = (TextView) findViewById(R.id.textViewFacebook);
				txtFacebook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.facebook_icon_red, 0, 0, 0);
				txtFacebook.setCompoundDrawablePadding(20);
				butPhone = (ImageView) findViewById(R.id.phone_button);
				butFacebook = (ImageView) findViewById(R.id.facebook_button);
				butMail = (ImageView) findViewById(R.id.mail_button);
				butWebSite = (ImageView) findViewById(R.id.website_button);
				flphone = (FrameLayout) findViewById(R.id.flphone);
				flmail = (FrameLayout) findViewById(R.id.flmail);
				flwebsite = (FrameLayout) findViewById(R.id.flwebsite);
				flfacebook = (FrameLayout) findViewById(R.id.flfacebook);

				separatorAddress = findViewById(R.id.separatorAddress);
				separatorPhone = findViewById(R.id.separatorPhone);
				separatorEmail = findViewById(R.id.separatorEmail);
				separatorWeb = findViewById(R.id.separatorWeb);
				
				mDemoSlider = (SliderLayout) findViewById(R.id.slider);
				mDemoSlider.setDuration(7000);
				
				butFavourite = (ImageView) findViewById(R.id.favourite_button);
				DatabaseHandler db = new DatabaseHandler(activity);
				if(db.checkFavouriteStatus(dataItem.getTitle())){
					butFavourite.setImageResource(R.drawable.favourite_active);
				}else{
					butFavourite.setImageResource(R.drawable.favourite_inactive);
				}
						
				imageView = (DinamicImageView) findViewById(R.id.imageView1);
				imageView2 = (DinamicImageView) findViewById(R.id.imageView2);

				txtDescription = (TextView) findViewById(R.id.textViewDescription);

				mapButton = (Button) findViewById(R.id.buttonMap);

				mapView = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();

				//inicBottomButton();

			}

		}

	}

//	public void inicActionBar() {
//		Toolbar toolbar = null;
//		if(findViewById(R.id.toolbar) != null){
//			toolbar = (Toolbar) findViewById(R.id.toolbar); 
//			if(!Helper.isBlank(title)){
//				((TextView)toolbar.findViewById(R.id.toolbar_title)).setText(title);
//			}else{
//				((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("");
//			}
//			
//		}else{
//			Log.d("MYTAG","Null toolbar");
//		}
//		setSupportActionBar(toolbar); 
//		
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getSupportActionBar().setDisplayShowTitleEnabled(false);
//		try{
//			ActionBar actionBar = getSupportActionBar();
//			
//			actionBar.setDisplayHomeAsUpEnabled(true);
//			actionBar.setDisplayShowHomeEnabled(false);
//			actionBar.setDisplayShowTitleEnabled(true);
//			actionBar.setDisplayUseLogoEnabled(false);
//			if(!Helper.isBlank(title)){
//				getSupportActionBar().setTitle(title);
//			}else{
//				getSupportActionBar().setTitle("BACK");
//			}
//		}catch(Exception ex){
//			Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
//		}
//	}
	
	public void inicAndroSlider(final AndroSliderAdapter androSliderAdapter){
		//androSliderAdapter.notifyDataSetChanged();
		final ViewPager viewPager = (ViewPager) findViewById(R.id.andro_slider_viewPager);
        viewPager.setAdapter(androSliderAdapter);
        viewPager.setOffscreenPageLimit(5);
        
		CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.circle_page_indicator);
		indicator.setViewPager(viewPager);

		final float density = getResources().getDisplayMetrics().density;
		indicator.setBackgroundColor(0x22000000);
		indicator.setRadius(3 * density);
		indicator.setPageColor(0x00000000);
		indicator.setFillColor(0xFFFFFFFF);
		indicator.setStrokeColor(0x22000000);
		//indicator.setStrokeWidth(1.3f * density);
		
		final Handler handler234 = new Handler();

		final Runnable Update = new Runnable() {
			public void run() {
				if (currentPage == DataContainer.androSliderUrlList.size()) {
					currentPage = 0;
				}
				androSliderAdapter.notifyDataSetChanged();
				viewPager.setCurrentItem(currentPage++, true);
			}
		};
		
		swipeTimer = new Timer();
		swipeTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler234.post(Update);
			}
		}, 7000, 10000);
	}
	
	public void fillData(String type, String previewType) {

		if (type.equalsIgnoreCase("event")) {

			txtTitle.setText(dataItem.getTitle());
			txtDistance.setText(dist);
//			ImageContainer.getInstance().getImageDownloader()
//					.download(dataItem.getImage(), imageView);
			
			if(!Helper.isBlank(dataItem.getGenre()) && !Helper.isBlank(dataItem.getAuthor())){
				//txtGenre.setVisibility(View.GONE);
				txtGenre.setText(dataItem.getGenre() + " / " + dataItem.getAuthor());
			}
			else if(!Helper.isBlank(dataItem.getGenre())){
				txtGenre.setText(dataItem.getGenre());
			}else if(!Helper.isBlank(dataItem.getAuthor())){
				txtGenre.setText(dataItem.getAuthor());
			}else{
				txtGenre.setVisibility(View.GONE);
			}
			
			Log.d("MYTAG", "getCategory() " + getIntent().getStringExtra("category"));
			if(getIntent().getStringExtra("category") == null){
				txtCategory.setVisibility(View.GONE);
			}
			else{
				txtCategory.setText(getIntent().getStringExtra("category"));
			}
			
			if(dataItem.getDescription().equalsIgnoreCase(" ")){
				txtDescription.setVisibility(View.GONE);
			}
			else{
				txtDescription.setText(dataItem.getDescription());
			}
			
			if(Helper.isBlank(dataItem.getDate())){
				txtTimeDate.setVisibility(View.GONE);
			}
			else{
				txtTimeDate.setText(dataItem.getDate());
			}
			
			if(dataItem.getTime().equalsIgnoreCase(" ")){
				txtTime.setVisibility(View.GONE);
			}
			else{
				txtTime.setText(dataItem.getTime());
			}
			
			if(dataItem.getCompany().equalsIgnoreCase(" ")){
				txtCompany.setVisibility(View.GONE);
			}
			else{
				txtCompany.setText(dataItem.getCompany());
			}
			
			if(Helper.isBlank(dataItem.getCompanyId())){
				webButton.setVisibility(View.GONE);
			}
			
			if(Helper.isBlank(dataItem.getMicrolocation())){
				txtMicrolocation.setVisibility(View.GONE);
			}else{
				txtMicrolocation.setText(dataItem.getMicrolocation());
			}
			
			DataContainer.androSliderUrlList.clear();
			
//			if(!dataItem.getImage2().equalsIgnoreCase(" ")){
//				DataContainer.androSliderUrlList.add(dataItem.getImage2());
//			} 
			
			if(dataItem.getSlider() != null){
				for (String s : dataItem.getSlider()) {
					DataContainer.androSliderUrlList.add(s);
				}
			}
			if(!Helper.isBlank(dataItem.getImage())){
				if(!DataContainer.androSliderUrlList.contains(dataItem.getImage())){
					DataContainer.androSliderUrlList.add(dataItem.getImage());
				}
			}
			if(!Helper.isBlank(dataItem.getWeb())){
				Log.d("MYTAG",dataItem.getWeb());
				DataContainer.androSliderUrlList.add(dataItem.getWeb());
			}
			
			if(Helper.isBlank(dataItem.getOfficialWebpage())){
				butPlus.setVisibility(View.GONE);
			}
			
			
			androSliderAdapter.fetchData();
			inicAndroSlider(androSliderAdapter);
			
			butFavourite.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DatabaseHandler db = new DatabaseHandler(activity);
					Log.d("MYTAG", "check Favourite: " + db.checkFavouriteStatus(dataItem.getId()));
					if(!db.checkFavouriteStatus(dataItem.getTitle())){
						Log.d("MYTAG", "Add Favourite");
						
						dataItem.setPreviewtype("event");
						dataItem.setId(id);
						dataItem.setCategory(category);
						db.addFavourite(dataItem);
						butFavourite.setImageResource(R.drawable.favourite_active);
					}else{
						butFavourite.setImageResource(R.drawable.favourite_inactive);
						Log.d("MYTAG", "Delete Favourite");
						db.deleteFavourite(dataItem);
					}
					
					Log.d("MYTAG", "ALL Favourites");
					
					for(DataItem item : db.getAllFavourites()){
						Log.d("MYTAG", "Favourite Record: " + item.getTitle());
					}
				}
			});
			
			// Active txtView
			webButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
//					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
//							.parse(dataItem.getWeb()));
//					startActivityForResult(browserIntent, 1);
					Intent intent = new Intent(v.getContext(),
							PreviewItemPage.class);

					intent.putExtra("id", dataItem.getCompanyId());
					intent.putExtra("type", "company");
					intent.putExtra("language", language);

					startActivity(intent);
				}
			});

			butPlus.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(dataItem.getOfficialWebpage()));
					startActivityForResult(browserIntent, 1);
					}
			});
			
			txtCompany.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(v.getContext(),
							PreviewItemPage.class);

					intent.putExtra("id", dataItem.getCompanyId());
					intent.putExtra("type", "company");
					intent.putExtra("language", language);

					startActivity(intent);
				}
			});
			
			

		}

		else if (type.equalsIgnoreCase("company")) {

			if (previewType.equalsIgnoreCase("0")) {

				txtTitle.setText(dataItem.getTitle());
				//flfacebook.setVisibility(View.GONE);
				
				
				if(dataItem.getPhone1().equalsIgnoreCase(" ")){
					txtPhone1.setVisibility(View.GONE);
				}
				else{
					txtPhone1.setText(dataItem.getPhone1());
					txtPhone1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getPhone1()));
							startActivityForResult(intent, 1);
						}
					});
				}
				
				if(dataItem.getEmail().equalsIgnoreCase(" ")){
					txtEmail.setVisibility(View.GONE);
//					flmail.setVisibility(View.GONE);
//					separatorEmail.setVisibility(View.GONE);
				}
				else{
					txtEmail.setText(dataItem.getEmail());
					txtEmail.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL,
									new String[] { dataItem.getEmail() });
							i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
							i.putExtra(Intent.EXTRA_TEXT, "");

							startActivityForResult(
									Intent.createChooser(i, "Send mail..."), 1);
							
						}
					});
				}
				
				if(dataItem.getWeb().equalsIgnoreCase(" ")){
					txtWeb.setVisibility(View.GONE);
//					flwebsite.setVisibility(View.GONE);
				}
				else{
					txtWeb.setText(dataItem.getWeb());
					txtWeb.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getWeb()));
							startActivityForResult(browserIntent, 1);
						}
					});
				}
				
				Log.d("MYTAG", "Zona: " + dataItem.getZona());
				
				if(dataItem.getStreet().equalsIgnoreCase(" ")){
					txtAdrress.setVisibility(View.GONE);
				}
				else if(!Helper.isBlank(dataItem.getZona())){
					txtAdrress.setText(dataItem.getZona() + ", " + dataItem.getStreet());
				}else{
					txtAdrress.setText(dataItem.getStreet());
				}
				
				if(dataItem.getCity().equalsIgnoreCase(" ")){
					txtCity.setVisibility(View.GONE);
				}
				else{
					txtCity.setText(dataItem.getCity());
				}

			} else if (previewType.equalsIgnoreCase("1")) {

				txtTitle.setText(dataItem.getTitle());
				txtDistance.setText(dist);
				
				
				if(dataItem.getPhone1().equalsIgnoreCase(" ")){
					txtPhone1.setVisibility(View.GONE);
					flphone.setVisibility(View.GONE);
					separatorPhone.setVisibility(View.GONE);
				}
				else{
					txtPhone1.setText(dataItem.getPhone1());
					txtPhone1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getPhone1()));
							startActivityForResult(intent, 1);
						}
					});
					butPhone.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							final Dialog dialog = new Dialog(PreviewItemPage.this);
						    dialog.setContentView(R.layout.custom_dialog);
						    dialog.setTitle("Call");
						    dialog.setCancelable(true);
						    // set up radiobutton
						    Button rd1 = (Button) dialog.findViewById(R.id.rd_1);
						    Button rd2 = (Button) dialog.findViewById(R.id.rd_2);
						    Button bCancel = (Button) dialog.findViewById(R.id.b_cancel);
						    
						    rd1.setText(txtPhone1.getText());
						    
						    if(dataItem.getPhone2().equalsIgnoreCase(" ")){
								rd2.setVisibility(View.GONE);
							}
							else{
								rd2.setText(txtPhone2.getText());
								
								rd2.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(Intent.ACTION_CALL, Uri
												.parse("tel:" + dataItem.getPhone2()));
										startActivityForResult(intent, 1);
										dialog.cancel();
									}
								});
							}	
						    
						    rd1.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(Intent.ACTION_CALL, Uri
											.parse("tel:" + dataItem.getPhone1()));
									startActivityForResult(intent, 1);
									dialog.cancel();
								}
							});
						    
						    bCancel.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});

						    // now that the dialog is set up, it's time to show it
						    dialog.show();
							
						}
					});
				}
				
				if(dataItem.getPhone2().equalsIgnoreCase(" ")){
					txtPhone2.setVisibility(View.GONE);
				}
				else{
					txtPhone2.setText(dataItem.getPhone2());
					txtPhone2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getPhone2()));
							startActivityForResult(intent, 1);
						}
					});
				}
				
				if(Helper.isBlank(dataItem.getFax())){
					txtFax.setVisibility(View.GONE);
				}else{
					txtFax.setText(dataItem.getFax());
					txtFax.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getFax()));
							startActivityForResult(intent, 1);
						}
					});
				}
				
				if(dataItem.getEmail().equalsIgnoreCase(" ")){
					txtEmail.setVisibility(View.GONE);
					flmail.setVisibility(View.GONE);
					separatorEmail.setVisibility(View.GONE);
				}
				else{
					txtEmail.setText(dataItem.getEmail());
					txtEmail.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL,
									new String[] { dataItem.getEmail() });
							i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
							i.putExtra(Intent.EXTRA_TEXT, "");

							startActivityForResult(
									Intent.createChooser(i, "Send mail..."), 1);
							
						}
					});
					butMail.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL,
									new String[] { dataItem.getEmail() });
							i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
							i.putExtra(Intent.EXTRA_TEXT, "");

							startActivityForResult(
									Intent.createChooser(i, "Send mail..."), 1);
							
						}
					});
				}
				
				if(dataItem.getWeb().equalsIgnoreCase(" ")){
					txtWeb.setVisibility(View.GONE);
					flwebsite.setVisibility(View.GONE);
					separatorWeb.setVisibility(View.GONE);
				}
				else{
					txtWeb.setText(dataItem.getWeb());
					txtWeb.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getWeb()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
					butWebSite.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getWeb()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
				}
				
				if(Helper.isBlank(dataItem.getShare())){
					txtFacebook.setVisibility(View.GONE);
					flfacebook.setVisibility(View.GONE);
				}
				else{
					txtFacebook.setText(dataItem.getShare());
					txtFacebook.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
//							TODO Solve facebook intent
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getShare()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
					butFacebook.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
//							TODO Solve facebook intent
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getShare()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
				}
				
				if(dataItem.getStreet().equalsIgnoreCase(" ")){
					txtAdrress.setVisibility(View.GONE);
					separatorAddress.setVisibility(View.GONE);
				}
				else if(!Helper.isBlank(dataItem.getZona())){
					txtAdrress.setText(dataItem.getZona() + ", " + dataItem.getStreet());
				}else{
					txtAdrress.setText(dataItem.getStreet());
				}
				
				
				if(dataItem.getCity().equalsIgnoreCase(" ")){
					txtCity.setVisibility(View.GONE);
				}
				else{
					txtCity.setText(dataItem.getCity());
				}
				
				DataContainer.bigBanerUrlList.clear();
				

				if(dataItem.getLogoSlider() != null){
					for (String s : dataItem.getLogoSlider()) {
						DataContainer.bigBanerUrlList.add(s.replace(" ", "%20"));
					}
				}
				if(!Helper.isBlank(dataItem.getLogo())){
					if(!DataContainer.bigBanerUrlList.contains(dataItem.getLogo()) && !dataItem.getLogo().contains("noimage")){
						DataContainer.bigBanerUrlList.add(dataItem.getLogo());
					}
				}
				
				if(DataContainer.bigBanerUrlList.size() == 1){
					ImageView bigImage = (ImageView) findViewById(R.id.ImageViewBigBanner);
					bigImage.setVisibility(View.VISIBLE);
					SliderLayout sl = (SliderLayout) findViewById(R.id.slider);
					sl.setLayoutParams(new LayoutParams(0, 0));
					sl.setVisibility(View.GONE);
					Picasso.with(activity).load(DataContainer.bigBanerUrlList.get(0)).into(bigImage);
				}else{
					for(int i = 0; i < DataContainer.bigBanerUrlList.size(); i++){
				         // initialize a SliderLayout BigBaner Slider
				         if(!Helper.isBlank(DataContainer.bigBanerUrlList.get(i))){
				        	 TextSliderView textSliderView = new TextSliderView(getBaseContext());
					         String pom = DataContainer.bigBanerUrlList.get(i);
					         textSliderView
					                 .image(pom)
					                 .setScaleType(BaseSliderView.ScaleType.CenterInside);
				        
					         mDemoSlider.addSlider(textSliderView);
				         }
					}
				}
				
				DataContainer.androSliderUrlList.clear();
				
//				if(!Helper.isBlank(dataItem.getImage())){
//					DataContainer.androSliderUrlList.add(dataItem.getImage());
//				}
				if(dataItem.getSlider() != null){
					for (String s : dataItem.getSlider()) {
						DataContainer.androSliderUrlList.add(s);
					}
				}
				if(!Helper.isBlank(dataItem.getVideo())){
					DataContainer.androSliderUrlList.add(dataItem.getVideo());
				}
				if(!Helper.isBlank(dataItem.getImage())){
					if(!DataContainer.androSliderUrlList.contains(dataItem.getImage())){
						DataContainer.androSliderUrlList.add(dataItem.getImage());
					}
				}
				
				if(findViewById(R.id.andro_slider_viewPager) != null){
					Log.d("MYTAG", "AndroSLider - YES");
					androSliderAdapter.fetchData();
					//androSliderAdapter.notifyDataSetChanged();
					inicAndroSlider(androSliderAdapter);
				 }
				

//				mapButton
//						.setText(ComponentInstance
//								.getTitleString(ComponentInstance.STRING_POGLEDAJ_NA_MAPI));
//				mapButton.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(v.getContext(),
//								PreviewItemOnMap.class);
//
//						intent.putExtra("x", dataItem.getX());
//						intent.putExtra("y", dataItem.getY());
//						intent.putExtra("title", dataItem.getTitle());
//
//						startActivity(intent);
//					}
//				});
				
				txtAdrress.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Double lat, lng;
						lat = Double.parseDouble(dataItem.getX());
						lng = Double.parseDouble(dataItem.getY());
						String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + dataItem.getTitle() + ")";
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
								Uri.parse(geoUri));
						activity.startActivity(intent);
					}
				});

				mapView.addMarker(new MarkerOptions().position(
						new LatLng(Double.parseDouble(dataItem.getX()), Double
								.parseDouble(dataItem.getY()))).title(
						dataItem.getTitle()));

				mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(
						(new LatLng(Double.parseDouble(dataItem.getX()), Double
								.parseDouble(dataItem.getY()))), 12.0f));
				
				inicMapView(mapView);
				
				butFavourite.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						DatabaseHandler db = new DatabaseHandler(activity);
						Log.d("MYTAG", "check Favourite: " + db.checkFavouriteStatus(dataItem.getId()));
						if(!db.checkFavouriteStatus(dataItem.getTitle())){
							Log.d("MYTAG", "Add Favourite");
							
							dataItem.setPreviewtype("company");
							dataItem.setId(id);
							dataItem.setCategory(category);
							db.addFavourite(dataItem);
							butFavourite.setImageResource(R.drawable.favourite_active);
						}else{
							butFavourite.setImageResource(R.drawable.favourite_inactive);
							Log.d("MYTAG", "Delete Favourite");
							db.deleteFavourite(dataItem);
						}
						
						Log.d("MYTAG", "ALL Favourites");
						
						for(DataItem item : db.getAllFavourites()){
							Log.d("MYTAG", "Favourite Record: " + item.getTitle());
						}
					}
				});
				

			} else if (previewType.equalsIgnoreCase("2")) {

				txtTitle.setText(dataItem.getTitle());
				txtDistance.setText(dist);
				//flfacebook.setVisibility(View.GONE);
				
				if(dataItem.getPhone1().equalsIgnoreCase(" ")){
					txtPhone1.setVisibility(View.GONE);
					flphone.setVisibility(View.GONE);
					separatorPhone.setVisibility(View.GONE);
				}
				else{
					txtPhone1.setText(dataItem.getPhone1());
					txtPhone1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getPhone1()));
							startActivityForResult(intent, 1);
						}
					});
					butPhone.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							final Dialog dialog = new Dialog(PreviewItemPage.this);
						    dialog.setContentView(R.layout.custom_dialog);
						    dialog.setTitle("Call");
						    dialog.setCancelable(true);
						    // set up radiobutton
						    Button rd1 = (Button) dialog.findViewById(R.id.rd_1);
						    Button rd2 = (Button) dialog.findViewById(R.id.rd_2);
						    Button bCancel = (Button) dialog.findViewById(R.id.b_cancel);
						    
						    rd1.setText(txtPhone1.getText());
						    
						    if(dataItem.getPhone2().equalsIgnoreCase(" ")){
								rd2.setVisibility(View.GONE);
							}
							else{
								rd2.setText(txtPhone2.getText());
								
								rd2.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(Intent.ACTION_CALL, Uri
												.parse("tel:" + dataItem.getPhone2()));
										startActivityForResult(intent, 1);
										dialog.cancel();
									}
								});
							}	
						    
						    rd1.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(Intent.ACTION_CALL, Uri
											.parse("tel:" + dataItem.getPhone1()));
									startActivityForResult(intent, 1);
									dialog.cancel();
								}
							});
						    
						    bCancel.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});

						    // now that the dialog is set up, it's time to show it
						    dialog.show();
							
						}
					});
				}
				
				if(dataItem.getPhone2().equalsIgnoreCase(" ")){
					txtPhone2.setVisibility(View.GONE);
				}
				else{
					txtPhone2.setText(dataItem.getPhone2());					
					txtPhone2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getPhone2()));
							startActivityForResult(intent, 1);
						}
					});
				}
				
				if(Helper.isBlank(dataItem.getFax())){
					txtFax.setVisibility(View.GONE);
				}else{
					txtFax.setText(dataItem.getFax());
					txtFax.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getFax()));
							startActivityForResult(intent, 1);
						}
					});
				}
				
				if(dataItem.getEmail().equalsIgnoreCase(" ")){
					txtEmail.setVisibility(View.GONE);
					flmail.setVisibility(View.GONE);
					separatorEmail.setVisibility(View.GONE);
				}
				else{
					txtEmail.setText(dataItem.getEmail());
					txtEmail.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL,
									new String[] { dataItem.getEmail() });
							i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
							i.putExtra(Intent.EXTRA_TEXT, "");

							startActivityForResult(
									Intent.createChooser(i, "Send mail..."), 1);
							
						}
					});
					butMail.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL,
									new String[] { dataItem.getEmail() });
							i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
							i.putExtra(Intent.EXTRA_TEXT, "");

							startActivityForResult(
									Intent.createChooser(i, "Send mail..."), 1);
							
						}
					});
				}
				
				if(dataItem.getWeb().equalsIgnoreCase(" ")){
					txtWeb.setVisibility(View.GONE);
					flwebsite.setVisibility(View.GONE);
					separatorWeb.setVisibility(View.GONE);
				}
				else{
					txtWeb.setText(dataItem.getWeb());
					txtWeb.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getWeb()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
					butWebSite.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getWeb()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
				}
				
				if(Helper.isBlank(dataItem.getShare())){
					txtFacebook.setVisibility(View.GONE);
					flfacebook.setVisibility(View.GONE);
				}
				else{
					txtFacebook.setText(dataItem.getShare());
					txtFacebook.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
//							TODO Solve facebook intent
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getShare()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
					butFacebook.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
//							TODO Solve facebook intent
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getShare()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
				}
				
//				Log.d("MYTAG", "Street: " + dataItem.getStreet());
//				Log.d("MYTAG", "City: " + dataItem.getCity());
//				Log.d("MYTAG", "Zona: " + dataItem.getZona());
				
				if(Helper.isBlank(dataItem.getStreet())){
					txtAdrress.setVisibility(View.GONE);
					separatorAddress.setVisibility(View.GONE);
				}
				else if(!Helper.isBlank(dataItem.getZona())){
					txtAdrress.setText(dataItem.getZona() + ", " + dataItem.getStreet());
				}else{
					txtAdrress.setText(dataItem.getStreet());
				}
				
				if(dataItem.getCity().equalsIgnoreCase(" ")){
					txtCity.setVisibility(View.GONE);
				}
				else{
					txtCity.setText(dataItem.getCity());
				}
			
				if(dataItem.getDescription().equalsIgnoreCase(" ")){
					txtDescription.setVisibility(View.GONE);
				}
				else{
					txtDescription.setText(dataItem.getDescription());
				}
			
				if(dataItem.getImage().equalsIgnoreCase(" ")){
					imageView.setVisibility(View.GONE);
				}else{
					ImageContainer.getInstance().getImageDownloader()
							.download(dataItem.getImage(), imageView);
				}
				
				DataContainer.bigBanerUrlList.clear();
				
//				if(!Helper.isBlank(dataItem.getLogo())){
//					DataContainer.bigBanerUrlList.add(dataItem.getLogo());
//				}
				if(dataItem.getLogoSlider() != null){
					for (String s : dataItem.getLogoSlider()) {
						DataContainer.bigBanerUrlList.add(s.replace(" ", "%20"));
					}
				}
				if(!Helper.isBlank(dataItem.getLogo())){
					if(!DataContainer.bigBanerUrlList.contains(dataItem.getLogo()) && !dataItem.getLogo().contains("noimage")){
						DataContainer.bigBanerUrlList.add(dataItem.getLogo());
					}
				}
				
				if(DataContainer.bigBanerUrlList.size() == 1){
					ImageView bigImage = (ImageView) findViewById(R.id.ImageViewBigBanner);
					bigImage.setVisibility(View.VISIBLE);
					SliderLayout sl = (SliderLayout) findViewById(R.id.slider);
					sl.setLayoutParams(new LayoutParams(0, 0));
					sl.setVisibility(View.GONE);
					Picasso.with(activity).load(DataContainer.bigBanerUrlList.get(0)).into(bigImage);
				}else{
					for(int i = 0; i < DataContainer.bigBanerUrlList.size(); i++){
				         // initialize a SliderLayout BigBaner Slider
				         if(!Helper.isBlank(DataContainer.bigBanerUrlList.get(i))){
				        	 TextSliderView textSliderView = new TextSliderView(getBaseContext());
					         String pom = DataContainer.bigBanerUrlList.get(i);
					         textSliderView
					                 .image(pom)
					                 .setScaleType(BaseSliderView.ScaleType.CenterInside);
				        
					         mDemoSlider.addSlider(textSliderView);
				         }
					}
				}
				DataContainer.androSliderUrlList.clear();
				
//				if(!Helper.isBlank(dataItem.getImage())){
//					DataContainer.androSliderUrlList.add(dataItem.getImage());
//				}
				if(dataItem.getSlider() != null){
					for (String s : dataItem.getSlider()) {
						DataContainer.androSliderUrlList.add(s);
					}
				}
				if(!Helper.isBlank(dataItem.getVideo())){
					DataContainer.androSliderUrlList.add(dataItem.getVideo());
				}
				if(!Helper.isBlank(dataItem.getImage())){
					if(!DataContainer.androSliderUrlList.contains(dataItem.getImage())){
						DataContainer.androSliderUrlList.add(dataItem.getImage());
					}
				}
				
				if(findViewById(R.id.andro_slider_viewPager) != null){
					Log.d("MYTAG", "AndroSLider - YES");
					androSliderAdapter.fetchData();
					//androSliderAdapter.notifyDataSetChanged();
					inicAndroSlider(androSliderAdapter);
				 }
				
//				mapButton
//						.setText(ComponentInstance
//								.getTitleString(ComponentInstance.STRING_POGLEDAJ_NA_MAPI));
//				txtAdrress.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(v.getContext(),
//								PreviewItemOnMap.class);
//
//						intent.putExtra("x", dataItem.getX());
//						intent.putExtra("y", dataItem.getY());
//						intent.putExtra("title", dataItem.getTitle());
//
//						startActivity(intent);
//					}
//				});
				txtAdrress.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Double lat, lng;
						lat = Double.parseDouble(dataItem.getX());
						lng = Double.parseDouble(dataItem.getY());
						String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + dataItem.getTitle() + ")";
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
								Uri.parse(geoUri));
						activity.startActivity(intent);
					}
				});
				// Active txtView

				mapView.addMarker(new MarkerOptions().position(
						new LatLng(Double.parseDouble(dataItem.getX()), Double
								.parseDouble(dataItem.getY()))).title(
						dataItem.getTitle()));

				mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(
						(new LatLng(Double.parseDouble(dataItem.getX()), Double
								.parseDouble(dataItem.getY()))), 12.0f));
				
				inicMapView(mapView);
				
				butFavourite.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						DatabaseHandler db = new DatabaseHandler(activity);
						Log.d("MYTAG", "check Favourite: " + db.checkFavouriteStatus(dataItem.getId()));
						if(!db.checkFavouriteStatus(dataItem.getTitle())){
							Log.d("MYTAG", "Add Favourite");
							
							dataItem.setPreviewtype("company");
							dataItem.setId(id);
							dataItem.setCategory(category);
							db.addFavourite(dataItem);
							butFavourite.setImageResource(R.drawable.favourite_active);
						}else{
							butFavourite.setImageResource(R.drawable.favourite_inactive);
							Log.d("MYTAG", "Delete Favourite");
							db.deleteFavourite(dataItem);
						}
						
						Log.d("MYTAG", "ALL Favourites");
						
						for(DataItem item : db.getAllFavourites()){
							Log.d("MYTAG", "Favourite Record: " + item.getTitle());
						}
					}
				});
				

			} else if (previewType.equalsIgnoreCase("3")) {

				txtTitle.setText(dataItem.getTitle());
				txtDistance.setText(dist);
				//flfacebook.setVisibility(View.GONE);
				
				if(dataItem.getPhone1().equalsIgnoreCase(" ")){
					txtPhone1.setVisibility(View.GONE);
					flphone.setVisibility(View.GONE);
					separatorPhone.setVisibility(View.GONE);
				}
				else{
					txtPhone1.setText(dataItem.getPhone1());
					txtPhone1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getPhone1()));
							startActivityForResult(intent, 1);
						}
					});
					butPhone.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							final Dialog dialog = new Dialog(PreviewItemPage.this);
						    dialog.setContentView(R.layout.custom_dialog);
						    dialog.setTitle("Call");
						    dialog.setCancelable(true);
						    // set up radiobutton
						    Button rd1 = (Button) dialog.findViewById(R.id.rd_1);
						    Button rd2 = (Button) dialog.findViewById(R.id.rd_2);
						    Button bCancel = (Button) dialog.findViewById(R.id.b_cancel);
						    
						    rd1.setText(txtPhone1.getText());
						    
						    if(dataItem.getPhone2().equalsIgnoreCase(" ")){
								rd2.setVisibility(View.GONE);
							}
							else{
								rd2.setText(txtPhone2.getText());
								
								rd2.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(Intent.ACTION_CALL, Uri
												.parse("tel:" + dataItem.getPhone2()));
										startActivityForResult(intent, 1);
										dialog.cancel();
									}
								});
							}	
						    
						    rd1.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(Intent.ACTION_CALL, Uri
											.parse("tel:" + dataItem.getPhone1()));
									startActivityForResult(intent, 1);
									dialog.cancel();
								}
							});
						    
						    bCancel.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});

						    // now that the dialog is set up, it's time to show it
						    dialog.show();
							
						}
					});
				}
				
				if(dataItem.getPhone2().equalsIgnoreCase(" ")){
					txtPhone2.setVisibility(View.GONE);
				}
				else{
					txtPhone2.setText(dataItem.getPhone2());
					txtPhone2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getPhone2()));
							startActivityForResult(intent, 1);
						}
					});
				}
				
				if(Helper.isBlank(dataItem.getFax())){
					txtFax.setVisibility(View.GONE);
				}else{
					txtFax.setText(dataItem.getFax());
					txtFax.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + dataItem.getFax()));
							startActivityForResult(intent, 1);
						}
					});
				}
				
				if(dataItem.getEmail().equalsIgnoreCase(" ")){
					txtEmail.setVisibility(View.GONE);
					flmail.setVisibility(View.GONE);
					separatorEmail.setVisibility(View.GONE);
				}
				else{
					txtEmail.setText(dataItem.getEmail());
					txtEmail.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL,
									new String[] { dataItem.getEmail() });
							i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
							i.putExtra(Intent.EXTRA_TEXT, "");

							startActivityForResult(
									Intent.createChooser(i, "Send mail..."), 1);
							
						}
					});
					butMail.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL,
									new String[] { dataItem.getEmail() });
							i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
							i.putExtra(Intent.EXTRA_TEXT, "");

							startActivityForResult(
									Intent.createChooser(i, "Send mail..."), 1);
							
						}
					});
				}
				
				if(dataItem.getWeb().equalsIgnoreCase(" ")){
					txtWeb.setVisibility(View.GONE);
					flwebsite.setVisibility(View.GONE);
					separatorWeb.setVisibility(View.GONE);
				}
				else{
					txtWeb.setText(dataItem.getWeb());
					txtWeb.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getWeb()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
					butWebSite.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getWeb()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
				}
				
				if(Helper.isBlank(dataItem.getShare())){
					txtFacebook.setVisibility(View.GONE);
					flfacebook.setVisibility(View.GONE);
				}
				else{
					txtFacebook.setText(dataItem.getShare());
					txtFacebook.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
//							TODO Solve facebook intent
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getShare()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
					butFacebook.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
//							TODO Solve facebook intent
							Intent browserIntent = new Intent(Intent.ACTION_VIEW,
									Uri.parse(dataItem.getShare()));
							startActivityForResult(browserIntent, 1);
							
						}
					});
				}
				
				if(dataItem.getStreet().equalsIgnoreCase(" ")){
					txtAdrress.setVisibility(View.GONE);
					separatorAddress.setVisibility(View.GONE);
				}
				else if(!Helper.isBlank(dataItem.getZona())){
					txtAdrress.setText(dataItem.getZona() + ", " + dataItem.getStreet());
				}else{
					txtAdrress.setText(dataItem.getStreet());
				}
				
				if(dataItem.getCity().equalsIgnoreCase(" ")){
					txtCity.setVisibility(View.GONE);
				}
				else{
					txtCity.setText(dataItem.getCity());
				}
			
				if(dataItem.getDescription().equalsIgnoreCase(" ")){
					txtDescription.setVisibility(View.GONE);
				}
				else{
					txtDescription.setText(dataItem.getDescription());
				}
				
				if(dataItem.getImage().equalsIgnoreCase(" ")){
					imageView.setVisibility(View.GONE);
				}
				else{
				ImageContainer.getInstance().getImageDownloader()
						.download(dataItem.getImage(), imageView);
				}
				if(dataItem.getImage2().equalsIgnoreCase(" ")){
					imageView.setVisibility(View.GONE);
				}
				else{
				ImageContainer.getInstance().getImageDownloader()
						.download(dataItem.getImage2(), imageView2);
				}

				DataContainer.bigBanerUrlList.clear();
				
//				if(!Helper.isBlank(dataItem.getLogo())){
//					DataContainer.bigBanerUrlList.add(dataItem.getLogo());
//				}
				if(dataItem.getLogoSlider() != null){
					for (String s : dataItem.getLogoSlider()) {
						DataContainer.bigBanerUrlList.add(s.replace(" ", "%20"));
					}
				}
				if(!Helper.isBlank(dataItem.getLogo())){
					if(!DataContainer.bigBanerUrlList.contains(dataItem.getLogo()) && !dataItem.getLogo().contains("noimage")){
						DataContainer.bigBanerUrlList.add(dataItem.getLogo());
					}
				}
				
				if(DataContainer.bigBanerUrlList.size() == 1){
					ImageView bigImage = (ImageView) findViewById(R.id.ImageViewBigBanner);
					bigImage.setVisibility(View.VISIBLE);
					SliderLayout sl = (SliderLayout) findViewById(R.id.slider);
					sl.setLayoutParams(new LayoutParams(0, 0));
					sl.setVisibility(View.GONE);
					Picasso.with(activity).load(DataContainer.bigBanerUrlList.get(0)).into(bigImage);
				}else{
					for(int i = 0; i < DataContainer.bigBanerUrlList.size(); i++){
				         // initialize a SliderLayout BigBaner Slider
				         if(!Helper.isBlank(DataContainer.bigBanerUrlList.get(i))){
				        	 TextSliderView textSliderView = new TextSliderView(getBaseContext());
					         String pom = DataContainer.bigBanerUrlList.get(i);
					         textSliderView
					                 .image(pom)
					                 .setScaleType(BaseSliderView.ScaleType.CenterInside);
				        
					         mDemoSlider.addSlider(textSliderView);
				         }
					}
				}
				
				DataContainer.androSliderUrlList.clear();
				
//				if(!Helper.isBlank(dataItem.getImage())){
//					DataContainer.androSliderUrlList.add(dataItem.getImage());
//				}
				if(dataItem.getSlider() != null){
					for (String s : dataItem.getSlider()) {
						DataContainer.androSliderUrlList.add(s);
					}
				}
				if(!Helper.isBlank(dataItem.getVideo())){
					DataContainer.androSliderUrlList.add(dataItem.getVideo());
				}
				if(!Helper.isBlank(dataItem.getImage())){
					if(!DataContainer.androSliderUrlList.contains(dataItem.getImage())){
						DataContainer.androSliderUrlList.add(dataItem.getImage());
					}
				}
				
				if(findViewById(R.id.andro_slider_viewPager) != null){
					Log.d("MYTAG", "AndroSLider - YES");
					androSliderAdapter.fetchData();
					//androSliderAdapter.notifyDataSetChanged();
					inicAndroSlider(androSliderAdapter);
				 }
				
				
//				mapButton
//						.setText(ComponentInstance
//								.getTitleString(ComponentInstance.STRING_POGLEDAJ_NA_MAPI));
//				mapButton.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(v.getContext(),
//								PreviewItemOnMap.class);
//
//						intent.putExtra("x", dataItem.getX());
//						intent.putExtra("y", dataItem.getY());
//						intent.putExtra("title", dataItem.getTitle());
//
//						startActivity(intent);
//					}
//				});
				
				txtAdrress.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Double lat, lng;
						lat = Double.parseDouble(dataItem.getX());
						lng = Double.parseDouble(dataItem.getY());
						String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + dataItem.getTitle() + ")";
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
								Uri.parse(geoUri));
						activity.startActivity(intent);
					}
				});

				inicMapView(mapView);
				
				butFavourite.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						DatabaseHandler db = new DatabaseHandler(activity);
						Log.d("MYTAG", "check Favourite: " + db.checkFavouriteStatus(dataItem.getId()));
						if(!db.checkFavouriteStatus(dataItem.getTitle())){
							Log.d("MYTAG", "Add Favourite");
							
							dataItem.setPreviewtype("company");
							dataItem.setId(id);
							dataItem.setCategory(category);
							db.addFavourite(dataItem);
							butFavourite.setImageResource(R.drawable.favourite_active);
						}else{
							butFavourite.setImageResource(R.drawable.favourite_inactive);
							Log.d("MYTAG", "Delete Favourite");
							db.deleteFavourite(dataItem);
						}
						
						Log.d("MYTAG", "ALL Favourites");
						
						for(DataItem item : db.getAllFavourites()){
							Log.d("MYTAG", "Favourite Record: " + item.getTitle());
						}
					}
				});
				
//				mapView.addMarker(new MarkerOptions().position(
//						new LatLng(Double.parseDouble(dataItem.getX()), Double
//								.parseDouble(dataItem.getY()))).title(
//						dataItem.getTitle()));
//
//				mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(
//						(new LatLng(Double.parseDouble(dataItem.getX()), Double
//								.parseDouble(dataItem.getY()))), 12.0f));
//				
//				mapView.getUiSettings().setAllGesturesEnabled(false);
//				mapView.getUiSettings().setZoomControlsEnabled(false);
//				mapView.setOnMapClickListener(new OnMapClickListener() {
//					
//					@Override
//					public void onMapClick(LatLng arg0) {
//						Double lat, lng;
//						lat = Double.parseDouble(dataItem.getX());
//						lng = Double.parseDouble(dataItem.getY());
//						String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + dataItem.getTitle() + ")";
//						Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//								Uri.parse(geoUri));
//						activity.startActivity(intent);
//					}
//				});
			}

		}

	}
	
	public void inicMapView(GoogleMap mapView){
		
		mapView.addMarker(new MarkerOptions().position(
				new LatLng(Double.parseDouble(dataItem.getX()), Double
						.parseDouble(dataItem.getY()))).title(
				dataItem.getTitle()));

		mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(
				(new LatLng(Double.parseDouble(dataItem.getX()), Double
						.parseDouble(dataItem.getY()))), 12.0f));
		
		mapView.getUiSettings().setAllGesturesEnabled(false);
		mapView.getUiSettings().setZoomControlsEnabled(false);
		mapView.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng arg0) {
				Double lat, lng;
				lat = Double.parseDouble(dataItem.getX());
				lng = Double.parseDouble(dataItem.getY());
				String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + dataItem.getTitle() + ")";
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse(geoUri));
				activity.startActivity(intent);
			}
		});
		
		mapView.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				Double lat, lng;
				lat = Double.parseDouble(dataItem.getX());
				lng = Double.parseDouble(dataItem.getY());
				String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + dataItem.getTitle() + ")";
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse(geoUri));
				activity.startActivity(intent);
				return false;
			}
		});
	}

	public void inicBottomButton() {
		buttonDirection = (ImageView) findViewById(R.id.imageViewDirection);
		buttonShare = (ImageView) findViewById(R.id.imageViewShare);
		buttonUp = (ImageView) findViewById(R.id.imageViewUp);
		
		ComponentInstance.inicTitleBar(this, title);
		
		buttonUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		buttonDirection.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				if(!locationService.canGetLocation()){
					Toast.makeText(PreviewItemPage.this, "No GPS signal!", Toast.LENGTH_LONG).show();
				}
				
				String longMy, latMy, longTo, latTo;

				longMy = Double.toString(locationService.getLongitude());
				latMy = Double.toString(locationService.getLatitude());

				longTo = dataItem.getY();
				latTo = dataItem.getX();

				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse("http://maps.google.com/maps?saddr=" + latMy
								+ "," + longMy + "&daddr=" + latTo + ","
								+ longTo));
				startActivityForResult(intent, 1);

			}
		});

//		buttonLike.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				// TODO Auto-generated method stub
//				startActivityForResult(
//						new Intent(Intent.ACTION_VIEW, Uri.parse(dataItem
//								.getShare())), 1);
//			}
//		});

		buttonShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						dataItem.getTitle() + ": " + dataItem.getShare());

				startActivityForResult(Intent.createChooser(sharingIntent,
						dataItem.getTitle()), 1);
			}
		});
	}

}
