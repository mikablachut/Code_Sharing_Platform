package com.codeSharingPlatform;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Repository
public class CodeRepository implements ObjectRepository<Code> {
   private LinkedHashMap<Integer, Code> repository;

   public CodeRepository() {
       this.repository = new LinkedHashMap<>();
   }

   @Override
    public Integer storeCode (Code code) {
       Integer id = repository.size() + 1;
       Code codeToPut = new Code(code.getCode(), LocalDateTime.now());
       repository.put(id, codeToPut);
       return id;
   }

   @Override
    public Code getCodeByID(Integer id) {
       return repository.get(id);
   }
}
