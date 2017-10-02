package com.technoindians.parser;

import android.content.Context;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Trojan on 10/1/2017.
 */

public class GetJson_ {

    public static JsonArray array(String response, String jsonName, String TAG, Context context) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        return jsonObject.getAsJsonArray(jsonName);
    }

}
