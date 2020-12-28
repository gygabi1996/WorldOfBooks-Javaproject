package main.java.dao;

import main.java.entity.Marketplace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class MarketplaceDaoImpl implements MarketplaceDao{
    @Override
    public Set<Marketplace> getAllMarketplace() {
        return null;
    }

    @Override
    public void insertMarketplace(Marketplace marketplace, Connection connection) throws SQLException {
        String sql = "insert into marketplaces(marketplace_id, marketplace_name) VALUES (?,?)";

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql);

        // Set parameters
        preparedStatement.setInt(1, marketplace.getId());
        preparedStatement.setString(2, marketplace.getMarketplace_name());

        preparedStatement.executeUpdate();
    }
}
