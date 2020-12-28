package main.java.dao;

import main.java.entity.ListingStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class ListingStatusDaoImpl implements ListingStatusDao{
    @Override
    public Set<ListingStatus> getAllListingStatuses() {
        return null;
    }

    @Override
    public void insertListingStatus(ListingStatus listingStatus, Connection connection) throws SQLException {
        String sql = "insert into listing_statuses(listing_status_id, status_name) VALUES (?,?)";

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql);

        // Set parameters
        preparedStatement.setInt(1, listingStatus.getId());
        preparedStatement.setString(2, listingStatus.getStatusName());

        preparedStatement.executeUpdate();
    }
}
