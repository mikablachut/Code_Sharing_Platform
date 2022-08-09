package com.codeSharingPlatform;

import java.util.LinkedHashMap;

public interface ObjectRepository<T> {
    Integer storeCode(T t);
    T getCodeByID(Integer id);
}
