package main.java.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ReportDao {

    public static Integer getTotalListingCount(Connection connection) throws SQLException {
        String sql = "select COUNT(*) from listings";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        return resultSet.getInt(1);
    }

    public static Map<String,Number> getEbayListings(Connection connection) throws SQLException {
        Map<String,Number> ebayMap = new LinkedHashMap<>();

        String sql = "select COUNT(*), SUM(li.listing_price), AVG(li.listing_price)\n" +
                "from listings li inner join marketplaces m\n" +
                "  on li.marketplace_id = m.marketplace_id\n" +
                "where m.marketplace_name LIKE 'Ebay'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        ebayMap.put("listingCount",resultSet.getInt(1));
        ebayMap.put("listingPriceSum",resultSet.getDouble(2));
        ebayMap.put("listingPriceAvg",resultSet.getDouble(3));

        return ebayMap;
    }

    public static Map<String,Number> getAmazonListings(Connection connection) throws SQLException {
        Map<String,Number> amazonMap = new LinkedHashMap<>();

        String sql = "select COUNT(*), SUM(li.listing_price), AVG(li.listing_price)\n" +
                "from listings li inner join marketplaces m\n" +
                "  on li.marketplace_id = m.marketplace_id\n" +
                "where m.marketplace_name LIKE 'Amazon'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        amazonMap.put("listingCount",resultSet.getInt(1));
        amazonMap.put("listingPriceSum",resultSet.getDouble(2));
        amazonMap.put("listingPriceAvg",resultSet.getDouble(3));

        return amazonMap;
    }

    public static String getBestLister(Connection connection) throws SQLException {
        String sql = "select owner_email_address\n" +
                "from listings\n" +
                "GROUP BY owner_email_address\n" +
                "order by COUNT(*) desc\n" +
                "LIMIT 1";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        return resultSet.getString(1);
    }

    public static Map<String,Map<String,Number>> getEbayListingsPerMonth(Connection connection) throws SQLException {
        Map<String,Map<String,Number>> ebayList = new LinkedHashMap<>();

        String sql = "SELECT COUNT(*), SUM(li.listing_price), AVG(li.listing_price),\n" +
                "       CONCAT(CONCAT(YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), '/'), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y'))) as date1\n" +
                "FROM listings li inner join marketplaces m\n" +
                "  on li.marketplace_id = m.marketplace_id\n" +
                "where m.marketplace_name LIKE 'Ebay'\n" +
                "GROUP BY CONCAT(CONCAT(YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), '/'), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y')))\n" +
                "ORDER BY YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y'))";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            Map<String,Number> ebayMap = new LinkedHashMap<>();

            ebayMap.put("listingCount",resultSet.getInt(1));
            ebayMap.put("listingPriceSum",resultSet.getDouble(2));
            ebayMap.put("listingPriceAvg",resultSet.getDouble(3));

            ebayList.put(resultSet.getString(4),ebayMap);
        }

        return ebayList;
    }

    public static Map<String,Map<String,Number>> getAmazonListingsPerMonth(Connection connection) throws SQLException {
        Map<String,Map<String,Number>> amazonList = new LinkedHashMap<>();

        String sql = "SELECT COUNT(*), SUM(li.listing_price), AVG(li.listing_price),\n" +
                "       CONCAT(CONCAT(YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), '/'), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y'))) as date1\n" +
                "FROM listings li inner join marketplaces m\n" +
                "  on li.marketplace_id = m.marketplace_id\n" +
                "where m.marketplace_name LIKE 'Amazon'\n" +
                "GROUP BY CONCAT(CONCAT(YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), '/'), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y')))\n" +
                "ORDER BY YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y'))";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            Map<String,Number> amazonMap = new HashMap<>();

            amazonMap.put("listingPriceAvg",resultSet.getInt(1));
            amazonMap.put("listingCount",resultSet.getDouble(2));
            amazonMap.put("listingPriceSum",resultSet.getDouble(3));

            amazonList.put(resultSet.getString(4),amazonMap);
        }

        return amazonList;
    }

    public static Map<String,String> getBestListerPerMonth(Connection connection) throws SQLException {
        Map<String,String> listerList = new LinkedHashMap<>();

        String sql = "select CONCAT(CONCAT(YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), '/'), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y'))) as date1,\n" +
                "       (select owner_email_address\n" +
                "        from listings\n" +
                "        where CONCAT(CONCAT(YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), '/'), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y'))) like date1\n" +
                "        GROUP BY owner_email_address\n" +
                "        order by COUNT(*) desc\n" +
                "        limit 1) as email_piece\n" +
                "from listings\n" +
                "GROUP BY date1\n" +
                "ORDER BY YEAR(STR_TO_DATE(upload_time,'%m/%d/%Y')), MONTH(STR_TO_DATE(upload_time,'%m/%d/%Y'))";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            listerList.put(resultSet.getString(1),resultSet.getString(2));
        }
        return listerList;
    }
}
