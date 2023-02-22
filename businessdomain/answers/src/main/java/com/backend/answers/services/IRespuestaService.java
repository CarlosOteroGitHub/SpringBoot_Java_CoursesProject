package com.backend.answers.services;

import com.backend.answers.models.RespuestaModel;
import java.util.Optional;

public interface IRespuestaService {

    public Iterable<RespuestaModel> findAll();

    public Optional<RespuestaModel> findById(Long id);

    public RespuestaModel save(RespuestaModel respuestaModel);

    public void deleteById(Long id);
}