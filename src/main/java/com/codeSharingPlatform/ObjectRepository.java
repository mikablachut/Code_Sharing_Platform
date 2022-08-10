package com.codeSharingPlatform;

import java.util.List;

public interface ObjectRepository<T> {
    CodeId storeCode(T t);
    T getCodeByID(Long id);
    List<T> getLatestCode();
}
