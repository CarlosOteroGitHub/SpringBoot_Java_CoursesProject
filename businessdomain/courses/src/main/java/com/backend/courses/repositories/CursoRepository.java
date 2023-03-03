package com.backend.courses.repositories;

import com.backend.courses.models.CursoModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<CursoModel, Long>{
    
    @Query("select e from CursoModel e where e.nombre like %?1%")
    public Optional<CursoModel> findByNombre(String nombre);
}