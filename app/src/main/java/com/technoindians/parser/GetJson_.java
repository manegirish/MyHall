package com.technoindians.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Trojan on 10/1/2017.
 */

public class GetJson_ {

    public static JsonArray array(String response, String jsonName) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
        if (jsonObject.has(jsonName)) {
            return jsonObject.getAsJsonArray(jsonName);
        } else {
            return null;
        }
    }

    public static JsonObject object(String response, String jsonName) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
        return jsonObject.getAsJsonObject(jsonName);
    }

    public static JsonObject json(String response) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(response).getAsJsonObject();
    }

}
