package com.codeSharingPlatform.repositories;

import com.codeSharingPlatform.entities.Code;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CodeRepository extends CrudRepository<Code, Long> {
    Code findCodeById(Long id);
    List<Code>findAllByOrderByDateDesc();
}
