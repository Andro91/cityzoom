package zoom.city.android.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;

public class SettingsPage extends AppCompatActivity {

	TextView txtTitle, txtDrzava, txtJezik, txtGrad;
	Button confirmButton;
	Spinner spinnerDrzava, spinnerGrad, spinnerJezik;

	SharedPreferences myPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_settings);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		
		inicComponenet();
		onCOmponentCLick();
		fillData();
		
		Helper.inicActionBar(SettingsPage.this, "Settings");
		
		ComponentInstance.inicGoogleBaner(this,myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/1143993865");
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		 super.onBackPressed();
		if (!myPrefs.getBoolean("firstTime", true)) {
	     	startActivity(new Intent(SettingsPage.this,
						MainPage.class));
	        finish();
        }else{
        	finish();
        }
	}

	private void fillData() {
		// TODO Auto-generated method stub
		txtDrzava.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_DRZAVA));
		txtGrad.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_GRAD));
		txtJezik.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_JEZIK));
		txtTitle.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_PODESAVANJE));
		confirmButton.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_POTVRDI));

		// SETUP SPINNER BAR
		ArrayAdapter<DataItem> drzavaAdapter = new ArrayAdapter<DataItem>(this,
				android.R.layout.simple_spinner_item, DataContainer
						.getInstance().getDrzaveItems());
		drzavaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDrzava.setAdapter(drzavaAdapter);

		spinnerDrzava.setSelection(DataContainer.getInstance().getDrzaveItems()
				.indexOf(new DataItem(myPrefs.getString("drzavaId", "0"))));

		spinnerDrzava
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long arg3) {
						// TODO Auto-generated method stub
						ArrayAdapter<DataItem> gradAdapter = new ArrayAdapter<DataItem>(
								view.getContext(),
								android.R.layout.simple_spinner_item,
								DataContainer.getInstance().getDrzaveItems()
										.get(position).getListDataItem());
						gradAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinnerGrad.setAdapter(gradAdapter);
						spinnerGrad.setSelection(DataContainer
								.getInstance()
								.getDrzaveItems()
								.get(position)
								.getListDataItem()
								.indexOf(
										new DataItem(myPrefs.getString(
												"gradId", "0"))));
						spinnerGrad.setSelection(0);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		ArrayAdapter<DataItem> jeziciAdapter = new ArrayAdapter<DataItem>(this,
				android.R.layout.simple_spinner_item, DataContainer
						.getInstance().getJeziciItems());
		jeziciAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerJezik.setAdapter(jeziciAdapter);
		spinnerJezik.setSelection(DataContainer.getInstance().getJeziciItems()
				.indexOf(new DataItem(myPrefs.getString("jezikId", "0"))));

	}

	private void onCOmponentCLick() {
		// TODO Auto-generated method stub
		confirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				boolean insertOK = true;

				if (spinnerDrzava.getSelectedItem() != null) {
					myPrefs.edit()
							.putString(
									"drzavaId",
									((DataItem) spinnerDrzava.getSelectedItem())
											.getId()).commit();
				} else {
					insertOK = false;
					Toast.makeText(
							view.getContext(),
							ComponentInstance
									.getTitleString(ComponentInstance.STRING_UNOS_DRZAVE),
							Toast.LENGTH_LONG).show();
					myPrefs.edit().putString("drzavaId", "0").commit();
				}

				if (spinnerGrad.getSelectedItem() != null) {
					myPrefs.edit()
							.putString(
									"gradId",
									((DataItem) spinnerGrad.getSelectedItem())
											.getId()).commit();
					myPrefs.edit()
					.putString(
							"nazivGrada",
							((DataItem) spinnerGrad.getSelectedItem())
									.getTitle()).commit();
				} else {
					insertOK = false;
					Toast.makeText(
							view.getContext(),
							ComponentInstance
									.getTitleString(ComponentInstance.STRING_UNOS_GRADA),
							Toast.LENGTH_LONG).show();
					myPrefs.edit().putString("gradId", "0").commit();
				}

				if (spinnerJezik.getSelectedItem() != null) {
					myPrefs.edit()
							.putString(
									"jezikId",
									((DataItem) spinnerJezik.getSelectedItem())
											.getId()).commit();
				} else {
					insertOK = false;
					Toast.makeText(
							view.getContext(),
							ComponentInstance
									.getTitleString(ComponentInstance.STRING_UNOS_JEZIKA),
							Toast.LENGTH_LONG).show();
					myPrefs.edit().putString("jezikId", "0").commit();
				}

				DataContainer.getInstance().getBigBanerItemList().clear();
				DataContainer.getInstance().getSmallBanerItemList().clear();
				DataContainer.getInstance().getDataItemList().clear();
				DataContainer.getInstance().getMapDataList().clear();

				if (insertOK) {
					if (myPrefs.getBoolean("firstTime", true)) {

						myPrefs.edit().putBoolean("firstTime", false).commit();

						startActivity(new Intent(SettingsPage.this,
								MainPage.class));
						finish();

					} else {

						startActivity(new Intent(SettingsPage.this,
								MainPage.class));
						finish();

					}
				}

			}
		});
	}

	private void inicComponenet() {
		// TODO Auto-generated method stub
		txtDrzava = (TextView) findViewById(R.id.textViewZemlja);
		txtTitle = (TextView) findViewById(R.id.textViewTitle);
		txtJezik = (TextView) findViewById(R.id.textViewJezik);
		txtGrad = (TextView) findViewById(R.id.textViewGrad);

		confirmButton = (Button) findViewById(R.id.buttonConfirm);

		spinnerDrzava = (Spinner) findViewById(R.id.spinnerZemlja);
		spinnerGrad = (Spinner) findViewById(R.id.spinnerGrad);
		spinnerJezik = (Spinner) findViewById(R.id.spinnerJezik);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	startActivity(new Intent(SettingsPage.this,
					MainPage.class));
            finish();
            return true;
        }
        return true;
	}
	

}
