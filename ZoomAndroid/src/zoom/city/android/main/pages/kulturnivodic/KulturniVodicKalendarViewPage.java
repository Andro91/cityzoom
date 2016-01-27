package zoom.city.android.main.pages.kulturnivodic;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.pages.PreviewListItemPage;

public class KulturniVodicKalendarViewPage extends Activity {

	String title, date, dateTitle;
	LinearLayout layout1, layout2, layout3, layout4, layout5;
	TextView txtView1, txtView2, txtView3, txtView4, txtView5;

	SharedPreferences myPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_kulturni_vodic_kalendar_view);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		// inicijalizacija malih banera
		ComponentInstance.inicSmallBaner(this, "kulturnivodic",
				myPrefs.getString("drzavaId", "0"),
				myPrefs.getString("gradId", "0"),
				myPrefs.getString("jezikId", "0"));

		inicComponent();
		fillData();
		onComponentClick();

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			title = extras.getString("title");
			date = extras.getString("date");
			dateTitle = extras.getString("dateTitle");
		}

		ComponentInstance.inicTitleBar(this, dateTitle);
		
		ComponentInstance.inicGoogleBaner(this,myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/7330128266");

	}

	private void onComponentClick() {
		// TODO Auto-generated method stub
		layout1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				// Kalendar
				Intent intent = new Intent(KulturniVodicKalendarViewPage.this,
						PreviewListItemPage.class);
				intent.putExtra("title", ComponentInstance
						.getTitleString(ComponentInstance.STRING_POZORISTA));
				intent.putExtra("date", date);
				
				//Kulturni vodicu je tipa event
				intent.putExtra("type", "event");
				
				startActivity(intent);
			}
		});
		layout2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(KulturniVodicKalendarViewPage.this,
						PreviewListItemPage.class);
				intent.putExtra("title", ComponentInstance
						.getTitleString(ComponentInstance.STRING_KONCERTI));
				intent.putExtra("date", date);
				
				//Kulturni vodicu je tipa event
				intent.putExtra("type", "event");
				
				startActivity(intent);
			}
		});
		layout3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(KulturniVodicKalendarViewPage.this,
						PreviewListItemPage.class);
				intent.putExtra(
						"title",
						ComponentInstance
								.getTitleString(ComponentInstance.STRING_KULTURNI_CENTRI));
				intent.putExtra("date", date);
				
				//Kulturni vodicu je tipa event
				intent.putExtra("type", "event");
				
				startActivity(intent);
			}
		});
		layout4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(KulturniVodicKalendarViewPage.this,
						PreviewListItemPage.class);
				intent.putExtra(
						"title",
						ComponentInstance
								.getTitleString(ComponentInstance.STRING_MUZEJI_GALERIJE));
				intent.putExtra("date", date);
				
				//Kulturni vodicu je tipa event
				intent.putExtra("type", "event");
				
				startActivity(intent);
			}
		});
		layout5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(KulturniVodicKalendarViewPage.this,
						PreviewListItemPage.class);
				intent.putExtra("title", ComponentInstance
						.getTitleString(ComponentInstance.STRING_BIBLIOTEKE));
				intent.putExtra("date", date);
				
				//Kulturni vodicu je tipa event
				intent.putExtra("type", "event");
				
				startActivity(intent);
			}
		});
	}

	private void fillData() {
		// TODO Auto-generated method stub
		txtView1.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_POZORISTA));
		txtView2.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KONCERTI));
		txtView3.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KULTURNI_CENTRI));
		txtView4.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_MUZEJI_GALERIJE));
		txtView5.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_BIBLIOTEKE));
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		layout1 = (LinearLayout) findViewById(R.id.linearLayoutIcon1);
		layout2 = (LinearLayout) findViewById(R.id.linearLayoutIcon2);
		layout3 = (LinearLayout) findViewById(R.id.linearLayoutIcon3);
		layout4 = (LinearLayout) findViewById(R.id.linearLayoutIcon4);
		layout5 = (LinearLayout) findViewById(R.id.linearLayoutIcon5);

		txtView1 = (TextView) findViewById(R.id.textViewIcon1);
		txtView2 = (TextView) findViewById(R.id.textViewIcon2);
		txtView3 = (TextView) findViewById(R.id.textViewIcon3);
		txtView4 = (TextView) findViewById(R.id.textViewIcon4);
		txtView5 = (TextView) findViewById(R.id.textViewIcon5);
	}

}
