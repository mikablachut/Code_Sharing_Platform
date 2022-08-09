package com.codeSharingPlatform;

import java.util.LinkedHashMap;

public interface ObjectRepository<T> {
    public Integer storeCode(T t);
    public T getCodeByID(Integer id);
}
