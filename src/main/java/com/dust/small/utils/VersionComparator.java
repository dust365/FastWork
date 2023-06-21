package com.dust.small.utils;

import java.util.Comparator;

public class VersionComparator implements Comparator<String> {
    @Override
    public int compare(String first, String second) {
        first = subStringBefore(first, "-");
        second = subStringBefore(second, "-");
        String[] firstSplit = first.split("\\.");
        String[] secondSplit = second.split("\\.");
        for (int i = 0, j = Math.min(firstSplit.length, secondSplit.length); i < j; i++) {
            int firstVersion = Integer.parseInt(firstSplit[i]);
            int secondVersion = Integer.parseInt(secondSplit[i]);
            if (firstVersion == secondVersion) continue;
            return secondVersion - firstVersion;
        }
        return 0;
    }

    private static String subStringBefore(String data, String rule) {
        int index = data.indexOf(rule);
        if (index == -1) {
            return data;
        }
        return data.substring(0, index);
    }
}
