package com.backend.alumns.services;

import com.backend.alumns.auxiliar.ServiceImp;
import com.backend.alumns.models.SexoModel;
import com.backend.alumns.repositories.SexoRepository;
import org.springframework.stereotype.Service;

@Service
public class SexoService extends ServiceImp<SexoModel, SexoRepository> implements ISexoService {

}