package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ckeswani on 9/16/15.
 */
public interface DBManager {
    public Connection getConnection();
    public List<Pair<String, Integer>> getAllCountriesWithPopulation(Connection c) throws SQLException;
    public List<Pair<String, Integer>> createListFromRS (ResultSet rs) throws SQLException;


}
