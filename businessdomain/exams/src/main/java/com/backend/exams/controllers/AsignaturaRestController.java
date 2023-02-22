package com.backend.exams.controllers;

import com.backend.exams.models.AsignaturaModel;
import com.backend.exams.services.AsignaturaService;
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
public class AsignaturaRestController {
    
    @Autowired
    AsignaturaService asignaturaService;
    
    @RequestMapping(value = "/asignatura-template", method = RequestMethod.GET)
    public ModelAndView asignatura(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("asignatura.html");
        return modelAndView;
    }

    @RequestMapping(value = "/listar-asignaturas", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(asignaturaService.findAll());
    }
    
    @RequestMapping(value = "/asignatura-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        return ResponseEntity.ok().body(asignaturaService.findAll(pageable));
    }

    @RequestMapping(value = "/agregar-asignatura", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody AsignaturaModel asignaturaModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaService.save(asignaturaModel));
    }

    @RequestMapping(value = "/detalle-asignatura/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<AsignaturaModel> optional = asignaturaService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @RequestMapping(value = "/actualizar-asignatura/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@PathVariable long id, @RequestBody AsignaturaModel asignaturaModel) {
        Optional<AsignaturaModel> optional = asignaturaService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AsignaturaModel asignaturaModel_db = optional.get();
        asignaturaModel_db.setNombre(asignaturaModel.getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaService.save(asignaturaModel_db));
    }

    @RequestMapping(value = "/eliminar-asignatura/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        asignaturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}