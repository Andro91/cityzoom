package zoom.city.android.main.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import zoom.city.android.main.constant.ComponentInstance;
import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.data.DataItem;

public class Parser {
	public static List<DataItem> parseSettings(String url) {

		List<DataItem> dataItemList = new ArrayList<DataItem>();

		JSONArray datItemJsonArray = null;

		JsonParser jParser = new JsonParser();

		// getting JSON Object from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		{

			try {
				// Getting Array of Contacts
				datItemJsonArray = json.getJSONArray("data");

				// looping through All Contacts
				for (int i = 0; i < datItemJsonArray.length(); i++) {

					JSONObject pom = datItemJsonArray.getJSONObject(i);

					// Storing each json item in variable
					// Set parsing variable into news item and add it in list

					DataItem dataItem = new DataItem();

					if (pom.has("name")) {
						dataItem.setTitle(pom.getString("name"));
					}
					if (pom.has("id")) {
						dataItem.setId(pom.getString("id"));
					}

					if (pom.has("cities")) {

						JSONArray arrayCities = pom.getJSONArray("cities");

						List<DataItem> dataItemListCity = new ArrayList<DataItem>();

						for (int j = 0; j < arrayCities.length(); j++) {

							JSONObject pomCity = arrayCities.getJSONObject(j);

							DataItem dataItemCity = new DataItem();
							dataItemCity.setTitle(pomCity.getString("name"));
							dataItemCity.setId(pomCity.getString("id"));

							if (pomCity.has("zone")) {

								JSONArray zonaArray = pomCity
										.getJSONArray("zone");

								List<DataItem> dataItemListZone = new ArrayList<DataItem>();

								for (int k = 0; k < zonaArray.length(); k++) {
									JSONObject pomZone = zonaArray
											.getJSONObject(k);

									DataItem dataItemZone = new DataItem();
									dataItemZone.setTitle(pomZone
											.getString("name"));
									dataItemZone.setId(pomZone.getString("id"));

									dataItemListZone.add(dataItemZone);
								}

								dataItemCity.setListDataItem(dataItemListZone);
							}

							dataItemListCity.add(dataItemCity);
						}

						dataItem.setListDataItem(dataItemListCity);

					}

					if (pom.has("subcategories")) {
						JSONArray arraySubCategory = pom
								.getJSONArray("subcategories");

						List<DataItem> dataItemListSubCategory = new ArrayList<DataItem>();

						for (int j = 0; j < arraySubCategory.length(); j++) {

							JSONObject pomSubCategory = arraySubCategory
									.getJSONObject(j);

							DataItem dataItemSubCategory = new DataItem();
							dataItemSubCategory.setTitle(pomSubCategory
									.getString("name"));
							dataItemSubCategory.setId(pomSubCategory
									.getString("id"));

							dataItemListSubCategory.add(dataItemSubCategory);
						}

						dataItem.setListDataItem(dataItemListSubCategory);
					}

					dataItemList.add(dataItem);
				}
			} catch (JSONException e) {
			}

			return dataItemList;
		}
	}

	public static List<DataItem> parseItems(String url) {

		List<DataItem> dataItemList = new ArrayList<DataItem>();

		JSONArray datItemJsonArray = null;

		JsonParser jParser = new JsonParser();

		// getting JSON Object from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		{

			try {
				// Getting Array of Contacts
				datItemJsonArray = json.getJSONArray("data");

				// looping through All Contacts
				for (int i = 0; i < datItemJsonArray.length(); i++) {

					JSONObject pom = datItemJsonArray.getJSONObject(i);

					// Storing each json item in variable
					// Set parsing variable into news item and add it in list

					DataItem dataItem = new DataItem();

					if (pom.has("title")) {
						dataItem.setTitle(pom.getString("title"));
					}

					if (pom.has("id")) {
						dataItem.setId(pom.getString("id"));
					}

					if (pom.has("category")) {
						dataItem.setCategory(pom.getString("category"));
					} else {
						dataItem.setCategory("");
					}

					if (pom.has("company")) {
						dataItem.setCompany(pom.getString("company"));
					} else {
						dataItem.setCompany("");
					}

					if (pom.has("date")) {
						dataItem.setDate(pom.getString("date"));
					} else {
						dataItem.setDate("");
					}

					if (pom.has("logo")) {
						dataItem.setImage(pom.getString("logo"));
					}

					if (pom.has("name")) {
						dataItem.setTitle(pom.getString("name"));
					}

					if (pom.has("companyid")) {
						dataItem.setCompanyId(pom.getString("companyid"));
					}
					
					//ArrayList<String>  slider = new ArrayList<String>(){{ add(""); }};
					if (pom.has("slider")) {
						if(pom.getJSONArray("slider").length() > 0){
							ArrayList<String>  slider = new ArrayList<String>();
							slider.clear();
							JSONArray jArray = pom.getJSONArray("slider");
							for (int j = 0; j < jArray.length(); j++) {
								slider.add(jArray.getString(j));
							}
							dataItem.setSlider(slider);
						}else{
							dataItem.setSlider(null);
						}
					}
					else{
						dataItem.setSlider(null);
					}
					
					if (pom.has("logo_slider")) {
						if(pom.getJSONArray("logo_slider").length() > 0){
							ArrayList<String>  logoSlider = new ArrayList<String>();
							logoSlider.clear();
							JSONArray jArray = pom.getJSONArray("logo_slider");
							for (int j = 0; j < jArray.length(); j++) {
								logoSlider.add(jArray.getString(j));
							}
							dataItem.setLogoSlider(logoSlider);
						}else{
							dataItem.setLogoSlider(null);
						}
					}
					else{
						dataItem.setLogoSlider(null);
					}
					
					if (pom.has("x")) {
						dataItem.setX(pom.getString("x"));
					}
					
					if (pom.has("y")) {
						dataItem.setY(pom.getString("y"));
					}

					dataItem.setTitleIndicator("1");

					dataItemList.add(dataItem);
				}
			} catch (JSONException e) {
			}

			return dataItemList;
		}
	}

	public static List<DataItem> parse(String title, String drzavaId,
			String gradId, String jezikId, String date) {

		if (date != null) {
			//date = date.replaceAll("-", "");
		} else {
			date = "";
		}

		if (!DataContainer.getInstance().getDataItemList()
				.containsKey(title + date)) {

			// PREPORUKE
			if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_PREPORUKE))) {

				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/recommendations?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId));

			}

			// KULTURNI VODIC
			// POZORISTA
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_POZORISTA))) {
				// Za pozorista id je fiksno i uvek 6
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/kulturnivodic?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId
										+ "&id=6&date=" + date));
			}

			// KONCERTI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_KONCERTI))) {
				// Za koncerte id je fiksno i uvek 7
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/kulturnivodic?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId
										+ "&id=7&date=" + date));
			}

			// MUZEJI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_MUZEJI_GALERIJE))) {
				// Za muzeje id je fiksno i uvek 8
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/kulturnivodic?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId
										+ "&id=8&date=" + date));
			}

			// KULTURNI CENTRI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_KULTURNI_CENTRI))) {
				// Za kulturne centre id je fiksno i uvek 9
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/kulturnivodic?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId
										+ "&id=9&date=" + date));
			}

			// BIBLIOTEKE
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BIBLIOTEKE))) {
				// Za biblioteke centre id je fiksno i uvek 11
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/kulturnivodic?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId
										+ "&id=11&date=" + date));
			}

			// CITY ZOOM
			// ZNAMENITOSTI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_ZNAMENITOSTI))) {
				// Za znamenitosti id je fiksno i uvek 12
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=12"));
			}
			// INspiracija
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_INSPIRACIJA))) {
				// Za inspiraciju id je fiksno i uvek 13
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/events?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId
										+ "&id=13&date=" + date));
			}

			// SMESTAJ
			// HOTELI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_HOTELI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=21"));
			}
			// APARTMANI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_APARTMANI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=22"));
			}
			// MOTELI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_MOTELI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=23"));
			}
			// HOSTELI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_HOSTELI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=24"));
			}
			// SOBE
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_SOBE))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=25"));
			}
			// KAMPOVI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_KAMPOVI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=26"));
			}
			// RENTA A LOCAL
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_RENT_LOCAL))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=371"));
			}
			// RENTA A STAN
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_RENT_STAN))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=372"));
			}

			// PREVOZ
			// BUS
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BUS))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=27"));
			}
			// TAXI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_TAXI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=28"));
			}
			// RENTA A CAR
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_RENT_CAR))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=30"));
			}
			// RENTA A SCUTER
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_RENT_SCUTER))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=31"));
			}
			// AVIO
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_AVIO))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=359"));
			}
			// VOZ
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_VOZ))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=29"));
			}
			// RENA A BOT
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_RENT_BOAT))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=360"));
			}

			// SHOPPING
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_SHOPPING))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=20"));
			}

			// KES
			// BANKE
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BANKE))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=32"));
			}
			// ATM
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_ATM))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=33"));
			}
			// MENJACNICE
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_MENJACNICE))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=34"));
			}

			// WIFI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_WI_FI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=17"));
			}

			// MEDICO
			// BOLNICE
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BOLNICE))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=37"));
			}
			// APOTEKE
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_APOTEKE))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=36"));
			}
			// ORDINACIJE
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_ORDINACIJE))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=35"));
			}
			// GAS
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_GAS))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=19"));
			}

			// ICE I PICE
			// CAFEI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_CAFEI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=356"));
			}
			// RESTORANI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_RESTORANI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=357"));
			}
			// FAST FOOD
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_FAST_FOOD))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=358"));
			}

			// WELNESS i SPA
			// WELNESS
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_WELLNESS))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=368"));
			}
			// SPA
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_SPA))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=369"));
			}
			// BEAUTY
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BEAUTY))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=370"));
			}

			// NIGHTLIFE
			// KLUBOVI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_KLUBOVI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=38"));
			}
			// BAROVI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BAROVI))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=39"));
			}
			// EVENTS
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_EVENTS))) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/events?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId
										+ "&id=3&date=" + date));
				Log.d("EVENT_URL", Constant.MAIN_URL
										+ "service/events?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId
										+ "&id=3&date=" + date);
			}
			// RAINBOW MUST 2 DO
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_MUST_TO_DO)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=373"));
			}
			// RAINBOW CAFEI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_CAFEI)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=377"));
			}
			// RAINBOW RESTORANT
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_RESTORANI)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=378"));
			}
			// RAINBOW BAROVI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BAROVI)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=363"));
			}
			// RAINBOW KLUBOVI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_KLUBOVI)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=379"));
			}
			// RAINBOW BAROVI
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BAROVI)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=380"));
			}
			// RAINBOW EVENTS
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_EVENTS)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "/service/events?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=381"));
			}
			// RAINBOW WELLNES
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_WELLNESS)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=382"));
			}
			// RAINBOW SPA
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_SPA)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=383"));
			}
			// RAINBOW BEAUTY
			else if (title.equalsIgnoreCase(ComponentInstance
					.getTitleString(ComponentInstance.STRING_BEAUTY)
					+ "_RAINBOW")) {
				DataContainer
						.getInstance()
						.getDataItemList()
						.put(title + date,
								parseItems(Constant.MAIN_URL
										+ "service/companies?seckey=zoom&country="
										+ drzavaId + "&city=" + gradId
										+ "&language=" + jezikId + "&id=384"));
			}

			else {
				return null;
			}
		}

		return DataContainer.getInstance().getDataItemList().get(title + date);

	}

	public static List<DataItem> parseTaxiSms(String countryId,
			String languageId, String cityId) {

		if (!DataContainer.getInstance().getTaxiSmsList().isEmpty()) {

			return DataContainer.getInstance().getTaxiSmsList();

		}

		else {

			List<DataItem> dataItemList = new ArrayList<DataItem>();

			JSONArray datItemJsonArray = null;

			JsonParser jParser = new JsonParser();

			// getting JSON Object from URL
			JSONObject json = jParser.getJSONFromUrl(Constant.MAIN_URL
					+ "service/taxisms?seckey=zoom&country=" + countryId
					+ "&city=" + cityId + "&language=" + languageId);
			{

				try {
					// Getting Array of Contacts
					datItemJsonArray = json.getJSONArray("data");

					// looping through All Contacts
					for (int i = 0; i < datItemJsonArray.length(); i++) {

						JSONObject pom = datItemJsonArray.getJSONObject(i);

						// Storing each json item in variable
						// Set parsing variable into news item and add it in
						// list

						DataItem dataItem = new DataItem();

						if (pom.has("name")) {
							dataItem.setTitle(pom.getString("name"));
						}

						if (pom.has("id")) {
							dataItem.setId(pom.getString("id"));
						}
						if (pom.has("logo")) {
							dataItem.setImage(pom.getString("logo"));
						}

						if (pom.has("phone1")) {
							dataItem.setPhone1(pom.getString("phone1"));
						}

						if (pom.has("phone2")) {
							dataItem.setPhone2(pom.getString("phone2"));
						}

						if (pom.has("phone3")) {
							dataItem.setPhone3(pom.getString("phone3"));
						}

						dataItemList.add(dataItem);
					}
				} catch (JSONException e) {
				}

				DataContainer.getInstance().setTaxiSmsList(dataItemList);

				return dataItemList;
			}
		}
	}
}
