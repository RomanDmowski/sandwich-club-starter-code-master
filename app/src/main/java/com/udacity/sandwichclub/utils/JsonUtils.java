package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonName = jsonObject.getJSONObject("name");
            String mainName = jsonName.getString("mainName");

            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsArray = jsonName.getJSONArray("alsoKnownAs");
            for (int i=0; i< alsoKnownAsArray.length();i++){
                String knownName=alsoKnownAsArray.getString(i);
                alsoKnownAs.add(knownName);
            }

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");

            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");

            for (int i=0; i< ingredientsArray.length();i++) {
                String ingredient=ingredientsArray.getString(i);
                ingredients.add(ingredient);
            }

            Sandwich sandwich = new Sandwich( mainName, alsoKnownAs,placeOfOrigin,description,image,ingredients);
            return  sandwich;
        }
        catch (JSONException j){
            return null;
        }

    }
}
