package com.backend.answers.services;

import com.backend.answers.models.RespuestaModel;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RespuestaService implements IRespuestaService {
    
    @Autowired
    RespuestaService respuestaService;
    
    @Override
    @Transactional(readOnly = true)
    public Iterable<RespuestaModel> findAll() {
        return this.respuestaService.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RespuestaModel> findById(Long id) {
        return this.respuestaService.findById(id);
    }

    @Override
    @Transactional
    public RespuestaModel save(RespuestaModel respuestaModel) {
        return this.respuestaService.save(respuestaModel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.respuestaService.deleteById(id);
    }
}