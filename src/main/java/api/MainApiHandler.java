package main.java.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
