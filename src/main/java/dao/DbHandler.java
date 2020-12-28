package main.java.dao;

import main.java.entity.list.EntityList;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

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
            properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/config/dbconfig.properties"));

            Class.forName(properties.getProperty(DB_DRIVER_CLASS));

            // Setup the connection with the DB
            connection = DriverManager.getConnection
                    (properties.getProperty(DB_URL)
                    ,properties.getProperty(DB_USERNAME)
                    ,properties.getProperty(DB_PASSWORD));

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

            connection.close();

            /*
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from feedback.comments");
            writeResultSet(resultSet);

             */

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
