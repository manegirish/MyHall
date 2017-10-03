package com.technoindians.parser;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.technoindians.variales.Constants;

/**
 * @author Girish Mane(girishmane8692@sagesurfer.com)
 *         Created on 03-10-2017
 *         Last Modified on 03-10-2017
 */

class Error_ {

    static int noData(String response, String jsonName, Context _context) {
        JsonArray jsonArray = GetJson_.array(response, jsonName);
        if (jsonArray != null) {
            JsonObject object = jsonArray.get(0).getAsJsonObject();
            if (object.has(Constants.STATUS)) {
                int result = object.get(Constants.STATUS).getAsInt();
                if (result == 2) {
                    return result;
                }
            }
        }
        return 0;
    }
}
