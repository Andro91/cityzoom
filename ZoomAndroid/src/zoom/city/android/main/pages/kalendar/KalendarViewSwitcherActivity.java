package zoom.city.android.main.pages.kalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.pages.kulturnivodic.KulturniVodicKalendarViewPage;
import zoom.city.android.main.pages.nightlife.NightlifeKalendarViewPage;

public class KalendarViewSwitcherActivity extends Activity {

	String title, date,dateTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			title = extras.getString("title");
			date = extras.getString("date");
			dateTitle = extras.getString("dateTitle");
		}

		if (title.equalsIgnoreCase(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KULTURNI_VODIC))) {

			Intent intent = new Intent(this,
					KulturniVodicKalendarViewPage.class);
			intent.putExtra("title", title);
			intent.putExtra("date", date);
			intent.putExtra("dateTitle", dateTitle);
			startActivity(intent);

			finish();

		} else if (title.equalsIgnoreCase(ComponentInstance
				.getTitleString(ComponentInstance.STRING_NIGHT_LIFE))) {
			Intent intent = new Intent(this,
					NightlifeKalendarViewPage.class);
			intent.putExtra("title", title);
			intent.putExtra("date", date);
			intent.putExtra("dateTitle", dateTitle);
			startActivity(intent);

			finish();
		}

	}

}
