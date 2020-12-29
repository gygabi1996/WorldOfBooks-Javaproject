package main.java.dao;

import main.java.entity.Listing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface ListingDao {
    public void insertListings(Set<Listing> listings, Connection connection) throws SQLException;
}
