package com.backend.answers.repositories;

import java.io.Serializable;
import com.backend.answers.models.RespuestaModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RespuestaRepository extends MongoRepository<RespuestaModel, Serializable>{

}