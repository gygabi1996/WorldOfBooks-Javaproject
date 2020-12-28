package main.java.dao;

import main.java.entity.Listing;

import java.util.Set;

public interface ListingDao {
    public Set<Listing> getAllListings();
    public void insertListing(Listing listing);
}
