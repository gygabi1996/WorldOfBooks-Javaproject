package main.java.dao;

import main.java.entity.ListingStatus;
import main.java.entity.Location;
import main.java.entity.Marketplace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class MarketplaceDaoImpl implements MarketplaceDao{
    @Override
    public void insertMarketplaces(Set<Marketplace> marketplaces, Connection connection) throws SQLException {
        String sql = "insert into marketplaces(marketplace_id, marketplace_name) VALUES (?,?)";

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql);

        for (Marketplace marketplace : marketplaces) {
            preparedStatement.setInt(1, marketplace.getId());
            preparedStatement.setString(2, marketplace.getMarketplace_name());

            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }
}
