package main.java.dao;

import main.java.entity.Location;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface LocationDao {
    public void insertLocations(Set<Location> locations, Connection connection) throws SQLException;
}
