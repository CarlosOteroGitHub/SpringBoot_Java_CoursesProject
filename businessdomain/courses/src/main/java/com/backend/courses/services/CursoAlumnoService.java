package com.backend.courses.services;

import com.backend.courses.auxiliar.ServiceImp;
import com.backend.courses.models.CursoAlumnoModel;
import com.backend.courses.repositories.CursoAlumnoRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoAlumnoService extends ServiceImp<CursoAlumnoModel, CursoAlumnoRepository> implements ICursoAlumnoService {

    @Override
    @Transactional(readOnly = true)
    public Optional<CursoAlumnoModel> findByIdAlumno(Long alumnoId) {
        return repository.findByIdAlumno(alumnoId);
    }
}