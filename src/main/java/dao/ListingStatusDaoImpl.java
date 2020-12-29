package main.java.dao;

import main.java.entity.ListingStatus;
import main.java.entity.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class ListingStatusDaoImpl implements ListingStatusDao{
    @Override
    public void insertListingStatuses(Set<ListingStatus> listingStatuses, Connection connection) throws SQLException {
        String sql = "insert into listing_statuses(listing_status_id, status_name) VALUES (?,?)";

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql);
        for (ListingStatus listingStatus : listingStatuses) {
            preparedStatement.setInt(1, listingStatus.getId());
            preparedStatement.setString(2, listingStatus.getStatusName());

            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }
}
