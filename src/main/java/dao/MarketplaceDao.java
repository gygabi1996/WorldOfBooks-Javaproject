package main.java.dao;

import main.java.entity.ListingStatus;
import main.java.entity.Marketplace;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface MarketplaceDao {
    public void insertMarketplaces(Set<Marketplace> marketplaces, Connection connection) throws SQLException;
}
