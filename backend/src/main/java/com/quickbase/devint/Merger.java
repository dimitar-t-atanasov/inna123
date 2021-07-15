package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface Merger {
    public List<Pair<String, Integer>> merge(List<Pair<String, Integer>> first, List<Pair<String, Integer>> second);

}