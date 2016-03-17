package zoom.city.android.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
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

		try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "zoom.city.android.main", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {
        	Log.d("KeyHash:", "NameNitFound");
        } catch (NoSuchAlgorithmException e) {
        	Log.d("KeyHash:", "NoSuch");
        }
		
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