package org.greenwave.service;

import org.greenwave.controller.adapter.DataAdapter;
import org.greenwave.model.Data;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

@Service
public class DataService {
	
	public String datasToJson(List<Data> datas) {  
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(Data.class, new DataAdapter()).create();
	    return gson.toJson(datas);
	} 

}
