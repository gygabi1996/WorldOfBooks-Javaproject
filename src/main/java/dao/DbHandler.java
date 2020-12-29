package main.java.dao;

import main.java.entity.Listing;
import main.java.entity.ReportEntity;
import main.java.entity.list.EntityList;
import main.java.report.ReportHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DbHandler {
    private static final String DB_DRIVER_CLASS="driver.class.name";
    private static final String DB_USERNAME="db.username";
    private static final String DB_PASSWORD="db.password";
    private static final String DB_URL="db.url";

    private static Connection connection = null;
    private static Properties properties = null;

    private static ListingDaoImpl listingDao;
    private static LocationDaoImpl locationDao;
    private static ListingStatusDaoImpl listingStatusDao;
    private static MarketplaceDaoImpl marketplaceDao;

    private static void createConnection() throws IOException, ClassNotFoundException, SQLException {
        properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config/config.properties"));

        Class.forName(properties.getProperty(DB_DRIVER_CLASS));

        // Setup the connection with the DB
        connection = DriverManager.getConnection
                (properties.getProperty(DB_URL)
                        ,properties.getProperty(DB_USERNAME)
                        ,properties.getProperty(DB_PASSWORD));
    }

    public static void saveEntitiesToDatabase(EntityList entityList){
        try {
            System.out.println("Create database connection to save entities");
            createConnection();

            //Insert Locations
            System.out.println("Insert Locations into the database");
            locationDao = new LocationDaoImpl();
            locationDao.insertLocations(entityList.locationList, connection);

            //Insert ListingStatuses
            System.out.println("Insert ListingStatuses into the database");
            listingStatusDao = new ListingStatusDaoImpl();
            listingStatusDao.insertListingStatuses(entityList.listingStatuseList, connection);

            //Insert Marketplaces
            System.out.println("Insert Marketplaces into the database");
            marketplaceDao = new MarketplaceDaoImpl();
            marketplaceDao.insertMarketplaces(entityList.marketplaceList, connection);

            //Insert Listings
            System.out.println("Insert Listings into the database");
            listingDao = new ListingDaoImpl();

            Set<Listing> localListingList = new LinkedHashSet<>();
            entityList.listingList.forEach((listing,fieldStatus) -> {
                // Check the listing is valid.
                Boolean isListingValid = true;
                for (Map.Entry<String, Boolean> listingField : fieldStatus.entrySet()) {
                    if(!listingField.getValue()){
                        isListingValid = false;
                    }
                }
                // if the listing is valid add it to the local list
                if (isListingValid){
                    localListingList.add(listing);
                }
            });
            listingDao.insertListings(localListingList,connection);

            System.out.println("Close database connection");
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ReportEntity getReportDatasFromDatabase() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Create database connection to get report datas");
        createConnection();

        // Get datas from database
        System.out.println("Getting report datas from database");
        Integer totalListingCount = ReportDao.getTotalListingCount(connection);
        Map<String,Integer> ebayMap = ReportDao.getEbayListings(connection);
        Map<String,Integer> amazonMap = ReportDao.getAmazonListings(connection);
        String bestLister = ReportDao.getBestLister(connection);
        Map<String,Map<String,Integer>> ebayMonthlyEbay = ReportDao.getEbayListingsPerMonth(connection);
        Map<String,Map<String,Integer>> amazonMonthlyEbay = ReportDao.getAmazonListingsPerMonth(connection);
        Map<String,String> bestListerMonthly = ReportDao.getBestListerPerMonth(connection);

        // Close database connection
        System.out.println("Close database connection");
        connection.close();

        // Convert monthly datas to ReportEntity
        Map<String,Map<String,Object>> montlyList = new LinkedHashMap<>();
        bestListerMonthly.forEach((date,lister) -> {
            Map<String,Object> month = new LinkedHashMap<>();
            month.put("total_ebay_listing_count",ebayMonthlyEbay.get(date).get("listingCount"));
            month.put("total_ebay_listing_price",ebayMonthlyEbay.get(date).get("listingPriceSum"));
            month.put("average_ebay_listing_price",ebayMonthlyEbay.get(date).get("listingPriceAvg"));
            month.put("total_amazon_listing_count",amazonMonthlyEbay.get(date).get("listingCount"));
            month.put("total_amazon_listing_price",amazonMonthlyEbay.get(date).get("listingPriceSum"));
            month.put("average_amazon_listing_price",amazonMonthlyEbay.get(date).get("listingPriceAvg"));
            month.put("best_lister_email_address",lister);
            montlyList.put(date,month);
        });

        // Create reportEntity
        ReportEntity reportEntity = new ReportEntity
            (totalListingCount,
            ebayMap.get("listingCount"), ebayMap.get("listingPriceSum"), ebayMap.get("listingPriceAvg"),
            amazonMap.get("listingCount"), amazonMap.get("listingPriceSum"), amazonMap.get("listingPriceAvg"),
            bestLister,montlyList);

        return reportEntity;
    }
}
