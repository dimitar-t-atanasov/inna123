package com.quickbase;

import com.quickbase.devint.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.ImmutablePair;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
    public static void main( String args[] ) {
        System.out.println("Starting.");
        System.out.print("Getting DB Connection...");

        DBManager dbm = new DBManagerImpl();
        Connection c = dbm.getConnection();
        if (null == c ) {
            System.out.println("failed.");
            System.exit(1);
        }

        ConcreteStatService myConcreteStatService = new ConcreteStatService();
        List<Pair<String, Integer>> dataFromList = myConcreteStatService.GetCountryPopulations();

        List<Pair<String, Integer>> listFromDB = null;
        try {
            listFromDB = dbm.getAllCountriesWithPopulation(c);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Merger merger = new MergerImpl();
        List<Pair<String, Integer>> allCountries = merger.merge(listFromDB, dataFromList);

        List<Pair<String, Integer>> sorted = allCountries.stream().sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList());
        for (Pair<String, Integer> country : sorted) {
            System.out.println(country);
        }


    }
}