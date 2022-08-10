package com.codeSharingPlatform;

import java.util.LinkedHashMap;

public interface ObjectRepository<T> {
    Long storeCode(T t);
    T getCodeByID(Long id);
}
