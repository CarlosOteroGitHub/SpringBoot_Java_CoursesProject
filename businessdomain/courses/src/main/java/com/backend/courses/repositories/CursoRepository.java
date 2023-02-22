package com.backend.courses.repositories;

import com.backend.courses.models.CursoModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<CursoModel, Long>{

}
