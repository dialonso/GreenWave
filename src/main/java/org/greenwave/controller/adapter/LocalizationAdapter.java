package org.greenwave.controller.adapter;

import java.lang.reflect.Type;

import org.greenwave.model.Localization;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


/*
 * This class is used to deserialize the Localization class to get an instance of localization from json geocoding purposes
 * This works for GOOGLE MAP API RESULTS ONLY
 */
public class LocalizationAdapter implements JsonDeserializer<Localization>{

	@Override
	public Localization deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		
		JsonObject jobject = arg0.getAsJsonObject();
		Localization loc = new Localization();
		
		JsonArray jsonArrayOne = jobject.get("results").getAsJsonArray();
		jobject = jsonArrayOne.get(0).getAsJsonObject();

		JsonArray jsonResultsArray = jobject.get("address_components").getAsJsonArray();
	    
		for (int i = 0; i < jsonResultsArray.size(); i++)
		{
			JsonObject resultsJsonObj = jsonResultsArray.get(i).getAsJsonObject();
			JsonArray jsonTypesArray = resultsJsonObj.get("types").getAsJsonArray();
			String type = jsonTypesArray.get(0).getAsString();

			// department
			if (type.equals("administrative_area_level_2"))
				loc.setDepartment(resultsJsonObj.get("long_name").getAsString());
			// region
			else if (type.equals("administrative_area_level_1"))
				loc.setRegion(resultsJsonObj.get("long_name").getAsString());
			// country
			else if (type.equals("country"))
				loc.setCountry(resultsJsonObj.get("long_name").getAsString());			
		}
		
		String address = jobject.get("formatted_address").getAsString();
		loc.setAddress(address);
		
		JsonObject resultsJsonObj = jobject.get("geometry").getAsJsonObject();
		resultsJsonObj = resultsJsonObj.get("location").getAsJsonObject();
		
		loc.setLatitude(resultsJsonObj.get("lat").getAsDouble());
		loc.setLongitude(resultsJsonObj.get("lng").getAsDouble());
		
		return loc;
	}

}

