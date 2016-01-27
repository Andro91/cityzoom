package zoom.city.android.main.constant;

import java.util.Calendar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.container.ImageContainer;
import zoom.city.android.main.container.StringContainer;
import zoom.city.android.main.pages.SettingsPage;
import zoom.city.android.main.pages.previewitem.PreviewItemPage;
import zoom.city.android.main.pages.smallbaners.SmallBanersPreviewPage;
import zoom.city.android.main.parser.ParserBaner;

public class ComponentInstance {

	public static final int STRING_PREPORUKE = 0, STRING_KULTURNI_VODIC = 1,
			STRING_CITY_ZOOM = 2, STRING_ADRESAR = 3, STRING_ICE_PICE = 4,
			STRING_NIGHT_LIFE = 5, STRING_SHOPPING = 6, STRING_TAXI_SMS = 7,
			STRING_SMESTAJ = 8, STRING_WELNES_AND_SPA = 9, STRING_MAP = 10,
			STRING_KALENDAR = 11, STRING_POZORISTA = 12, STRING_KONCERTI = 13,
			STRING_MUZEJI_GALERIJE = 14, STRING_KULTURNI_CENTRI = 15,
			STRING_BIBLIOTEKE = 16, STRING_ZNAMENITOSTI = 17,
			STRING_INSPIRACIJA = 18, STRING_PREVOZ = 19, STRING_KES = 20,
			STRING_MEDICO = 21, STRING_GAS = 22, STRING_WI_FI = 23,
			STRING_HOTELI = 24, STRING_APARTMANI = 25, STRING_MOTELI = 26,
			STRING_HOSTELI = 27, STRING_SOBE = 28, STRING_KAMPOVI = 29,
			STRING_RENT_LOCAL = 30, STRING_RENT_STAN = 31, STRING_BUS = 32,
			STRING_TAXI = 33, STRING_RENT_CAR = 34, STRING_RENT_SCUTER = 35,
			STRING_AVIO = 36, STRING_VOZ = 37, STRING_RENT_BOAT = 38,
			STRING_BANKE = 39, STRING_ATM = 40, STRING_MENJACNICE = 41,
			STRING_BOLNICE = 42, STRING_APOTEKE = 43, STRING_ORDINACIJE = 44,
			STRING_CAFEI = 45, STRING_RESTORANI = 46, STRING_FAST_FOOD = 47,
			STRING_KLUBOVI = 48, STRING_BAROVI = 49, STRING_EVENTS = 50,
			STRING_WELLNESS = 51, STRING_SPA = 52, STRING_BEAUTY = 53,
			STRING_RAINBOW = 54, STRING_POSLOVNI_ADRESAR = 55,
			STRING_TRAZI = 56, STRING_NAPREDNA_PRETAGA = 57,
			STRING_IME_FIRME = 58, STRING_ADRESA = 59, STRING_GRAD = 60,
			STRING_ZONA = 61, STRING_KATEGORIJA = 62, STRING_DELATNOST = 63,
			STRING_DRZAVA = 64, STRING_JEZIK = 65, STRING_POTVRDI = 66,
			STRING_PODESAVANJE = 67, STRING_UNOS_DRZAVE = 68,
			STRING_UNOS_GRADA = 69, STRING_UNOS_JEZIKA = 70,
			STRING_POSALJI_PORUKU = 71, STRING_SMS_TEXT = 72,
			STRING_POGLEDAJ_NA_MAPI = 73,STRING_KATEGORIJE=74,STRING_MORE=75,STRING_MUST_TO_DO=76;

	private static Calendar appCalendar;
	private static SharedPreferences myPrefs;

	public static SharedPreferences getMyPrefs() {
		return myPrefs;
	}

	public static void setMyPrefs(SharedPreferences myPrefs) {
		ComponentInstance.myPrefs = myPrefs;
	}

	public static Calendar getAppCalendar() {
		return appCalendar.getInstance();
	}

	public void setAppCalendar(Calendar appCalendar) {
		this.appCalendar = appCalendar;
	}

	public static void inicActionBar(Context context) {
//		Button settingsButton = (Button) ((Activity) context)
//				.findViewById(R.id.buttonSettings);

//		settingsButton.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(view.getContext(),
//						SettingsPage.class);
//				((Activity) view.getContext()).startActivity(intent);
//				((Activity) view.getContext()).finish();
//			}
//		});
		
		ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.hamburger);
		actionBar.setDisplayShowTitleEnabled(true);
	}

	public static void inicTitleBar(Context context, String title) {
		TextView txtTitle = (TextView) ((Activity) context)
				.findViewById(R.id.textViewMainTitle);

		txtTitle.setText(title);
	}
	
	public static void inicBackTitleBar(Context context, String title) {
		TextView txtTitle = (TextView) ((Activity) context)
				.findViewById(R.id.textViewTitle);

		txtTitle.setText(title);
	}

	public static String getTitleString(int number) {
		// LOCAL = 1
		
		int jezikId = Integer.parseInt(myPrefs.getString("jezikId", "1")) - 1;
		
		String result = StringContainer.getInstance().getIconTextList().get(jezikId)
				.get(number);
		
		return result;
	}

	// VELIKI BANER
	public static void inicBigBaner(final Context context, final String title,
			final String country, final String city) {
		final Handler handler;
		Thread thread;
		
		final ImageView imageView = (ImageView) ((Activity) context)
				.findViewById(R.id.imageViewBigBaner1);
		//imageView.setScaleType(ScaleType.FIT_XY);

		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String companyId = DataContainer.getInstance().getBigBanerItemList().get(title).get(0).getCompanyId();
				
				if(!companyId.equalsIgnoreCase("null")){
					Intent i = new Intent(context, PreviewItemPage.class);
					
					i.putExtra("id", companyId);
					i.putExtra("type", "company");
					i.putExtra("language", myPrefs.getString("jezikId", "0"));
					
					context.startActivity(i);
				}
			}
		});

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				ImageContainer
						.getInstance()
						.getImageDownloader()
						.download(
								DataContainer.getInstance()
										.getBigBanerItemList().get(title)
										.get(0).getImage(), imageView);
				super.handleMessage(msg);
			}

		};

		thread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				try {
					DataContainer
							.getInstance()
							.getBigBanerItemList()
							.put(title,
									ParserBaner.parseBigBaner(title, country,
											city));
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					handler.sendEmptyMessage(0);
				}
			}
		};

		if (DataContainer.getInstance().getBigBanerItemList()
				.containsKey(title)) {
			handler.sendEmptyMessage(0);
		} else {
			thread.start();
		}
	}


	// DVA MALA BANERA
	public static void inicSmallBaner(final Context context,
			final String title, final String country, final String city,
			final String language) {
		final Handler handler;
		Thread thread;

		final ImageView imageView1 = (ImageView) ((Activity) context)
				.findViewById(R.id.imageViewSmallBaner1);
		final ImageView imageView2 = (ImageView) ((Activity) context)
				.findViewById(R.id.imageViewSmallBaner2);
		final TextView textView1 = (TextView) ((Activity) context)
				.findViewById(R.id.textViewSmallBaner1);
		final TextView textView2 = (TextView) ((Activity) context)
				.findViewById(R.id.textViewSmallBaner2);

		final FrameLayout frame1 = (FrameLayout) ((Activity) context)
				.findViewById(R.id.FrameLayoutSmallBaner1);
		final FrameLayout frame2 = (FrameLayout) ((Activity) context)
				.findViewById(R.id.FrameLayoutSmallBaner2);

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				if (DataContainer.getInstance().getSmallBanerItemList()
						.get(title).get(0).getImage().equalsIgnoreCase(" ")) {

					frame1.setVisibility(View.GONE);

				} else {

					ImageContainer
							.getInstance()
							.getImageDownloader()
							.download(
									DataContainer.getInstance()
											.getSmallBanerItemList().get(title)
											.get(0).getImage(), imageView1);
					textView1.setText(DataContainer.getInstance()
							.getSmallBanerItemList().get(title).get(0)
							.getCategory());

					frame1.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(context,
									SmallBanersPreviewPage.class);
							intent.putExtra("title",
									DataContainer.getInstance()
											.getSmallBanerItemList().get(title)
											.get(0).getCategory());
							intent.putExtra("id", "5");
							((Activity) context).startActivity(intent);
						}
					});

				}

				if (DataContainer.getInstance().getSmallBanerItemList()
						.get(title).get(1).getImage().equalsIgnoreCase(" ")) {

					frame2.setVisibility(View.GONE);

				} else {
					ImageContainer
							.getInstance()
							.getImageDownloader()
							.download(
									DataContainer.getInstance()
											.getSmallBanerItemList().get(title)
											.get(1).getImage(), imageView2);
					textView2.setText(DataContainer.getInstance()
							.getSmallBanerItemList().get(title).get(1)
							.getCategory());
					frame2.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(context,
									SmallBanersPreviewPage.class);
							intent.putExtra("title",
									DataContainer.getInstance()
											.getSmallBanerItemList().get(title)
											.get(1).getCategory());
							intent.putExtra("id", "10");
							((Activity) context).startActivity(intent);
						}
					});
				}

				super.handleMessage(msg);
			}

		};

		thread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				try {
					DataContainer
							.getInstance()
							.getSmallBanerItemList()
							.put(title,
									ParserBaner.parseSmallBaner(title, country,
											city, language));
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					handler.sendEmptyMessage(0);
				}
			}
		};

		if (DataContainer.getInstance().getSmallBanerItemList()
				.containsKey(title)) {
			handler.sendEmptyMessage(0);
		} else {
			thread.start();
		}

	}

	// Google baner
	public static void inicGoogleBaner(Context context, String keyWord,String AD_UNIT_ID) {

		LinearLayout linearLayoutGoogleBaner;
		AdView adView;
		//String AD_UNIT_ID = "ca-app-pub-1530516813542398/4376661865";

		linearLayoutGoogleBaner = (LinearLayout) ((Activity) context)
				.findViewById(R.id.linearLayoutGoogleBaner);
		linearLayoutGoogleBaner.removeAllViews();
		// Create an ad.
		adView = new AdView(context);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(AD_UNIT_ID);

		adView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		linearLayoutGoogleBaner.addView(adView);

		// Create an ad request. Check logcat output for the hashed device ID to
		// get test ads on a physical device.
		AdRequest adRequest = new AdRequest.Builder().addKeyword(keyWord)
				.build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);

	}
	
	public static void inicTopButton(final Context context) {
		ImageView buttonUp;
		buttonUp = (ImageView) ((Activity) context).findViewById(R.id.imageViewUp);
		
		buttonUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Activity) context).finish();
			}
		});
	}

	private static InterstitialAd interstitial;

	public static void showBigGoogleBaner() {

		if (interstitial.isLoaded()) {
			interstitial.show();
		}

	}

	public static void inicBigGoogleBanerTest(Context context,String AD_UNIT_ID) {

		//String AD_UNIT_ID = "ca-app-pub-1530516813542398/4376661865";
		//String AD_UNIT_ID = "ba40fe4ce2a84e6b";
		interstitial = new InterstitialAd(context);
		interstitial.setAdUnitId(AD_UNIT_ID);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				AdRequest.DEVICE_ID_EMULATOR).build();
		interstitial.loadAd(adRequest);

		interstitial.setAdListener(new AdListener() {

			@Override
			public void onAdClosed() {
				// TODO Auto-generated method stub
				super.onAdClosed();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				// TODO Auto-generated method stub

				super.onAdFailedToLoad(errorCode);
			}

			@Override
			public void onAdLoaded() {
				// TODO Auto-generated method stub
				super.onAdLoaded();
			}

			@Override
			public void onAdOpened() {
				// TODO Auto-generated method stub
				super.onAdOpened();
			}
		});

	}
	
	public static InterstitialAd inicFullScreenBaner(Context context,String adId){
		InterstitialAd interstitial = new InterstitialAd(context);
		interstitial.setAdUnitId(adId);
		// Create ad request.
		AdRequest adRequest = new AdRequest.Builder().build();

		// Begin loading your interstitial.
		interstitial.loadAd(adRequest);
		return interstitial;
	}

}
