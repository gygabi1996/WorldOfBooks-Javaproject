package main.java.dao;

import main.java.entity.Location;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface LocationDao {
    public Set<Location> getAllLocations();
    public void insertLocation(Location location, Connection connection) throws SQLException;
}
