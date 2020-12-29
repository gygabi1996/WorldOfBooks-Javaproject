package main.java.dao;

import main.java.entity.ListingStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface ListingStatusDao {
    public void insertListingStatuses(Set<ListingStatus> listingStatuses, Connection connection) throws SQLException;
}
