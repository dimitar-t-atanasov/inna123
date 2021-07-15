package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This DBManager implementation provides a connection to the database containing population data.
 * <p>
 * Created by ckeswani on 9/16/15.
 */
public class DBManagerImpl implements DBManager {

    Statement stmt = null;
    ResultSet rs = null;

    @Override
    public Connection getConnection() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources/data/citystatecountry.db");
            System.out.println("Opened database successfully");

        } catch (ClassNotFoundException cnf) {
            System.out.println("could not load driver");
        } catch (SQLException sqle) {
            System.out.println("sql exception:" + sqle.getStackTrace());
        }
        return c;
    }

    @Override
    public List<Pair<String, Integer>> getAllCountriesWithPopulation(Connection c) {

        List<Pair<String, Integer>> listFromRS = null;
        try {
            stmt = c.createStatement();

            rs = stmt.executeQuery("SELECT c.CountryName , SUM(Population)\n" +
                    "FROM `Country` AS c\n" +
                    "JOIN `State` AS s ON s.CountryId = c.CountryId\n" +
                    "JOIN `City` ON City.StateId = s.StateId\n" +
                    "GROUP BY c.CountryId;");

            listFromRS = createListFromRS(rs);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                    rs = null;
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    stmt = null;
                }
            }
        }
        return listFromRS;
    }

    @Override
    public List<Pair<String, Integer>> createListFromRS(ResultSet rs) throws SQLException {
        List<Pair<String, Integer>> result = new ArrayList<>();

        while (rs.next()) {
            String country = rs.getString(1);
            int population = (int) Double.parseDouble(rs.getString(2));
            result.add(new ImmutablePair<String, Integer>(country, population));
        }
        return result;
    }

}
