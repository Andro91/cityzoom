package zoom.city.android.main.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.data.DataItem;

public class ParserSearch {

	public static List<DataItem> parseSimple(String searchText, String country,String city,
			String languaga) {

		List<DataItem> dataItemList = new ArrayList<DataItem>();

		JSONArray datItemJsonArray = null;

		JsonParser jParser = new JsonParser();

		// getting JSON Object from URL
		JSONObject json = null;
		try {
			json = jParser.getJSONFromUrl(Constant.MAIN_URL
					+ "service/basicsearch?seckey=zoom&country=" + country+ "&city=" + city
					+ "&language=" + languaga + "&title="
					+ URLEncoder.encode(searchText, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		
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
					
					if (pom.has("type")) {
						dataItem.setCategory(pom.getString("type"));
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

	public static List<DataItem> parseAdvance(String searchText,
			String country, String languaga, String street, String city,
			String township, String category, String subcategory) {

		List<DataItem> dataItemList = new ArrayList<DataItem>();

		JSONArray datItemJsonArray = null;

		JsonParser jParser = new JsonParser();

		// getting JSON Object from URL
		JSONObject json = null;
		try{
		Log.d("URL", Constant.MAIN_URL
				+ "service/advancedsearch?seckey=zoom&country=" + country
				+ "&language=" + languaga + "&title="
				+ URLEncoder.encode(searchText,"UTF-8") + "&street="
				+ URLEncoder.encode(street,"UTF-8") + "&cityid=" + city + "&township="
				+ URLEncoder.encode(township,"UTF-8") + "&category="
				+ URLEncoder.encode(category,"UTF-8") + "&subcategory="
				+ URLEncoder.encode(subcategory,"UTF-8"));
		json = jParser.getJSONFromUrl(Constant.MAIN_URL
				+ "service/advancedsearch?seckey=zoom&country=" + country
				+ "&language=" + languaga + "&title="
				+ URLEncoder.encode(searchText,"UTF-8") + "&street="
				+ URLEncoder.encode(street,"UTF-8") + "&cityid=" + city + "&township="
				+ URLEncoder.encode(township,"UTF-8") + "&category="
				+ URLEncoder.encode(category,"UTF-8") + "&subcategory="
				+ URLEncoder.encode(subcategory,"UTF-8"));
		}catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
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

					dataItem.setTitleIndicator("1");

					dataItemList.add(dataItem);
				}
			} catch (JSONException e) {
			}

			return dataItemList;
		}
	}

}
