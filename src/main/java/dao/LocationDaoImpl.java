package main.java.dao;

import main.java.entity.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class LocationDaoImpl implements LocationDao{
    @Override
    public Set<Location> getAllLocations() {
        return null;
    }

    @Override
    public void insertLocation(Location location, Connection connection) throws SQLException {
        String sql = "insert into locations(location_id, manager_name, phone, address_primary, address_secondary, country, town, postal_code)" +
                "VALUES (?,?,?,?,?,?,?,?);";

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql);

        // Set parameters
        preparedStatement.setString(1, location.getId().toString());
        preparedStatement.setString(2, location.getManager_name());
        preparedStatement.setString(3, location.getPhone());
        preparedStatement.setString(4, location.getAddress_primary());
        preparedStatement.setString(5, location.getAddress_secondary());
        preparedStatement.setString(6, location.getCountry());
        preparedStatement.setString(7, location.getTown());
        preparedStatement.setString(8, location.getPostal_code());

        preparedStatement.executeUpdate();
    }
}
