package zoom.city.android.main.internet;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppInternetStatus {

	private static AppInternetStatus instance = new AppInternetStatus();
	private ConnectivityManager connectivityManager;
	//private NetworkInfo wifiInfo, mobileInfo;
	private static Context context;
	private boolean connected = false;

	public static AppInternetStatus getInstance(Context ctx) {

		context = ctx;
		return instance;
	}

	public Boolean isOnline() {

		try {
			connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable()
					&& networkInfo.isConnected();
			return connected;

		} catch (Exception e) {

		}

		return connected;
	}
}