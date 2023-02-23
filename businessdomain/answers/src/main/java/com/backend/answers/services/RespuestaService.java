package com.backend.answers.services;

import com.backend.answers.auxiliar.ServiceImp;
import com.backend.answers.models.RespuestaModel;
import com.backend.answers.repositories.RespuestaRepository;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService extends ServiceImp<RespuestaModel, RespuestaRepository> implements IRespuestaService {

}