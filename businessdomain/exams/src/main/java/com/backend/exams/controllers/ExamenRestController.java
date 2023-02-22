package com.backend.exams.controllers;

import com.backend.exams.models.ExamenModel;
import com.backend.exams.services.ExamenService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ExamenRestController {

    @Autowired
    ExamenService examenService;
    
    @RequestMapping(value = "/examen-template", method = RequestMethod.GET)
    public ModelAndView examen(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("examen.html");
        return modelAndView;
    }

    @RequestMapping(value = "/listar-examenes", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(examenService.findAll());
    }
    
    @RequestMapping(value = "/examen-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        return ResponseEntity.ok().body(examenService.findAll(pageable));
    }

    @RequestMapping(value = "/agregar-examen", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody ExamenModel examenModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examenService.save(examenModel));
    }

    @RequestMapping(value = "/detalle-examen/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<ExamenModel> optional = examenService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @RequestMapping(value = "/actualizar-examen/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@PathVariable long id, @RequestBody ExamenModel examenModel) {
        Optional<ExamenModel> optional = examenService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ExamenModel examenModel_db = optional.get();
        examenModel_db.setNombre(examenModel.getNombre());

        examenModel_db.getPreguntas().stream().filter(pdb -> examenModel.getPreguntas().contains(pdb)).forEach(p -> {
            examenModel_db.removePregunta(p);
        });

        examenModel_db.setPreguntas(examenModel.getPreguntas());

        return ResponseEntity.status(HttpStatus.CREATED).body(examenService.save(examenModel_db));
    }

    @RequestMapping(value = "/eliminar-examen/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        examenService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}