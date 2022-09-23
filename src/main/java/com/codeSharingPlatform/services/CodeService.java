package com.codeSharingPlatform.services;

import com.codeSharingPlatform.entities.Code;
import com.codeSharingPlatform.exceptions.CodeNotFoundException;
import com.codeSharingPlatform.repositories.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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

    public Code getCodeByID(String id) {
        Code code = codeRepository.findCodeById(id);
        if (code.isViewRestricted() || code.isTimeRestricted()) {
            changeTimeAndViews(code);
        }
        return code;
   }

   public List<Code> getLatestCode() {
        List<Code> latestCode = codeRepository.findAllByOrderByDateDesc();
        latestCode.removeIf(code -> code.isViewRestricted() || code.isTimeRestricted());
        return latestCode.size() > 10 ? latestCode.subList(0,10) : latestCode;
   }

   public void changeTimeAndViews(Code code) {
       final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss:SSS";
       if (code.isViewRestricted()) {
           if (code.getViews() > 0) {
               code.setViews(code.getViews() - 1);
               codeRepository.save(code);
           } else {
               codeRepository.deleteById(code.getId());
               throw new CodeNotFoundException();
           }
       }

       if (code.isTimeRestricted()) {
           long currentSeconds = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
           LocalDateTime codeLocalDateTime = LocalDateTime.parse(code.getDate(),
                   DateTimeFormatter.ofPattern(DATE_FORMATTER));
           long secondsInDatabase = codeLocalDateTime.toEpochSecond(ZoneOffset.UTC);
           long time = code.getTime() - (currentSeconds - secondsInDatabase);
           if (time <= 0) {
               codeRepository.deleteById(code.getId());
               throw new CodeNotFoundException();
           } else {
               code.setTime(time);
               codeRepository.save(code);
           }
       }
   }
}
