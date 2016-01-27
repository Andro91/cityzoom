package zoom.city.android.main.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.data.DataItem;

public class ParserMap {

	public static List<DataItem> parse(String type, String cityId,
			String languageId) {

		List<DataItem> dataItemList = new ArrayList<DataItem>();

		JSONArray datItemJsonArray = null;

		JsonParser jParser = new JsonParser();

		// getting JSON Object from URL
		JSONObject json = jParser.getJSONFromUrl(Constant.MAIN_URL
				+ "service/map?seckey=zoom&city=" + cityId + "&language="
				+ languageId + "&" + type + "=1");
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

					if(pom.has("id")){
						dataItem.setId(pom.getString("id"));
					}
					else{
						dataItem.setId("");
					}
					
					if(pom.has("title")){
						dataItem.setTitle(pom.getString("title"));
					}
					else{
						dataItem.setTitle("");
					}
					
					if(pom.has("category")){
						dataItem.setCategory(pom.getString("category"));
					}
					else{
						dataItem.setCategory("");
					}
					
					if(pom.has("type")){
						dataItem.setGenre(pom.getString("type"));
					}
					else{
						dataItem.setGenre("");
					}
					
					if(pom.has("date")){
						dataItem.setDate(pom.getString("date"));
					}
					else{
						dataItem.setDate("");
					}
					
					if(pom.has("address")){
						dataItem.setStreet(pom.getString("address"));
					}
					else{
						dataItem.setStreet("");
					}
					
					
					if(pom.has("x")){
						dataItem.setX(pom.getString("x"));
					}
					else{
						dataItem.setX("");
					}
					
					if(pom.has("y")){
						dataItem.setY(pom.getString("y"));
					}
					else{
						dataItem.setY("");
					}
					
					

					dataItemList.add(dataItem);
				}
			} catch (JSONException e) {
			}

			return dataItemList;
		}
	}

}
