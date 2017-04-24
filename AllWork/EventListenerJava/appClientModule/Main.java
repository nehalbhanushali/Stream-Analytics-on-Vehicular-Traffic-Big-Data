import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			HttpClient client = new DefaultHttpClient();

			HttpGet request = new HttpGet(
					"https://maps.googleapis.com/maps/api/geocode/json?address=Boston&key=AIzaSyD00P0SYkGq0u02feNgh_6hHvnSQGWRK84");
			String lat=null;
			String lon=null;
			HttpResponse response = null;
			response = client.execute(request);
			BufferedReader rd = null;
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			String result = "";
			while ((line = rd.readLine()) != null) {

				result += line;
			}

			JSONObject myObject = null;
			myObject = new JSONObject(result);

			HashMap keyArrayList = new HashMap();

			JSONArray jsonArray = myObject.getJSONArray("results");
			
			JSONObject location=null;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject innerObj = jsonArray.getJSONObject(i);
				for (Iterator it = innerObj.keys(); it.hasNext();) {
					String key = (String) it.next();
					
					if(key.equals("geometry"))
					{
						JSONObject geo= (JSONObject) innerObj.get("geometry");
						JSONObject loc=(JSONObject) geo.get("location");
						lat=loc.getString("lat");
						lon=loc.getString("lng");
					}
					
				}
			}
			if(lat!=null && lon!=null)
			{
				System.out.println("Latitude: "+lat);
				System.out.println("Longitude: "+lon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}