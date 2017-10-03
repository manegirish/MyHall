package com.technoindians.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.technoindians.network.JsonArrays_;
import com.technoindians.phonebook.Family_;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GirishM on 03-10-2017.
 */

public class Contact_ {

    public static ArrayList<Family_> parseFamilies(String response, Context _context, String TAG) {
        ArrayList<Family_> familyArrayList = new ArrayList<>();
        if (response == null) {
            Family_ family_ = new Family_();
            family_.setStatus(12);
            familyArrayList.add(family_);
            return familyArrayList;
        }

        if (Error_.noData(response, JsonArrays_.GET_FAMILIES, _context) == 2) {
            Family_ family_ = new Family_();
            family_.setStatus(2);
            familyArrayList.add(family_);
            return familyArrayList;
        }
        JsonArray jsonArray = GetJson_.array(response, JsonArrays_.GET_FAMILIES);

        if (jsonArray != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Family_>>() {
            }.getType();
            return gson.fromJson(GetJson_.array(response, JsonArrays_.GET_FAMILIES).toString(), listType);
        } else {
            Family_ family_ = new Family_();
            family_.setStatus(11);
            familyArrayList.add(family_);
            return familyArrayList;
        }
    }
}
