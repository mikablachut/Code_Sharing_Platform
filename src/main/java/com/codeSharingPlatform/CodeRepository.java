package com.codeSharingPlatform;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Repository
public class CodeRepository implements ObjectRepository<Code> {
   private LinkedHashMap<Long, Code> repository;

   public CodeRepository() {
       this.repository = new LinkedHashMap<>();
   }

   @Override
    public Long storeCode (Code code) {
       Long id = (long)repository.size() + 1;
       Code codeToPut = new Code(code.getCode(), LocalDateTime.now());
       repository.put(id, codeToPut);
       return id;
   }

   @Override
    public Code getCodeByID(Long id) {
       return repository.get(id);
   }
}
