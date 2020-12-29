package main.java.api;

import main.java.entity.ListingStatus;
import main.java.entity.Marketplace;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class MarketplaceApiHandler {

    public static Set<Marketplace> stringToMarketplaceList(String jsonString){
        System.out.println("Getting 'marketplaces' from API");
        Set<Marketplace> marketplaces = new HashSet<>();

        JSONArray array = new JSONArray(jsonString);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonO = array.getJSONObject(i);

            Integer id = jsonO.getInt("id");
            String marketplace_name = jsonO.getString("marketplace_name");

            marketplaces.add(new Marketplace(id,marketplace_name));
        }
        return marketplaces;
    }
}
