package com.backend.courses.services;

import com.backend.courses.auxiliar.IService;
import com.backend.courses.models.CursoModel;
import java.util.List;

public interface ICursoService extends IService<CursoModel>{
    
    public List<CursoModel> findByNombre(String nombre);
}