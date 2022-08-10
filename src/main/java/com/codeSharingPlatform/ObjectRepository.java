package com.codeSharingPlatform;

import java.util.List;

public interface ObjectRepository<T> {
    Long storeCode(T t);
    T getCodeByID(Long id);
    List<T> getLatestCode();
}
