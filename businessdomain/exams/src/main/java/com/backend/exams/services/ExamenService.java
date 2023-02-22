package com.backend.exams.services;

import com.backend.exams.auxiliar.ServiceImp;
import com.backend.exams.models.ExamenModel;
import com.backend.exams.repositories.ExamenRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamenService extends ServiceImp<ExamenModel, ExamenRepository> implements IExamenService {

}
