package main.java.api;

import main.java.entity.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MainApiHandler {

    public static String apiGetRequest(URL url) throws Exception{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String readLine;
            while ((readLine = in.readLine()) != null){
                response.append(readLine);
            } in.close();
        return response.toString();
        }
        else {
            System.out.println("GET REQUEST TO " + url.toString() + " NOT WORKED");
        }
        return null;
    }
}
