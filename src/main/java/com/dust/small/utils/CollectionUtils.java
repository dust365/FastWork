package com.dust.small.utils;

import java.util.Collection;

public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.size() < 1;
    }
}
