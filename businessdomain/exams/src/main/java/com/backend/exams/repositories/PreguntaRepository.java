package com.backend.exams.repositories;

import com.backend.exams.models.PreguntaModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PreguntaRepository extends PagingAndSortingRepository<PreguntaModel, Long>{

}