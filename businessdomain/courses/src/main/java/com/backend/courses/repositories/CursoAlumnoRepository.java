package com.backend.courses.repositories;

import com.backend.courses.models.CursoAlumnoModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoAlumnoRepository extends PagingAndSortingRepository<CursoAlumnoModel, Long>{
    
    @Query("select e from CursoAlumnoModel e where e.alumnoId = ?1")
    public Optional<CursoAlumnoModel> findByIdAlumno(Long alumnoId);
}