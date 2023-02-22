package com.backend.exams.services;

import com.backend.exams.auxiliar.ServiceImp;
import com.backend.exams.models.AsignaturaModel;
import com.backend.exams.repositories.AsignaturaRepository;
import org.springframework.stereotype.Service;

@Service
public class AsignaturaService extends ServiceImp<AsignaturaModel, AsignaturaRepository> implements IAsignaturaService {

}
