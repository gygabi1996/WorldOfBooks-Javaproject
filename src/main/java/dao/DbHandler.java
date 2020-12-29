package main.java.dao;

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

    public static void saveEntitiesToDatabase(EntityList entityList){
        try {
            createConnection();

            /*
            //Insert Locations
            locationDao = new LocationDaoImpl();

            entityList.locationList.forEach(location -> {
                try {
                    locationDao.insertLocation(location,connection);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            //Insert ListingStatuses
            listingStatusDao = new ListingStatusDaoImpl();

            entityList.listingStatuseList.forEach(listingStatus -> {
                try {
                    listingStatusDao.insertListingStatus(listingStatus,connection);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            //Insert Marketplaces
            marketplaceDao = new MarketplaceDaoImpl();

            entityList.marketplaceList.forEach(marketplace -> {
                try {
                    marketplaceDao.insertMarketplace(marketplace,connection);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            //Insert Listings
            listingDao = new ListingDaoImpl();

            entityList.listingList.forEach((listing,fieldStatus) -> {
                // Check the listing is valid.
                Boolean isListingValid = true;
                for (Map.Entry<String, Boolean> listingField : fieldStatus.entrySet()) {
                    if(!listingField.getValue()){
                        isListingValid = false;
                    }
                }
                // if the listing is valid
                if (isListingValid){
                    try {
                        listingDao.insertListing(listing,connection);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });


             */

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createConnection() throws IOException, ClassNotFoundException, SQLException {
        properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config/dbconfig.properties"));

        Class.forName(properties.getProperty(DB_DRIVER_CLASS));

        // Setup the connection with the DB
        connection = DriverManager.getConnection
                (properties.getProperty(DB_URL)
                ,properties.getProperty(DB_USERNAME)
                ,properties.getProperty(DB_PASSWORD));
    }

    public static ReportEntity getReportDatasFromDatabase() throws SQLException, IOException, ClassNotFoundException {
        createConnection();

        // Get datas from database
        Integer totalListingCount = ReportDao.getTotalListingCount(connection);
        Map<String,Integer> ebayMap = ReportDao.getEbayListings(connection);
        Map<String,Integer> amazonMap = ReportDao.getAmazonListings(connection);
        String bestLister = ReportDao.getBestLister(connection);
        Map<String,Map<String,Integer>> ebayMonthlyEbay = ReportDao.getEbayListingsPerMonth(connection);
        Map<String,Map<String,Integer>> amazonMonthlyEbay = ReportDao.getAmazonListingsPerMonth(connection);
        Map<String,String> bestListerMonthly = ReportDao.getBestListerPerMonth(connection);

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
