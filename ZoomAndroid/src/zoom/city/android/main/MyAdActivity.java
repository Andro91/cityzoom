package zoom.city.android.main;

import zoom.city.android.main.container.DataContainer;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MyAdActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ad);
		
		ImageView button = (ImageView) findViewById(R.id.new_id_for_image_view_close);
		final String activityCode = getIntent().getStringExtra("activity_code");
		
		
		ImageView adImage = (ImageView) findViewById(R.id.image_view_my_ad);
		Bitmap bitmap = DataContainer.androTransitImageList.get(activityCode);
		
		if(bitmap == null){
			finish();
		}
		
		long timeNow = System.currentTimeMillis() / 1000L;
		
		DataContainer.androTransitTimestampList.put(activityCode, timeNow); 
		
		adImage.setImageBitmap(bitmap);
		
		adImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(
						Intent.ACTION_VIEW, Uri.parse(
								DataContainer.androTransitUrlList.get(activityCode)));
				startActivityForResult(browserIntent, 1);
			}
		});
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	protected void onPause() {
		//finish();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_ad, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
