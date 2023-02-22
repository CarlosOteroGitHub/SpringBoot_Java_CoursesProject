package com.backend.alumns.controllers;

import com.backend.alumns.models.AlumnoModel;
import com.backend.alumns.services.AlumnoService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.backend.alumns.auxiliar.Auxiliar;
import java.io.IOException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AlumnoRestController {

    @Autowired
    AlumnoService alumnoService;

    @RequestMapping(value = "/alumno-template", method = RequestMethod.GET)
    public ModelAndView alumno(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("alumno.html");
        return modelAndView;
    }

    @RequestMapping(value = "/listar-alumnos", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(alumnoService.findAll());
    }

    @RequestMapping(value = "/alumno-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        return ResponseEntity.ok().body(alumnoService.findAll(pageable));
    }

    @RequestMapping(value = "/detalle-alumno-archivo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get_archivo(@PathVariable long id) {
        Optional<AlumnoModel> optional = alumnoService.findById(id);
        if (optional.isEmpty() || optional.get().getFoto() == null) {
            return ResponseEntity.notFound().build();
        }

        Resource imagen = new ByteArrayResource(optional.get().getFoto());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @RequestMapping(value = "/agregar-alumno", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody AlumnoModel alumnoModel, BindingResult result) {
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.save(alumnoModel));
    }

    @RequestMapping(value = "/agregar-archivo-alumno", method = RequestMethod.POST)
    public ResponseEntity<?> post_archivo(@Valid AlumnoModel alumnoModel, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
        if (!archivo.isEmpty()) {
            alumnoModel.setFoto(archivo.getBytes());
        }
        return this.post(alumnoModel, result);
    }

    @RequestMapping(value = "/detalle-alumno/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<AlumnoModel> optional = alumnoService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @RequestMapping(value = "/actualizar-alumno/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@Valid @RequestBody AlumnoModel alumnoModel, BindingResult result, @PathVariable long id) {
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }

        Optional<AlumnoModel> optional = alumnoService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AlumnoModel alumnoModel_db = optional.get();
        alumnoModel_db.setNombre(alumnoModel.getNombre());
        alumnoModel_db.setApellido(alumnoModel.getApellido());
        alumnoModel_db.setEmail(alumnoModel.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.save(alumnoModel_db));
    }

    @RequestMapping(value = "/actualizar-archivo-alumno/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put_archivo(@Valid AlumnoModel alumnoModel, BindingResult result, @PathVariable long id, @RequestParam MultipartFile archivo) throws IOException {
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }

        Optional<AlumnoModel> optional = alumnoService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AlumnoModel alumnoModel_db = optional.get();
        alumnoModel_db.setNombre(alumnoModel.getNombre());
        alumnoModel_db.setApellido(alumnoModel.getApellido());
        alumnoModel_db.setEmail(alumnoModel.getEmail());

        if (!archivo.isEmpty()) {
            alumnoModel.setFoto(archivo.getBytes());
        }

        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.save(alumnoModel_db));
    }

    @RequestMapping(value = "/eliminar-alumno/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        alumnoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
