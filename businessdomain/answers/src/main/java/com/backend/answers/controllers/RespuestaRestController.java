package com.backend.answers.controllers;

import com.backend.answers.models.RespuestaModel;
import com.backend.answers.services.RespuestaService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RespuestaRestController {
    
    @Autowired
    RespuestaService respuestaService;
    
    @RequestMapping(value = "/respuesta", method = RequestMethod.GET)
    public ModelAndView respuesta(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("respuesta.html");
        return modelAndView;
    }

    @RequestMapping(value = "/api/respuesta", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(respuestaService.findAll());
    }

    @RequestMapping(value = "/api/respuesta", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody RespuestaModel respuestaModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaService.save(respuestaModel));
    }

    @RequestMapping(value = "/api/respuesta/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<RespuestaModel> optional = respuestaService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @RequestMapping(value = "/api/respuesta/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@PathVariable long id, @RequestBody RespuestaModel respuestaModel) {
        Optional<RespuestaModel> optional = respuestaService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RespuestaModel respuestaModel_db = optional.get();
        respuestaModel_db.setTexto(respuestaModel.getTexto());

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaService.save(respuestaModel_db));
    }

    @RequestMapping(value = "/api/respuesta/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        respuestaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}