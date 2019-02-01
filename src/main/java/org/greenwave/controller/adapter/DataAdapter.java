package org.greenwave.controller.adapter;

import java.lang.reflect.Type;

import org.greenwave.model.Data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/*
 * This class is used to serialize the Data class in Json format for client-side processing in JS/Jquery etc...
 */
public class DataAdapter implements JsonSerializer<Data> {
	
	@Override
	public JsonElement serialize(Data data, Type arg1, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id_data", data.getId_data());
        jsonObject.addProperty("complete_address", data.getLocalization_data().getAddress());
        jsonObject.addProperty("latitude", data.getLocalization_data().getLatitude());
        jsonObject.addProperty("longitude", data.getLocalization_data().getLongitude());
        jsonObject.addProperty("value", data.getValue());
        jsonObject.addProperty("category", "");
        jsonObject.addProperty("date", data.getMeasure_date().toString());
        jsonObject.addProperty("type", data.getType());

        return jsonObject;   
	}
}

