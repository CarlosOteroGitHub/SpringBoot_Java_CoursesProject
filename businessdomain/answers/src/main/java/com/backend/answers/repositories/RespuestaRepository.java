package com.backend.answers.repositories;

import com.backend.answers.models.RespuestaModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RespuestaRepository extends PagingAndSortingRepository<RespuestaModel, Long>{
    
}