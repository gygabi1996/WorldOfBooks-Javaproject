package main.java.dao;

import main.java.entity.Listing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Set;

public class ListingDaoImpl implements ListingDao{
    @Override
    public void insertListings(Set<Listing> listings, Connection connection) throws SQLException {
        String sql = "insert into listings" +
                "(listing_id, title, description,inventory_item_location_id, listing_price, currency,quantity, listing_status_id,marketplace_id, upload_time, owner_email_address) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql);
        for (Listing listing : listings) {
            //format the uploadTime
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String uploadTime = formatter.format(listing.getUpload_time());

            // Set parameters
            preparedStatement.setString(1, listing.getId().toString());
            preparedStatement.setString(2, listing.getTitle());
            preparedStatement.setString(3, listing.getDescription());
            preparedStatement.setString(4, listing.getInventory_item_location_id().toString());
            preparedStatement.setDouble(5, listing.getListing_price());
            preparedStatement.setString(6, listing.getCurrency());
            preparedStatement.setInt(7, listing.getQuantity());
            preparedStatement.setInt(8, listing.getListing_status().getId());
            preparedStatement.setInt(9, listing.getMarketplace().getId());
            preparedStatement.setString(10, uploadTime);
            preparedStatement.setString(11, listing.getOwner_email_address());

            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }
}
