package com.backend.exams.repositories;

import com.backend.exams.models.ExamenCursoModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExamenCursoRepository extends PagingAndSortingRepository<ExamenCursoModel, Long>{
    
}