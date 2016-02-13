package zoom.city.android.main.pages.adresar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;

public class NaprednaPretragaPage extends AppCompatActivity {

	TextView txtTitle, txtImeFirme, txtAdresa, txtGrad, txtZona, txtKategorija,
			txtDelatnost;
	EditText editImeFirme, editAdresa;
	Spinner spinnerGrad, spinnerZona, spinnerKategorija, spinnerDelatnost;
	Button searchButton;
	SharedPreferences myPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_napredna_pretraga);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		inicComponent();
		onComponentClick();
		fillData();
		
		Helper.inicActionBar(NaprednaPretragaPage.this, ComponentInstance
				.getTitleString(ComponentInstance.STRING_POSLOVNI_ADRESAR));
		
		ComponentInstance.inicGoogleBaner(this,myPrefs.getString("nazivGrada", ""),"ca-app-pub-1530516813542398/1143993865");

	}

	private void fillData() {
		// TODO Auto-generated method stub
		txtTitle.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_POSLOVNI_ADRESAR));

		txtDelatnost.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_DELATNOST));
		txtImeFirme.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_IME_FIRME));
		txtAdresa.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ADRESA));
		txtZona.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_ZONA));
		txtGrad.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_GRAD));
		txtKategorija.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_KATEGORIJA));

		searchButton.setText(ComponentInstance
				.getTitleString(ComponentInstance.STRING_TRAZI));

		ArrayAdapter<DataItem> gradAdapter = new ArrayAdapter<DataItem>(this,
				android.R.layout.simple_spinner_item, DataContainer
						.getInstance()
						.getDrzaveItems()
						.get(DataContainer
								.getInstance()
								.getDrzaveItems()
								.indexOf(
										new DataItem(myPrefs.getString(
												"drzavaId", "0"))))
						.getListDataItem());
		gradAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerGrad.setAdapter(gradAdapter);

		spinnerGrad.setSelection(0);

		spinnerGrad
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long arg3) {
						// TODO Auto-generated method stub
						ArrayAdapter<DataItem> zonaAdapter = new ArrayAdapter<DataItem>(
								view.getContext(),
								android.R.layout.simple_spinner_item,
								DataContainer
								.getInstance()
								.getDrzaveItems()
								.get(DataContainer
										.getInstance()
										.getDrzaveItems()
										.indexOf(
												new DataItem(myPrefs.getString(
														"drzavaId", "0"))))
								.getListDataItem().get(position).getListDataItem());
						zonaAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinnerZona.setAdapter(zonaAdapter);
						spinnerZona.setSelection(0);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		
		
		ArrayAdapter<DataItem> kategorijaAdapter = new ArrayAdapter<DataItem>(this,
				android.R.layout.simple_spinner_item, DataContainer
						.getInstance()
						.getKategorijaSearchItems());
		kategorijaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerKategorija.setAdapter(kategorijaAdapter);

		spinnerKategorija.setSelection(0);

		spinnerKategorija
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long arg3) {
						// TODO Auto-generated method stub
						ArrayAdapter<DataItem> delatnostAdapter = new ArrayAdapter<DataItem>(
								view.getContext(),
								android.R.layout.simple_spinner_item,
								DataContainer
										.getInstance()
										.getKategorijaSearchItems().get(position).getListDataItem());
						delatnostAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinnerDelatnost.setAdapter(delatnostAdapter);
						spinnerDelatnost.setSelection(0);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void onComponentClick() {
		// TODO Auto-generated method stub
		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(view.getContext(),
						PreviewAdvancedSearchPage.class);
				try{
					intent.putExtra("searchText", editImeFirme.getText().toString().trim());
					intent.putExtra("street", editAdresa.getText().toString().trim());
					intent.putExtra("township", ((DataItem)spinnerZona.getSelectedItem()).getId());
					intent.putExtra("category", ((DataItem)spinnerKategorija.getSelectedItem()).getId());
					intent.putExtra("subcategory", ((DataItem)spinnerDelatnost.getSelectedItem()).getId());
					intent.putExtra("gradId", ((DataItem)spinnerGrad.getSelectedItem()).getId());
				}catch(NullPointerException ex){
					Log.d("MYTAG", ex.getMessage());
				}
				startActivity(intent);
			}
		});
	}

	private void inicComponent() {
		// TODO Auto-generated method stub
		txtTitle = (TextView) findViewById(R.id.textViewTitle);
		txtImeFirme = (TextView) findViewById(R.id.textViewImeFirme);
		txtAdresa = (TextView) findViewById(R.id.textViewAdresa);
		txtZona = (TextView) findViewById(R.id.textViewZona);
		txtGrad = (TextView) findViewById(R.id.textViewGrad);
		txtKategorija = (TextView) findViewById(R.id.textViewKategorija);
		txtDelatnost = (TextView) findViewById(R.id.textViewDelatnost);

		searchButton = (Button) findViewById(R.id.buttonSearch);

		editAdresa = (EditText) findViewById(R.id.editTextAdresa);
		editImeFirme = (EditText) findViewById(R.id.editTextImeFirme);

		spinnerDelatnost = (Spinner) findViewById(R.id.spinnerDelatnost);
		spinnerGrad = (Spinner) findViewById(R.id.spinnerGrad);
		spinnerKategorija = (Spinner) findViewById(R.id.spinnerKategorija);
		spinnerZona = (Spinner) findViewById(R.id.spinnerZona);
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
