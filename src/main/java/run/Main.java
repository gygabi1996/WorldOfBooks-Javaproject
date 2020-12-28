package main.java.run;

import main.java.api.*;
import main.java.dao.DbHandler;
import main.java.entity.ListingStatus;
import main.java.entity.Location;
import main.java.entity.Marketplace;
import main.java.entity.list.EntityList;
import main.java.error.InvalidListingLogger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws Exception {
        EntityList entityList = new EntityList();

        /**
         * Get the Location JSON from API
         */
        //URL url = new URL("https://my.api.mockaroo.com/location?key=63304c70");
        //String locationSting = MainApiHandler.apiGetRequest(url);
        String locationSting = "[{\"id\":\"0fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\"manager_name\":\"Ware Reville\",\"phone\":\"744-150-6044\",\"address_primary\":\"5 Nobel Drive\",\"address_secondary\":null,\"country\":\"Finland\",\"town\":\"Iitti\",\"postal_code\":\"47520\"},{\"id\":\"ac867cd8-c321-42ab-b179-01a4b8301a9c\",\"manager_name\":\"Dell Radnedge\",\"phone\":\"606-838-8913\",\"address_primary\":\"67 Larry Way\",\"address_secondary\":null,\"country\":\"Russia\",\"town\":\"Firovo\",\"postal_code\":\"161453\"},{\"id\":\"bfcd9dc2-3ead-470b-9846-5a3f6d8f32a1\",\"manager_name\":\"Eudora McCurlye\",\"phone\":\"450-492-7816\",\"address_primary\":\"8133 Carpenter Junction\",\"address_secondary\":null,\"country\":\"Vietnam\",\"town\":\"Hưng Yên\",\"postal_code\":null},{\"id\":\"5ae22efb-f875-4134-a03d-6402efa31dc5\",\"manager_name\":\"Mehetabel Keemar\",\"phone\":\"809-501-8029\",\"address_primary\":\"2263 Corscot Alley\",\"address_secondary\":null,\"country\":\"China\",\"town\":\"Meipu\",\"postal_code\":null},{\"id\":\"1d551b07-fd16-4760-88a3-4aa4fda13a2b\",\"manager_name\":\"Katusha Keling\",\"phone\":\"775-505-1259\",\"address_primary\":\"05769 Summerview Alley\",\"address_secondary\":null,\"country\":\"Portugal\",\"town\":\"Baleal\",\"postal_code\":\"2520-005\"},{\"id\":\"52ea143e-cb45-43af-981e-92cedb89f7a8\",\"manager_name\":\"Lucais Castro\",\"phone\":\"585-835-1468\",\"address_primary\":\"1415 1st Way\",\"address_secondary\":null,\"country\":\"China\",\"town\":\"Jintian\",\"postal_code\":null},{\"id\":\"c47ab7b4-3a94-4218-8e0b-4bada1fb05c0\",\"manager_name\":\"Coralyn Salomon\",\"phone\":\"900-146-8288\",\"address_primary\":\"6 Carpenter Road\",\"address_secondary\":null,\"country\":\"Canada\",\"town\":\"Medicine Hat\",\"postal_code\":\"T1C\"},{\"id\":\"5c3a4cf8-1ac4-456d-ba85-a782ff475256\",\"manager_name\":\"Winne Maggs\",\"phone\":\"165-635-9622\",\"address_primary\":\"225 Prairieview Place\",\"address_secondary\":null,\"country\":\"Russia\",\"town\":\"Volokonovka\",\"postal_code\":\"457151\"},{\"id\":\"013cc380-c18b-4c87-8043-16e14f7127de\",\"manager_name\":\"Germain Dutch\",\"phone\":\"295-697-0605\",\"address_primary\":\"76 Ramsey Park\",\"address_secondary\":null,\"country\":\"Ukraine\",\"town\":\"Kolochava\",\"postal_code\":null},{\"id\":\"5249f33c-fadf-44d9-ab70-471df29c20a6\",\"manager_name\":\"Jannelle Clethro\",\"phone\":\"527-234-5225\",\"address_primary\":\"98833 Scoville Street\",\"address_secondary\":null,\"country\":\"Guatemala\",\"town\":\"Jocotán\",\"postal_code\":\"20005\"}]";
        /**
         * Convert the location JSON to a List and save it into the entityList's locationList
         */
        entityList.locationList = LocationApiHandler.stringToLocationList(locationSting);

        /**
         * Get the ListingStatus JSON from API
         */
        //URL url = new URL("https://my.api.mockaroo.com/listingStatus?key=63304c70");
        //String ListingStatusString = MainApiHandler.apiGetRequest(url);
        String ListingStatusString = "[{\"id\":1,\"status_name\":\"ACTIVE\"},{\"id\":2,\"status_name\":\"SCHEDULED\"},{\"id\":3,\"status_name\":\"CANCELLED\"}]";
        entityList.listingStatuseList = ListingStatusApiHandler.stringToListingStatusList(ListingStatusString);

        for (ListingStatus listingStatus:entityList.listingStatuseList) {
            //System.out.println(listingStatus.getStatus_name());
        }

        /**
         * Get the ListingStatus JSON from API
         */
        //URL url = new URL("https://my.api.mockaroo.com/marketplace?key=63304c70");
        //String MarketplaceString = MainApiHandler.apiGetRequest(url);
        String MarketplaceString = "[{\"id\":1,\"marketplace_name\":\"Amazon\"},{\"id\":2,\"marketplace_name\":\"Ebay\"},{\"id\":3,\"marketplace_name\":\"Aliexpress\"}]";
        entityList.marketplaceList = MarketplaceApiHandler.stringToMarketplaceList(MarketplaceString);

        for (Marketplace marketplace:entityList.marketplaceList) {
            //System.out.println(marketplace.getMarketplace_name());
        }

        /**
         * Get the Listing JSON from API
         */
        //url = new URL("https://my.api.mockaroo.com/listing?key=63304c70");
        //String listingSting = MainApiHandler.apiGetRequest(url);

        String path = "F:\\Munka\\WoB\\javaprojekt\\json\\listing.JSON";
        String listingSting = "";

        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            String content = lines.collect(Collectors.joining(System.lineSeparator()));
            listingSting = content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        entityList.listingList = ListingApiHandler.stringToListingList(listingSting,entityList);

        //InvalidListingLogger.saveInvalidListingsToCSV(entityList);

        DbHandler.saveEntitiesToDatabase(entityList);
    }
}
