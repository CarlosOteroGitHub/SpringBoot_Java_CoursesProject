package com.backend.alumns.services;

import com.backend.alumns.auxiliar.ServiceImp;
import com.backend.alumns.models.AlumnoModel;
import com.backend.alumns.repositories.AlumnoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService extends ServiceImp<AlumnoModel, AlumnoRepository> implements IAlumnoService {

}