package zoom.city.android.main;

import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.helper.Helper;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MyAdActivity extends AppCompatActivity {
	
	String transitIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ad);
		
		ImageView button = (ImageView) findViewById(R.id.new_id_for_image_view_close);
		final int activityCode = getIntent().getIntExtra("activity_code",0);
		
		
		if(getIntent().getStringExtra("transit_index") == null){
			transitIndex = "" + activityCode;
		}else{
			transitIndex = getIntent().getStringExtra("transit_index");
		}
		
		long timeSinceLastTransitDisplay = 0;
    	if (DataContainer.androTransitTimestampList.get(transitIndex) != null) {
    		long timeNow = System.currentTimeMillis() / 1000L;
        	long timeOfLastTransitDisplay = DataContainer.androTransitTimestampList.get(transitIndex);
        	timeSinceLastTransitDisplay =  timeNow - timeOfLastTransitDisplay;
		}
		
    	Log.d("MYTAG", "MyAdActivity.timeSinceLastTransitDisplay: " + timeSinceLastTransitDisplay);
    	
    	if(timeSinceLastTransitDisplay < 300){
    		finish();
    	}
    	
		Log.d("MYTAG", "MyAdActivity.activityCode: " + activityCode);
		
		ImageView adImage = (ImageView) findViewById(R.id.image_view_my_ad);
		Bitmap bitmap = DataContainer.androTransitImageList.get(transitIndex);
		
		if(bitmap == null || activityCode == 0){
			finish();
		}
		
		long timeNow = System.currentTimeMillis() / 1000L;
		
		DataContainer.androTransitTimestampList.put(transitIndex, timeNow); 
		
		adImage.setImageBitmap(bitmap);
		
		adImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch(Helper.linkSwitch(DataContainer.androTransitUrlList.get(transitIndex))){
				case Helper.LINK_WEB_CODE: {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DataContainer.androTransitUrlList.get(transitIndex)));
					startActivityForResult(browserIntent, 1);
					return;
				}
				case Helper.LINK_PHONE_CODE: {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + DataContainer.androTransitUrlList.get(transitIndex)));
					startActivityForResult(intent, 1);
					return;
				}
				case Helper.LINK_EMAIL_CODE: {
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("message/rfc822");
					i.putExtra(Intent.EXTRA_EMAIL,
							new String[] { DataContainer.androTransitUrlList.get(transitIndex) });
					i.putExtra(Intent.EXTRA_SUBJECT, "ZOOM Android");
					i.putExtra(Intent.EXTRA_TEXT, "");

					startActivityForResult(
							Intent.createChooser(i, "Send mail..."), 1);
					return;
				}
			}
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
