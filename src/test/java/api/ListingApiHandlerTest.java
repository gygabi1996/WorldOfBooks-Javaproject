package test.java.api;

import main.java.api.*;
import main.java.entity.list.EntityList;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListingApiHandlerTest {

    @Test
    public static void Test() {
        /**
         * Check the the ListingApiAndler returns depend on valid or invalid JSON string.
         */
        EntityList entityList = new EntityList();
        /**
         * Add location, listingStatus and marketplace because reference issues
         */
        String locationSting = "[{\"id\":\"0fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"manager_name\":\"Ware Reville\",\"phone\":\"744-150-6044\",\"address_primary\":\"5 Nobel Drive\",\"address_secondary\":null,\"country\":\"Finland\",\"town\":\"Iitti\",\"postal_code\":\"47520\"}]";
        String listingStatusString = "[{\"id\":1,\"status_name\":\"ACTIVE\"}]";
        String marketplaceString = "[{\"id\":1,\"marketplace_name\":\"Amazon\"}]";
        entityList.locationList = LocationApiHandler.stringToLocationList(locationSting);
        entityList.listingStatuseList = ListingStatusApiHandler.stringToListingStatusList(listingStatusString);
        entityList.marketplaceList = MarketplaceApiHandler.stringToMarketplaceList(marketplaceString);

        entityList.listingList.clear();
        String validListingString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"0fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":126.38,\"currency\":\"GBP\",\"quantity\":28,\"listing_status\":1,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(validListingString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            fieldNames.forEach((fieldName,isvalid) -> {
                assertTrue(isvalid);
            });
        });

        entityList.listingList.clear();
        String invalidLocationIdString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"afe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":126.38,\"currency\":\"GBP\",\"quantity\":28,\"listing_status\":1,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(invalidLocationIdString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            assertFalse(fieldNames.get("location_id"));
        });

        entityList.listingList.clear();
        String negativeListingPriceString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"afe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":-126.38,\"currency\":\"GBP\",\"quantity\":28,\"listing_status\":1,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(negativeListingPriceString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            assertFalse(fieldNames.get("listing_price"));
        });

        entityList.listingList.clear();
        String threeDecimalListingPriceString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"afe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":126.378,\"currency\":\"GBP\",\"quantity\":28,\"listing_status\":1,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(threeDecimalListingPriceString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            assertFalse(fieldNames.get("listing_price"));
        });

        entityList.listingList.clear();
        String invalidCurrencyString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"afe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":126.37,\"currency\":\"GBPX\",\"quantity\":28,\"listing_status\":1,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(invalidCurrencyString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            assertFalse(fieldNames.get("currency"));
        });

        entityList.listingList.clear();
        String negativeQuantityString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"afe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":126.37,\"currency\":\"GBP\",\"quantity\":-28,\"listing_status\":1,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(negativeQuantityString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            assertFalse(fieldNames.get("quantity"));
        });

        entityList.listingList.clear();
        String invalidListingStatusString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"afe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":126.37,\"currency\":\"GBP\",\"quantity\":28,\"listing_status\":0,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(invalidListingStatusString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            assertFalse(fieldNames.get("listing_status"));
        });

        entityList.listingList.clear();
        String invalidMarketplaceString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"afe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":126.37,\"currency\":\"GBP\",\"quantity\":28,\"listing_status\":1,\"marketplace\":0,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(invalidMarketplaceString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            assertFalse(fieldNames.get("marketplace"));
        });

        entityList.listingList.clear();
        String invalidEmailString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"afe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":126.37,\"currency\":\"GBP\",\"quantity\":28,\"listing_status\":1,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@maccom\"}]";
        entityList.listingList = ListingApiHandler.stringToListingList(invalidEmailString,entityList);
        entityList.listingList.forEach((listing,fieldNames) -> {
            assertFalse(fieldNames.get("owner_email_address"));
        });
    }
}
