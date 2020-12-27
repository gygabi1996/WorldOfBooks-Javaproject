package main.java.entity.list;

import main.java.entity.Listing;
import main.java.entity.ListingStatus;
import main.java.entity.Location;
import main.java.entity.Marketplace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityList {
    public Map<Listing,Map<String,Boolean>> listingList = new HashMap<>();
    public Set<Location> locationList = new HashSet<>();
    public Set<ListingStatus> listingStatuseList = new HashSet<>();
    public Set<Marketplace> marketplaceList = new HashSet<>();
}
