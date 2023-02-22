package com.backend.alumns.repositories;

import com.backend.alumns.models.AlumnoModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlumnoRepository extends PagingAndSortingRepository<AlumnoModel, Long>{
    
}