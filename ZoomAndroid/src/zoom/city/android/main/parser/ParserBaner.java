package zoom.city.android.main.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.data.DataItem;

public class ParserBaner {

	public static List<DataItem> parseBigBaner(String title, String country,
			String city) {

		List<DataItem> dataItemList = new ArrayList<DataItem>();

		JSONArray datItemJsonArray = null;

		JsonParser jParser = new JsonParser();

		// getting JSON Object from URL
		JSONObject json = jParser.getJSONFromUrl(Constant.MAIN_URL
				+ "service/banner?seckey=zoom&country=" + country + "&city="
				+ city + "&id=" + title);
		
		{

			try {
				
				datItemJsonArray = json.getJSONArray("data");
				
				//JSONArray banner = datItemJsonArray.getJSONObject(0).getJSONArray("banners");

				for (int i = 0; i < datItemJsonArray.length(); i++) {

					//Log.d("LENGTH", ""+datItemJsonArray.length());
					
					JSONObject pom = datItemJsonArray.getJSONObject(i);
					//JSONArray banner = pom.getJSONArray("banners");

					// Storing each json item in variable
					// Set parsing variable into news item and add it in list

					DataItem dataItem = new DataItem();

					if (pom.has("image")) {
						dataItem.setImage(pom.getString("image"));
					}
					if(pom.has("id_company")){
						dataItem.setCompanyId(pom.getString("id_company"));
					}
					Log.d("IMAGE", pom.getString("image"));
					Log.d("ID_COMPANY", pom.getString("id_company"));
					
					dataItemList.add(dataItem);
				}
			} catch (JSONException e) {
			}

			return dataItemList;
		}
	}

	public static List<DataItem> parseSmallBaner(String title, String country,
			String city,String language) {
		
		List<DataItem> dataItemList = new ArrayList<DataItem>();

		JSONArray datItemJsonArray = null;

		JsonParser jParser = new JsonParser();

		// getting JSON Object from URL
		JSONObject json = jParser.getJSONFromUrl(Constant.MAIN_URL
				+ "service/smallbanner?seckey=zoom&country=" + country + "&city="
				+ city + "&id=" + title+"&language="+language);
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

					if (pom.has("image")) {
						dataItem.setImage(pom.getString("image"));
					}
					if (pom.has("title")) {
						dataItem.setTitle(pom.getString("title"));
					}
					if (pom.has("category")) {
						dataItem.setCategory(pom.getString("category"));
					}
					
					dataItemList.add(dataItem);
				}
			} catch (JSONException e) {
			}

			return dataItemList;
		}
	}
}
