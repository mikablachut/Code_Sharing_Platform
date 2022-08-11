package com.codeSharingPlatform.services;

import com.codeSharingPlatform.entities.Code;
import com.codeSharingPlatform.repositories.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CodeService {
    private final CodeRepository codeRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Code save (Code codeToSave) {
       return codeRepository.save(codeToSave);
   }

    public Code findCodeByID(String id) {
        return codeRepository.findCodeById(id);
   }

   public List<Code> getLatestCode() {
        List<Code> latestCode = codeRepository.findAllByOrderByDateDesc();
        return latestCode.size() > 10 ? latestCode.subList(0,10) : latestCode;
   }
}
