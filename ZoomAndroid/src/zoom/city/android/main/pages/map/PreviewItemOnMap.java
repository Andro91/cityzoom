package zoom.city.android.main.pages.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import zoom.city.android.main.R;


public class PreviewItemOnMap extends Activity {

	String x, y, title;
	GoogleMap mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_map_preview_item);

		inicComponent();
		fillData();
		onComponentClick();

	}

	private void onComponentClick() {
		// TODO Auto-generated method stub

	}

	private void fillData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			x = extras.getString("x");
			y = extras.getString("y");
			title = extras.getString("title");
		}

		mapView.addMarker(new MarkerOptions()
				.position(
						new LatLng(Double.parseDouble(x), Double
								.parseDouble(y)))
				.title(title));

		mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(
				(new LatLng(Double.parseDouble(x), Double
						.parseDouble(y))), 12.0f));

	}


	private void inicComponent() {
		// TODO Auto-generated method stub
		mapView = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
	}

}
