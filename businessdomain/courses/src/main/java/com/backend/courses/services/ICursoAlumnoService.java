package com.backend.courses.services;

import com.backend.courses.auxiliar.IService;
import com.backend.courses.models.CursoAlumnoModel;
import java.util.Optional;

public interface ICursoAlumnoService extends IService<CursoAlumnoModel>{
    
    public Optional<CursoAlumnoModel> findByIdAlumno(Long alumnoId);
}