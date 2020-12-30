package test.java.dao;

import main.java.api.ListingApiHandler;
import main.java.api.ListingStatusApiHandler;
import main.java.api.LocationApiHandler;
import main.java.api.MarketplaceApiHandler;
import main.java.dao.DbHandler;
import main.java.entity.ReportEntity;
import main.java.entity.list.EntityList;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DaoTest {

    @Test
    public static void Test() throws SQLException, IOException, ClassNotFoundException {
        /**
         * Create an valid entity list and insert into the database
         */
        EntityList entityList = new EntityList();
        String locationSting = "[{\"id\":\"2fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"manager_name\":\"Test Manager\",\"phone\":\"11-111-1111\",\"address_primary\":\"Test Address\",\"address_secondary\":null,\"country\":\"Test Country\",\"town\":\"Test Town\",\"postal_code\":\"11111\"}]";
        String listingStatusString = "[{\"id\":99,\"status_name\":\"TESTSTATUS\"}]";
        String marketplaceString = "[{\"id\":99,\"marketplace_name\":\"Ebay\"}]";
        String InvalidListingPriceString = "[{\"id\":\"14747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Test Listing Title\",\"description\":\"Test Listing Description\",\"location_id\":\"2fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":111.11,\"currency\":\"TES\",\"quantity\":11,\"listing_status\":99,\"marketplace\":99,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"test@test.com\"}]";
        entityList.locationList = LocationApiHandler.stringToLocationList(locationSting);
        entityList.listingStatuseList = ListingStatusApiHandler.stringToListingStatusList(listingStatusString);
        entityList.marketplaceList = MarketplaceApiHandler.stringToMarketplaceList(marketplaceString);
        entityList.listingList = ListingApiHandler.stringToListingList(InvalidListingPriceString,entityList);

        DbHandler.saveEntitiesToDatabase(entityList);

        String locationSting2 = "[{\"id\":\"3fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"manager_name\":\"Test Manager2\",\"phone\":\"11-111-1111\",\"address_primary\":\"Test Address2\",\"address_secondary\":null,\"country\":\"Test Country\",\"town\":\"Test Town\",\"postal_code\":\"11111\"}]";
        String listingStatusString2 = "[{\"id\":98,\"status_name\":\"TESTSTATUS2\"}]";
        String marketplaceString2 = "[{\"id\":98,\"marketplace_name\":\"Amazon\"}]";
        String InvalidListingPriceString2 = "[{\"id\":\"34747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Test Listing Title2\",\"description\":\"Test Listing Description2\",\"location_id\":\"3fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":222.22,\"currency\":\"TES\",\"quantity\":11,\"listing_status\":98,\"marketplace\":98,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"test2@test.com\"}]";
        entityList.locationList = LocationApiHandler.stringToLocationList(locationSting2);
        entityList.listingStatuseList = ListingStatusApiHandler.stringToListingStatusList(listingStatusString2);
        entityList.marketplaceList = MarketplaceApiHandler.stringToMarketplaceList(marketplaceString2);
        entityList.listingList = ListingApiHandler.stringToListingList(InvalidListingPriceString2,entityList);

        DbHandler.saveEntitiesToDatabase(entityList);

        ReportEntity reportEntity = DbHandler.getReportDatasFromDatabase();
        assertNotNull(reportEntity);

        Integer expectedTotalCount = 2;
        Integer expectedEbayTotalCount = 1;
        Double expectedEbayTotalSum = 111.11;
        Double expectedEbayAvg = 111.11;
        Integer expectedAmazonTotalCount = 1;
        Double expectedAmazonTotalSum = 222.22;
        Double expectedAmazonAvg = 222.22;

        assertEquals(expectedTotalCount,reportEntity.getTotal_listing_count());
        assertEquals(expectedEbayTotalCount,reportEntity.getTotal_ebay_listing_count());
        assertEquals(expectedEbayTotalSum,reportEntity.getTotal_ebay_listing_price());
        assertEquals(expectedEbayAvg,reportEntity.getAverage_ebay_listing_price());
        assertEquals(expectedAmazonTotalCount,reportEntity.getTotal_amazon_listing_count());
        assertEquals(expectedAmazonTotalSum,reportEntity.getTotal_amazon_listing_price());
        assertEquals(expectedAmazonAvg,reportEntity.getAverage_amazon_listing_price());
    }
}
