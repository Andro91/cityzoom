package zoom.city.android.main.pages.adresar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.helper.Helper;

public class AdresarPage extends AppCompatActivity {

	TextView txtTitle, txtNapredna;
	Button searchButton;
	EditText searchText;
	Handler handler;
	Thread thread;
	
	SharedPreferences myPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_adresar);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		inicComponent();
		onCOmponentCLick();
		fillData();
		
		Helper.inicActionBar(AdresarPage.this, ComponentInstance
				.getTitleString(ComponentInstance.STRING_POSLOVNI_ADRESAR));
		
		ComponentInstance.inicGoogleBaner(this,myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/1143993865");
	}

	private void fillData() {
		// TODO Auto-generated method stub
		searchButton.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_TRAZI));
		txtNapredna.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_NAPREDNA_PRETAGA));
		txtTitle.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_POSLOVNI_ADRESAR));
	}

	private void onCOmponentCLick() {
		// TODO Auto-generated method stub
		txtNapredna.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(view.getContext(),
						NaprednaPretragaPage.class);
				startActivity(intent);
			}
		});

		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(view.getContext(),
						PreviewSearchResult.class);
				intent.putExtra("searchText", searchText.getText().toString().trim());
				startActivity(intent);
			}
		});
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		txtTitle = (TextView) findViewById(R.id.textViewTitle);
		searchButton = (Button) findViewById(R.id.buttonSearch);
		txtNapredna = (TextView) findViewById(R.id.textViewNapredna);
		searchText = (EditText) findViewById(R.id.editTextSearch);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish(); break;
            }
        return true;
    }

}
