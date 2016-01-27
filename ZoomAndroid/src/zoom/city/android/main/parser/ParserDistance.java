package zoom.city.android.main.parser;

import org.json.JSONException;
import org.json.JSONObject;

public class ParserDistance {
	
	public static String uzmiDistancu(String orig1, String orig2, String dest1, String dest2){
	JSONObject distanca = null;
	String rastojanje = "";

	JsonParser jParser = new JsonParser();

	// getting JSON Object from URL
	JSONObject json = jParser.getJSONFromUrl("https://maps.googleapis.com/maps/" 
		+ "api/distancematrix/json?origins="+orig1+"+"+orig2+"&destinations="
		+ dest1 +"+" + dest2);
	{
	try{
		distanca = json.getJSONArray("rows").getJSONObject(0).
				getJSONArray("elements").getJSONObject(0).getJSONObject("distance");
		rastojanje = distanca.getString("text");
	}
	catch (JSONException e) {
   		e.printStackTrace();
   	}
	return rastojanje;
	}
	}
}
