package com.backend.courses.services;

import com.backend.courses.auxiliar.ServiceImp;
import com.backend.courses.models.CursoModel;
import com.backend.courses.repositories.CursoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService extends ServiceImp<CursoModel, CursoRepository> implements ICursoService {

    @Override
    @Transactional(readOnly = true)
    public List<CursoModel> findByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }
}