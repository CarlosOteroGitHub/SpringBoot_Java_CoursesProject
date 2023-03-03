package com.backend.courses.services;

import com.backend.courses.auxiliar.IService;
import com.backend.courses.models.CursoModel;
import java.util.Optional;

public interface ICursoService extends IService<CursoModel>{
    
    public Optional<CursoModel> findByNombre(String nombre);
}