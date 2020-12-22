package main.java.api;

import main.java.entity.ListingStatus;
import main.java.entity.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ListingStatusApiHandler {

    public static Set<ListingStatus> stringToListingStatusList(String jsonString){
        Set<ListingStatus> listingStatuses = new HashSet<>();

        JSONArray array = new JSONArray(jsonString);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonO = array.getJSONObject(i);

            Integer id = jsonO.getInt("id");
            String status_name = jsonO.getString("status_name");

            listingStatuses.add(new ListingStatus(id,status_name));
        }
        return listingStatuses;
    }
}
