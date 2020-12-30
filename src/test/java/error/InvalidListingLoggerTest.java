package test.java.error;

import main.java.api.ListingApiHandler;
import main.java.api.ListingStatusApiHandler;
import main.java.api.LocationApiHandler;
import main.java.api.MarketplaceApiHandler;
import main.java.entity.list.EntityList;
import main.java.error.InvalidListingLogger;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class InvalidListingLoggerTest {

    @Test
    public static void Test() throws IOException {
        /**
         * Create an invalid entity list (invalid listing price in listing fields)
         */
        EntityList entityList = new EntityList();
        String locationSting = "[{\"id\":\"0fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"manager_name\":\"Ware Reville\",\"phone\":\"744-150-6044\",\"address_primary\":\"5 Nobel Drive\",\"address_secondary\":null,\"country\":\"Finland\",\"town\":\"Iitti\",\"postal_code\":\"47520\"}]";
        String listingStatusString = "[{\"id\":1,\"status_name\":\"ACTIVE\"}]";
        String marketplaceString = "[{\"id\":1,\"marketplace_name\":\"Amazon\"}]";
        String InvalidListingPriceString = "[{\"id\":\"64747042-63f4-4cb8-82f7-97a3be23020f\",\"title\":\"Bambarra Groundnut\",\"description\":\"Vigna subterranea (L.) Verdc.\",\"location_id\":\"0fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"listing_price\":-126.38,\"currency\":\"GBP\",\"quantity\":28,\"listing_status\":1,\"marketplace\":1,\"upload_time\":\"10/26/2018\",\"owner_email_address\":\"mallanmba@mac.com\"}]";
        entityList.locationList = LocationApiHandler.stringToLocationList(locationSting);
        entityList.listingStatuseList = ListingStatusApiHandler.stringToListingStatusList(listingStatusString);
        entityList.marketplaceList = MarketplaceApiHandler.stringToMarketplaceList(marketplaceString);
        entityList.listingList = ListingApiHandler.stringToListingList(InvalidListingPriceString,entityList);


        InvalidListingLogger.saveInvalidListingsToCSV(entityList);

        /**
         * Check the file is created
         */
        File file = new File("log/importLog.csv");
        assertNotNull(file);
        Scanner myReader = new Scanner(file);

        /**
         * Check the rows are correct (first row is the fields, 2 is the invalid datas)
         */
        String expectedFirstRow = "ListingId;MarketplaceName;InvalidField";
        String expectedSecondRow = "64747042-63f4-4cb8-82f7-97a3be23020f;Amazon;listing_price";

        String data = myReader.nextLine();
        assertEquals(expectedFirstRow, data.trim());

        data = myReader.nextLine();
        assertEquals(expectedSecondRow, data.trim());

        myReader.close();
    }
}
