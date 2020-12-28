package main.java.dao;

import main.java.entity.ListingStatus;
import main.java.entity.Marketplace;

import java.util.Set;

public interface MarketplaceDao {
    public Set<Marketplace> getAllMarketplace();
    public void insertMarketplace(Marketplace marketplace);
}
