package com.backend.exams.repositories;

import com.backend.exams.models.ExamenModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExamenRepository extends PagingAndSortingRepository<ExamenModel, Long>{

}
