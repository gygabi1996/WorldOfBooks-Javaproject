package main.java.api;

import main.java.entity.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LocationApiHandler {

    public static Set<Location> stringToLocationList(String jsonString){
        System.out.println("Getting 'locations' from API");
        Set<Location> locations = new HashSet<>();

        JSONArray array = new JSONArray(jsonString);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonO = array.getJSONObject(i);

            UUID id = UUID.fromString(jsonO.getString("id"));
            String manager_name = jsonO.getString("manager_name");
            String phone = jsonO.getString("phone");
            String address_primary = jsonO.getString("address_primary");

            String address_secondary = null;
            if (!jsonO.isNull("address_secondary")) {
                address_secondary = jsonO.getString("address_secondary");
            }

            String country = jsonO.getString("country");
            String town = jsonO.getString("town");

            String postal_code = null;
            if(!jsonO.isNull("postal_code")){
                postal_code = jsonO.getString("postal_code");
            }

            locations.add(new Location(id,manager_name,phone,address_primary,address_secondary,country,town,postal_code));
        }
        return locations;
    }
}
