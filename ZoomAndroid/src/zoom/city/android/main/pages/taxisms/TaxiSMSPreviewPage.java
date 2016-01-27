package zoom.city.android.main.pages.taxisms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.container.ImageContainer;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.imageview.DinamicImageView;

public class TaxiSMSPreviewPage extends AppCompatActivity {

	private DataItem pomDataItem;
	TextView txtTitle, txtPhone1, txtPhone2, txtPhone3;
	DinamicImageView imageViewLogo;
	Button buttonSendSms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_taxi_sms_preview_item);

		inicComponent();
		fillData();
		onComponentClick();
		Helper.inicActionBar(TaxiSMSPreviewPage.this, ComponentInstance.getTitleString(ComponentInstance.STRING_TAXI_SMS));
	}

	private void onComponentClick() {
		// TODO Auto-generated method stub
		buttonSendSms.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				String smsNum = "";
				/*
				if (!pomDataItem.getPhone1().equalsIgnoreCase(" ")) {
					smsNum = pomDataItem.getPhone1();
				}
				if (!pomDataItem.getPhone2().equalsIgnoreCase(" ")) {
					smsNum = pomDataItem.getPhone2();
				}
				*/
				if (!pomDataItem.getPhone3().equalsIgnoreCase(" ")) {
					smsNum = pomDataItem.getPhone3();
				}

				Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.parse("sms:" + smsNum));
				smsIntent.putExtra("sms_body", ComponentInstance
						.getTitleString(ComponentInstance.STRING_SMS_TEXT));
				startActivityForResult(smsIntent, 1);
			}
		});

		txtPhone1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ pomDataItem.getPhone1()));
				startActivityForResult(intent, 1);
			}
		});

		txtPhone2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ pomDataItem.getPhone2()));
				startActivityForResult(intent, 1);
			}
		});

		txtPhone3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ pomDataItem.getPhone3()));
				startActivityForResult(intent, 1);
			}
		});
	}

	private void fillData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			pomDataItem = DataContainer.getInstance().getTaxiSmsList()
					.get(extras.getInt("position"));
		}

		
		if(pomDataItem.getPhone1().equalsIgnoreCase(" ")){
			txtPhone1.setVisibility(View.GONE);
		}
		else{
			txtPhone1.setText(pomDataItem.getPhone1());
		}
		
		if(pomDataItem.getPhone2().equalsIgnoreCase(" ")){
			txtPhone2.setVisibility(View.GONE);
		}
		else{
			txtPhone2.setText(pomDataItem.getPhone2());
		}
		
		
		txtPhone3.setText(pomDataItem.getPhone3());
		txtPhone3.setVisibility(View.GONE);

		if (pomDataItem.getPhone1().equalsIgnoreCase(" ")) {
			txtPhone1.setVisibility(View.GONE);
		}

		if (pomDataItem.getPhone2().equalsIgnoreCase(" ")) {
			txtPhone2.setVisibility(View.GONE);
		}
		if (pomDataItem.getPhone3().equalsIgnoreCase(" ")) {
			txtPhone3.setVisibility(View.GONE);
		}

		txtTitle.setText(pomDataItem.getTitle());

		ImageContainer.getInstance().getImageDownloader()
				.download(pomDataItem.getImage(), imageViewLogo);

		buttonSendSms.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_POSALJI_PORUKU));

	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		txtPhone1 = (TextView) findViewById(R.id.textViewPhone1);
		txtPhone2 = (TextView) findViewById(R.id.textViewPhone2);
		txtPhone3 = (TextView) findViewById(R.id.textViewPhone3);
		txtTitle = (TextView) findViewById(R.id.textViewTitle);
		imageViewLogo = (DinamicImageView) findViewById(R.id.imageViewLogo);
		buttonSendSms = (Button) findViewById(R.id.buttonSendSms);

	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish(); break;
            }
        return true;
    }
	
	public void inicActionBar() {
		try{
			ActionBar actionBar = getSupportActionBar();
			String title = ComponentInstance.getTitleString(ComponentInstance.STRING_TAXI_SMS);
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayUseLogoEnabled(false);
			if(!isBlank(title)){
				getSupportActionBar().setTitle(" " + title);
			}else{
				getSupportActionBar().setTitle(" " + "BACK");
			}
			}catch(Exception ex){
				Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
			}
	}
	
    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0 || string.equals("null"))
            return true;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!Character.isWhitespace(string.codePointAt(i)))
                return false;
        }
        return true;
    }

}
