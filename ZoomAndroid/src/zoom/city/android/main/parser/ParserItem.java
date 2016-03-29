package zoom.city.android.main.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import zoom.city.android.main.constant.Constant;
import zoom.city.android.main.data.DataItem;

public class ParserItem {

	public static DataItem parseSendData(String id, String language,String type) {
		Log.d("PARSER", "parseSendData");
		DataItem dataItem = new DataItem();

		HttpURLConnection c = null;
		try {
			URL u = new URL(Constant.MAIN_URL
					+ "service/preview?seckey=zoom&id="+id+"&language="+language+"&type="+type);
	        c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("GET");
	        c.setRequestProperty("Content-length", "0");
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        //c.setConnectTimeout(1000);
	        //c.setReadTimeout(1000);
	        c.connect();
	        int status = c.getResponseCode();
	        
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(Constant.MAIN_URL
//					+ "service/preview?seckey=zoom&id="+id+"&language="+language+"&type="+type);
//
//			HttpResponse httpResponse = httpClient.execute(httpPost);
			// httpResponse.setHeader("Content-Type",
			// "text/xml; charset=UTF-8");
			InputStream inputStream = c.getInputStream();

			// ODAVDE PARSIRANJE ODGOVORA
			// VRACAMO STATUS TAG U TEXT DATAITEM
			// VRACAMO DISLIKE I LIKE U DATAITEM
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "ISO-8859-1"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			inputStream.close();
			String json = sb.toString();

			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject pom = jsonArray.getJSONObject(i);
				
				if(pom.has("microlocation")){
					dataItem.setMicrolocation(pom.getString("microlocation"));
				}
				else{
					dataItem.setMicrolocation("");
				}
				
				if(pom.has("genre")){
					dataItem.setGenre(pom.getString("genre"));
				}
				else{
					dataItem.setGenre("");
				}
				
				if(pom.has("street")){
					dataItem.setStreet(pom.getString("street"));
				}
				else{
					dataItem.setStreet("");
				}
				
				if(pom.has("web")){
					dataItem.setWeb(pom.getString("web"));
				}
				else{
					dataItem.setWeb("");
				}
				
				if(pom.has("date")){
					dataItem.setDate(pom.getString("date"));
				}
				else{
					dataItem.setDate("");
				}
				
				if(pom.has("city")){
					dataItem.setCity(pom.getString("city"));
				}
				else{
					dataItem.setCity("");
				}
				
				if(pom.has("author")){
					dataItem.setAuthor(pom.getString("author"));
				}
				else{
					dataItem.setAuthor("");
				}
				
				if(pom.has("time")){
					dataItem.setTime(pom.getString("time"));
				}
				else{
					dataItem.setTime("");
				}
				
				if(pom.has("image1")){
					dataItem.setImage(pom.getString("image1"));
				}
				else{
					dataItem.setImage("");
				}
				
				if(pom.has("image2")){
					dataItem.setImage2(pom.getString("image2"));
				}
				else{
					dataItem.setImage2("");
				}
				
				if(pom.has("description")){
					dataItem.setDescription(pom.getString("description"));
				}
				else{
					dataItem.setDescription("");
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
				
				if(pom.has("name")){
					dataItem.setTitle(pom.getString("name"));
				}
				else{
					dataItem.setTitle("");
				}
				
				if(pom.has("zona")){
					dataItem.setZona(pom.getString("zona"));
				}
				else{
					dataItem.setZona("");
				}
				
				if(pom.has("logo")){
					dataItem.setLogo(pom.getString("logo"));
				}
				else{
					dataItem.setLogo("");
				}
				
				if(pom.has("fax")){
					dataItem.setFax(pom.getString("fax"));
				}
				else{
					dataItem.setFax("");
				}
				
				if(pom.has("previewtype")){
					dataItem.setPreviewtype(pom.getString("previewtype"));
				}
				else{
					dataItem.setPreviewtype("");
				}
				
				if(pom.has("phone1")){
					dataItem.setPhone1(pom.getString("phone1"));
				}
				else{
					dataItem.setPhone1("");
				}
				
				if(pom.has("phone2")){
					dataItem.setPhone2(pom.getString("phone2"));
				}
				else{
					dataItem.setPhone2("");
				}
				
				if(pom.has("email")){
					dataItem.setEmail(pom.getString("email"));
				}
				else{
					dataItem.setEmail("");
				}
				
				if(pom.has("image3")){
					dataItem.setImage3(pom.getString("image3"));
				}
				else{
					dataItem.setImage3("");
				}
				
				if (pom.has("companyid")) {
					dataItem.setCompanyId(pom.getString("companyid"));
				}
				else{
					dataItem.setCompanyId("");
				}
				
				
				if (pom.has("company")) {
					dataItem.setCompany(pom.getString("company"));
				}
				else{
					dataItem.setCompany("");
				}
				
				if (pom.has("category")) {
					dataItem.setCategory(pom.getString("category"));
				}
				else{
					dataItem.setCategory("");
				}
				
				if (pom.has("official_webpage")) {
					dataItem.setOfficialWebpage(pom.getString("official_webpage"));
				}
				else{
					dataItem.setOfficialWebpage("");
				}
				
				
				//ArrayList<String>  slider = new ArrayList<String>(){{ add(""); }};
				if (pom.has("slider")) {
					if(pom.getJSONArray("slider").length() > 0){
						ArrayList<String>  slider = new ArrayList<String>();
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
						ArrayList<String>  logoslider = new ArrayList<String>();
						JSONArray jArray = pom.getJSONArray("logo_slider");
						for (int j = 0; j < jArray.length(); j++) {
							logoslider.add(jArray.getString(j));
						}
						dataItem.setLogoSlider(logoslider);
					}else{
						dataItem.setLogoSlider(null);
					}
				}
				else{
					dataItem.setLogoSlider(null);
				}
				
				if (pom.has("video")) {
					dataItem.setVideo(pom.getString("video"));
				}
				else{
					dataItem.setVideo("");
				}
				
				if (pom.has("facebook")) {
					dataItem.setShare(pom.getString("facebook"));
				}
				
				if (pom.has("official_facebook")) {
					dataItem.setOfficialFacebook(pom.getString("official_facebook"));
				}
			}

		} catch (UnsupportedEncodingException e) {
			Log.d("PARSER", "ERROR " + e.getMessage());
			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			Log.d("PARSER", "ERROR " + e.getMessage());
//			e.printStackTrace();
		} catch (IOException e) {
			Log.d("PARSER", "ERROR " + e.getMessage());
			e.printStackTrace();
		} catch (JSONException e) {
			Log.d("PARSER", "ERROR " + e.getMessage());
			e.printStackTrace();
		}

		return dataItem;

	}
	
	public static DataItem parseDistData(String id, String language,String type) {

		DataItem dataItem = new DataItem();

		HttpURLConnection c = null;
		try {
			URL u = new URL(Constant.MAIN_URL
					+ "service/preview?seckey=zoom&id="+id+"&language="+language+"&type="+type);
	        c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("GET");
	        c.setRequestProperty("Content-length", "0");
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        //c.setConnectTimeout(1000);
	        //c.setReadTimeout(1000);
	        c.connect();
	        int status = c.getResponseCode();
		

//			HttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(Constant.MAIN_URL
//					+ "service/preview?seckey=zoom&id="+id+"&language="+language+"&type="+type);
//
//			HttpResponse httpResponse = httpClient.execute(httpPost);
			// httpResponse.setHeader("Content-Type",
			// "text/xml; charset=UTF-8");
			InputStream inputStream = c.getInputStream();

			// ODAVDE PARSIRANJE ODGOVORA
			// VRACAMO STATUS TAG U TEXT DATAITEM
			// VRACAMO DISLIKE I LIKE U DATAITEM
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "ISO-8859-1"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			inputStream.close();
			String json = sb.toString();

			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject pom = jsonArray.getJSONObject(i);
				
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
				
				if(pom.has("name")){
					dataItem.setTitle(pom.getString("name"));
				}
				else{
					dataItem.setTitle("");
				}
				
				if (pom.has("companyid")) {
					dataItem.setCompanyId(pom.getString("companyid"));
				}
				else{
					dataItem.setCompanyId("");
				}
				
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataItem;

	}
}
