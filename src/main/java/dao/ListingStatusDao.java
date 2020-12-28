package main.java.dao;

import main.java.entity.ListingStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface ListingStatusDao {
    public Set<ListingStatus> getAllListingStatuses();
    public void insertListingStatus(ListingStatus listingStatus, Connection connection) throws SQLException;
}
