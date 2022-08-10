package com.codeSharingPlatform;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CodeRepository implements ObjectRepository<Code> {
    private LinkedHashMap<Long, Code> repository;

   public CodeRepository() {
       this.repository = new LinkedHashMap<>();
   }

   @Override
    public Long storeCode (Code code) {
       Long id = (long)repository.size() + 1;
       String date = code.formatDate(LocalDateTime.now());
       Code codeToPut = new Code(code.getCode(), date);
       repository.put(id, codeToPut);
       return id;
   }

   @Override
    public Code getCodeByID(Long id) {
       return repository.get(id);
   }

   @Override
   public List<Code> getLatestCode() {
       List<Code> latestCode = repository.entrySet().stream()
               .map(Map.Entry::getValue)
               .collect(Collectors.toList());
       Collections.reverse(latestCode);
       return latestCode.size() > 10 ? latestCode.subList(0,10) : latestCode;
   }
}
