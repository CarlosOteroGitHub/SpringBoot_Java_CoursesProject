package com.backend.exams.services;

import com.backend.exams.auxiliar.ServiceImp;
import com.backend.exams.models.ExamenCursoModel;
import com.backend.exams.repositories.ExamenCursoRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamenCursoService extends ServiceImp<ExamenCursoModel, ExamenCursoRepository> implements IExamenCursoService {

}