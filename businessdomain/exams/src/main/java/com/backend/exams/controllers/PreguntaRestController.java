package com.backend.exams.controllers;

import com.backend.exams.models.PreguntaModel;
import com.backend.exams.services.PreguntaService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
public class PreguntaRestController {
    
    @Autowired
    PreguntaService preguntaService;
    
    @RequestMapping(value = "/pregunta-template", method = RequestMethod.GET)
    public ModelAndView pregunta(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pregunta.html");
        return modelAndView;
    }

    @RequestMapping(value = "/listar-preguntas", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(preguntaService.findAll());
    }
    
    @RequestMapping(value = "/pregunta-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        return ResponseEntity.ok().body(preguntaService.findAll(pageable));
    }

    @RequestMapping(value = "/agregar-pregunta", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody PreguntaModel preguntaModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.save(preguntaModel));
    }

    @RequestMapping(value = "/detalle-pregunta/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<PreguntaModel> optional = preguntaService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @RequestMapping(value = "/actualizar-pregunta/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@PathVariable long id, @RequestBody PreguntaModel preguntaModel) {
        Optional<PreguntaModel> optional = preguntaService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PreguntaModel preguntaModel_db = optional.get();
        preguntaModel_db.setTexto(preguntaModel.getTexto());

        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.save(preguntaModel_db));
    }

    @RequestMapping(value = "/eliminar-pregunta/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        preguntaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}