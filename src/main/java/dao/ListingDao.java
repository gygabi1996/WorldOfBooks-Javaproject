package main.java.dao;

import main.java.entity.Listing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface ListingDao {
    public Set<Listing> getAllListings();
    public void insertListing(Listing listing, Connection connection) throws SQLException;
}
