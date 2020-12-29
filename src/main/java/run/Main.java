package main.java.run;

import main.java.api.*;
import main.java.dao.DbHandler;
import main.java.entity.ListingStatus;
import main.java.entity.Location;
import main.java.entity.Marketplace;
import main.java.entity.ReportEntity;
import main.java.entity.list.EntityList;
import main.java.error.InvalidListingLogger;
import main.java.ftp.FtpHandler;
import main.java.report.ReportHandler;

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
         * Get the Location JSON from API and save it into the local list
         */
        URL url = new URL("https://my.api.mockaroo.com/location?key=63304c70");
        String locationSting = MainApiHandler.apiGetRequest(url);
        entityList.locationList = LocationApiHandler.stringToLocationList(locationSting);

        /**
         * Get the ListingStatus JSON from API and save it into the local list
         */
        url = new URL("https://my.api.mockaroo.com/listingStatus?key=63304c70");
        String ListingStatusString = MainApiHandler.apiGetRequest(url);
        entityList.listingStatuseList = ListingStatusApiHandler.stringToListingStatusList(ListingStatusString);

        /**
         * Get the ListingStatus JSON from API and save it into the local list
         */
        url = new URL("https://my.api.mockaroo.com/marketplace?key=63304c70");
        String MarketplaceString = MainApiHandler.apiGetRequest(url);
        entityList.marketplaceList = MarketplaceApiHandler.stringToMarketplaceList(MarketplaceString);

        /**
         * Get the Listing JSON from API and save it into the local list
         */
        url = new URL("https://my.api.mockaroo.com/listing?key=63304c70");
        String listingSting = MainApiHandler.apiGetRequest(url);
        entityList.listingList = ListingApiHandler.stringToListingList(listingSting,entityList);

        /**
         * Save invalid listings to CSV in "log" directory (log/importLog.csv)
         */
        InvalidListingLogger.saveInvalidListingsToCSV(entityList);

        /**
         * Save valid entities (Listing,Location,ListingStatus,Marketplace) into the database (Database configuration in config.properties)
         */
        DbHandler.saveEntitiesToDatabase(entityList);

        /**
         * Get report datas from database
         */
        ReportEntity reportEntity = DbHandler.getReportDatasFromDatabase();

        /**
         * Create report.JSON using database report datas
         */
        ReportHandler.createReport(reportEntity);

        /**
         * Upload report.JSON to the local FTP server (FTP configuration in config.properties)
         */
        FtpHandler.uploadReportToFtp();
    }
}
