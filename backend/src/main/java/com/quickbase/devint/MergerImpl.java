package com.quickbase.devint;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class MergerImpl implements Merger{

    @Override
    public List<Pair<String, Integer>> merge(List<Pair<String, Integer>> first, List<Pair<String, Integer>> second) {
        List<Pair<String, Integer>> copyOfFirst = new ArrayList<>(first);
        List<Pair<String, Integer>> copyOfSecond = new ArrayList<>(second);

        Map<String, Integer> firstMap = new LinkedHashMap<>();
        for (Pair<String, Integer> el : copyOfFirst) {
            firstMap.put(el.getLeft(), el.getRight());
        }

        Map<String, Integer> secondMap = new LinkedHashMap<>();
        for (Pair<String, Integer> el2 : copyOfSecond) {
            secondMap.put(el2.getLeft(), el2.getRight());
        }

        firstMap.putAll(secondMap);

        List<Pair<String, Integer>> mergedList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : firstMap.entrySet()) {
            if (entry.getKey().equals("United States of America")) {
                continue;
            }
            mergedList.add(new ImmutablePair<String, Integer>(entry.getKey(), entry.getValue()));
        }

        return mergedList;
    }



}
