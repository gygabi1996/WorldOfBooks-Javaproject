package main.java.api;

import main.java.entity.Listing;
import main.java.entity.ListingStatus;
import main.java.entity.Location;
import main.java.entity.Marketplace;
import main.java.entity.list.EntityList;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class ListingApiHandler {
    public static Map<Listing, Map<String,Boolean>> stringToListingList(String jsonString, EntityList entityList) {
        Map<Listing, Map<String,Boolean>> listings = new HashMap<>();

        JSONArray array = new JSONArray(jsonString);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonO = array.getJSONObject(i);
            Map<String,Boolean> invalidFieldList = new HashMap<>();

            // Listing's id
            UUID id = null;
            if (!jsonO.isNull("id")) {
                id = UUID.fromString(jsonO.getString("id"));
                invalidFieldList.put("id",true);
            } else {
                System.out.println("Missing \"id\" at Listing. Listing id: " + id);
                invalidFieldList.put("id",false);
            }

            // Listing's title
            String title = null;
            if (!jsonO.isNull("title")) {
                title = jsonO.getString("title");
                invalidFieldList.put("title",true);
            } else {
                System.out.println("Missing \"title\" at Listing. Listing id: " + id);
                invalidFieldList.put("title",false);
            }

            // Listing's description
            String description = null;
            if (!jsonO.isNull("description")) {
                description = jsonO.getString("description");
                invalidFieldList.put("description",true);
            } else {
                System.out.println("Missing \"description\" at Listing. Listing id: " + id);
                invalidFieldList.put("description",false);
            }

            // Listing's location_id
            UUID locationId = null;
            if (!jsonO.isNull("location_id")) {
                locationId = UUID.fromString(jsonO.getString("location_id"));

                Boolean isReferenceSuccess = validateLocationId(entityList, locationId);
                if(!isReferenceSuccess){
                    invalidFieldList.put("location_id",false);
                    System.out.println("Wrong reference on \"location_id\" at Listing. Listing id: " + id);
                } else {
                    invalidFieldList.put("location_id",true);
                }
            } else {
                invalidFieldList.put("location_id",false);
                System.out.println("Missing \"location_id\" at Listing. Listing id: " + id);
            }

            // Listing's listing_price
            Number listingPrice = null;
            if (!jsonO.isNull("listing_price")) {
                listingPrice = jsonO.getNumber("listing_price");
                Boolean isValid = true;

                if(listingPrice.intValue() < 0){
                    System.out.println("Number is less than 0, \"listing_price\" at Listing. Listing id: " + id);
                    invalidFieldList.put("listing_price",false);
                    isValid = false;
                }

                Boolean isValidateSuccess = validateListingPrice(listingPrice);
                if(!isValidateSuccess){
                    System.out.println("Number have more or less than 2 decimal, \"listing_price\" at Listing. Listing id: " + id);
                    invalidFieldList.put("listing_price",false);
                    isValid = false;
                }

                if(isValid){
                    invalidFieldList.put("listing_price",true);
                }

            } else {
                invalidFieldList.put("listing_price",false);
                System.out.println("Missing \"listing_price\" at Listing. Listing id: " + id);
            }

            // Listing's currency
            String currency = null;
            if (!jsonO.isNull("currency")) {
                currency = jsonO.getString("currency");
                if (currency.length() != 3){
                    System.out.println("String is longer or shorter than 3 character, \"currency\" at Listing. Listing id: " + id);
                    invalidFieldList.put("currency",false);
                } else {
                    invalidFieldList.put("currency",true);
                }
            } else {
                invalidFieldList.put("currency",false);
                System.out.println("Missing \"currency\" at Listing. Listing id: " + id);
            }

            // Listing's quantity
            Number quantity = null;
            if (!jsonO.isNull("quantity")) {
                quantity = jsonO.getNumber("quantity");
                if(quantity.intValue() < 0){
                    System.out.println("Number is less than 0, \"quantity\" at Listing. Listing id: " + id);
                    invalidFieldList.put("quantity",false);
                } else {
                    invalidFieldList.put("quantity",true);
                }
            } else {
                invalidFieldList.put("quantity",false);
                System.out.println("Missing \"quantity\" at Listing. Listing id: " + id);
            }

            // Listing's listing_status
            ListingStatus listingStatus = new ListingStatus();
            Integer listingStatusId;
            if (!jsonO.isNull("listing_status")) {
                listingStatusId = jsonO.getInt("listing_status");

                Boolean isReferenceSuccess = validateListingStatusId(entityList, listingStatusId);
                if(!isReferenceSuccess){
                    invalidFieldList.put("listing_status",false);
                    System.out.println("Wrong reference on \"listing_status\" at Listing. Listing id: " + id);
                } else {
                    // if the reference is success, set the fields of the marketplace
                    for (ListingStatus listingStatusLocal: entityList.listingStatuseList) {
                        if (listingStatusLocal.getId() == listingStatusId){
                            listingStatus.setId(listingStatusLocal.getId());
                            listingStatus.setStatusName(listingStatusLocal.getStatusName());
                        }
                    }
                    invalidFieldList.put("listing_status",true);
                }
            } else {
                invalidFieldList.put("listing_status",false);
                System.out.println("Missing \"listing_status\" at Listing. Listing id: " + id);
            }

            // Listing's marketplace
            Marketplace marketplace = new Marketplace();
            Integer marketplaceId = null;
            if (!jsonO.isNull("marketplace")) {
                marketplaceId = jsonO.getInt("marketplace");
                Boolean isReferenceSuccess = validateMarketplaceId(entityList, marketplaceId);
                if(!isReferenceSuccess){
                    invalidFieldList.put("marketplace",false);
                    System.out.println("Wrong reference on \"marketplace\" at Listing. Listing id: " + id);
                } else {
                    // if the reference is success, set the fields of the marketplace
                    for (Marketplace marketplaceLocal: entityList.marketplaceList) {
                        if (marketplaceLocal.getId() == marketplaceId){
                            marketplace.setId(marketplaceLocal.getId());
                            marketplace.setMarketplace_name(marketplaceLocal.getMarketplace_name());
                        }
                    }
                    invalidFieldList.put("marketplace",true);
                }
            } else {
                invalidFieldList.put("marketplace",false);
                System.out.println("Missing \"marketplace\" at Listing. Listing id: " + id);
            }

            // Listing's updateTime
            Date uploadTime = null;
            if (!jsonO.isNull("upload_time")) {
                String uploadTimeString = jsonO.getString("upload_time");
                uploadTime = validateUploadTime(uploadTime, uploadTimeString);
                invalidFieldList.put("upload_time",true);
            } else {
                System.out.println("Wrong or missing \"upload_time\" at Listing. Listing id: " + id);
                invalidFieldList.put("upload_time",false);
            }

            // Listing's owner_email_address
            String ownerEmailAddress = null;
            if (!jsonO.isNull("owner_email_address")) {
                ownerEmailAddress = jsonO.getString("owner_email_address");

                EmailValidator emailValidator = EmailValidator.getInstance();
                invalidFieldList.put("owner_email_address",true);
                if(!emailValidator.isValid(ownerEmailAddress)){
                    System.out.println("Wrong format, \"owner_email_address\"  at Listing. Listing id: " + id);
                    invalidFieldList.put("owner_email_address",false);
                }
            } else {
                invalidFieldList.put("owner_email_address",false);
                System.out.println("Missing \"owner_email_address\" at Listing. Listing id: " + id);
            }

            // Make a new listing
            Listing listing = new Listing(id,title,description,locationId,
                    listingPrice != null ? listingPrice.doubleValue() : null,
                    currency,
                    quantity != null ? quantity.intValue() : null,
                    listingStatus, marketplace, uploadTime,ownerEmailAddress);

            // Add the listing and the valid status to the list. (if isValid the listing is valid, if not, there was a wrong or missing field)
            listings.put(listing,invalidFieldList);
        }
        return listings;
    }

    private static Boolean validateListingPrice(Number listingPrice) {
        Double localValue = listingPrice.doubleValue();
        // check the length of the decimal part. if it is not 2
        String localValueString = String.valueOf(localValue);
        String[] parts = localValueString.split("\\.");
        if((parts[1].length() == 2)){
            return true;
        }
        return false;
    }

    private static Date validateUploadTime(Date uploadTime, String uploadTimeString) {
        // Set the date
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            uploadTime = formatter.parse(uploadTimeString);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return uploadTime;
    }

    private static Boolean validateLocationId(EntityList entityList, UUID location_id) {
        // Check the locations_id reference
        Boolean referenceSuccess = false;
        for (Location location: entityList.locationList) {
            // compare the location_id with all location's id in the entityList.locationList list, and it one of them is equal with the location_id the make the referenceSuccess to true
            if (location.getId().toString().equals(location_id.toString())){
                referenceSuccess = true;
            }
        }
        // If there is no reference (not found the location_id in the location list), mark the Listing invalid
        if(!referenceSuccess){
            return false;
        }
        return true;
    }

    private static Boolean validateListingStatusId(EntityList entityList, int listingStatusId) {
        // Check the listing_status reference
        Boolean referenceSuccess = false;
        for (ListingStatus listingStatus: entityList.listingStatuseList) {
            if (listingStatus.getId() == listingStatusId){
                referenceSuccess = true;
            }
        }
        // If there is no reference mark the Listing invalid
        if(!referenceSuccess){
            return false;
        }
        return true;
    }

    private static Boolean validateMarketplaceId(EntityList entityList, int marketplaceId) {
        // Check the marketplaceId reference
        Boolean referenceSuccess = false;
        for (Marketplace marketplace: entityList.marketplaceList) {
            if (marketplace.getId() == marketplaceId){
                referenceSuccess = true;
            }
        }
        // If there is no reference, mark the Listing invalid
        if(!referenceSuccess){
            return false;
        }
        return true;
    }
}
