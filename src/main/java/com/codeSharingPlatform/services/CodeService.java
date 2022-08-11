package com.codeSharingPlatform.services;

import com.codeSharingPlatform.entities.Code;
import com.codeSharingPlatform.exceptions.CodeNotFoundException;
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
        codeToSave.setTimeRestricted(codeToSave.getTime() != 0);
        codeToSave.setViewRestricted(codeToSave.getViews() != 0);
        return codeRepository.save(codeToSave);
   }

    public Code findCodeByID(String id) {
        Code code = codeRepository.findCodeById(id);
        if (code.isViewRestricted() && code.getViews() > 0) {
            code.setViews(code.getViews() - 1);
            codeRepository.save(code);
            return code;
        } else if (code.isViewRestricted() && code.getViews() <= 0) {
            codeRepository.deleteById(id);
            throw new CodeNotFoundException();
        } else {
            return code;
        }
   }

   public List<Code> getLatestCode() {
        List<Code> latestCode = codeRepository.findAllByOrderByDateDesc();
        return latestCode.size() > 10 ? latestCode.subList(0,10) : latestCode;
   }
}
