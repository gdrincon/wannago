package net.jaumebalmes.grincon17.wannago.models;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import net.jaumebalmes.grincon17.wannago.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class PlaceApi {

    private final String MAPS_BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
    private final String INPUT_ATTRIBUTE = "?input=";
    private final String KEY_ATTRIBUTE = "&key=";

    public ArrayList<String> autoComplete(String input, Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        HttpsURLConnection connection = null;
        StringBuilder jsonResult = new StringBuilder();

        try {
            String stringBuilder = MAPS_BASE_URL + INPUT_ATTRIBUTE + input +
                    KEY_ATTRIBUTE + context.getString(R.string.google_maps_key);
            URL url = new URL(stringBuilder);
            connection = (HttpsURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            int read;
            char [] buffer = new char[1024];

            while ((read = inputStreamReader.read(buffer)) != -1) {
                jsonResult.append(buffer, 0, read);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonResult.toString());
            JSONArray prediction = jsonObject.getJSONArray("predictions");
            for(int i = 0; i < prediction.length(); i++) {
                arrayList.add(prediction.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return arrayList;
    }
}
