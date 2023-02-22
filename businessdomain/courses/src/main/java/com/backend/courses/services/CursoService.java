package com.backend.courses.services;

import com.backend.courses.auxiliar.ServiceImp;
import com.backend.courses.models.CursoModel;
import com.backend.courses.repositories.CursoRepository;
import org.springframework.stereotype.Service;

@Service
public class CursoService extends ServiceImp<CursoModel, CursoRepository> implements ICursoService {

}
