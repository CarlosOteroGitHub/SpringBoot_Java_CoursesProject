package com.backend.exams.services;

import com.backend.exams.auxiliar.ServiceImp;
import org.springframework.stereotype.Service;
import com.backend.exams.models.PreguntaModel;
import com.backend.exams.repositories.PreguntaRepository;

@Service
public class PreguntaService extends ServiceImp<PreguntaModel, PreguntaRepository> implements IPreguntaService {

}
