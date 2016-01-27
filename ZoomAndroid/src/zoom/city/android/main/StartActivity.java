package zoom.city.android.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.internet.AppInternetStatus;
import zoom.city.android.main.pages.MainPage;
import zoom.city.android.main.pages.SettingsPage;
import zoom.city.android.main.parser.Parser;

public class StartActivity extends Activity {
	/** Called when the activity is first created. */

	Thread startThread;
	SharedPreferences myPrefs;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		ComponentInstance.setMyPrefs(myPrefs);

		startThread = new Thread() {

			@Override
			public void run() {
				try {
					super.run();
					try {
						// DRZAVE, gradovi, zone
						DataContainer
								.getInstance() 
								.setDrzaveItems(
										Parser.parseSettings(Constant.URL_DRZAVE_GRADOVI));

						// Jezici
						DataContainer.getInstance().setJeziciItems(
								Parser.parseSettings(Constant.URL_JEZICI));

						// Kategorija i delatnosti (subcategorija)
						DataContainer.getInstance().setKategorijaSearchItems(
								Parser.parseSettings(Constant.URL_KATEGORIJE
										+ myPrefs.getString("jezikId", "")));
						
						

					} catch (Throwable t) {
					}
				} catch (Exception e) {
				}
				finally {

					if (myPrefs.getBoolean("firstTime", true)) {
						startActivity(new Intent(StartActivity.this,
								SettingsPage.class));
						finish();
					} else {
						startActivity(new Intent(StartActivity.this,
								MainPage.class));
						finish();
					}

				}
			}
		};
		
	

		if (AppInternetStatus.getInstance(this).isOnline()) {

			startThread.start();

		} else {

			Toast.makeText(this, "Nema interneta", Toast.LENGTH_LONG).show();
		}
	}
}