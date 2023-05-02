package com.backend.courses.controllers;

import com.backend.courses.auxiliar.ApiExceptionHandler;
import com.backend.courses.auxiliar.Auxiliar;
import com.backend.courses.models.CursoAlumnoModel;
import com.backend.courses.services.CursoAlumnoService;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.netty.http.client.HttpClient;

@Tag(name = "API Cursos - Alumnos", description = "API´s del Servicio Cursos - Alumnos")
@RestController
public class CursoAlumnoRestController {

    private final WebClient.Builder webClientBuilder;

    public CursoAlumnoRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection -> {
                connection.addHandler(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandler(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    private String getAlumnoName(Long id) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:8081/detalle-alumno")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081/detalle-alumno"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        StringBuilder sb = new StringBuilder(100);
        sb.append(block.get("nombre").asText());
        sb.append(" ");
        sb.append(block.get("apellido").asText());
        return sb.toString();
    }
    
    private boolean validIdAlumno(int id) {
        boolean bandera = true;
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:8081/detalle-alumno")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081/detalle-alumno"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        
        if(block.get("id").asText().isEmpty()){
            bandera = false;
        }
        return bandera;
    }

    @Autowired
    CursoAlumnoService cursoAlumnoService;

    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/curso-alumno-template", method = RequestMethod.GET)
    public ModelAndView cursoAlumno(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cursoAlumno.html");
        return modelAndView;
    }

    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-curso-alumno", method = RequestMethod.GET)
    public ResponseEntity<?> get(Pageable pageable) {
        List<CursoAlumnoModel> lista;
        try {
            lista = (List<CursoAlumnoModel>) cursoAlumnoService.findAll();
            if (lista == null || lista.isEmpty());
            lista.forEach(x -> {
                String alumnoName = getAlumnoName(x.getAlumnoId());
                x.setAlumnoNombre(alumnoName);
            });
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(cursoAlumnoService.findAll(pageable));
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-curso-alumno/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<CursoAlumnoModel> optional;
        try {
            optional = cursoAlumnoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch (Exception e) {
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por ID del Alumno", description = "API para Retornar el Detalle de un Elemento por ID del Alumno del Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/buscar-curso-alumno/{id_alumno}", method = RequestMethod.GET)
    public ResponseEntity<?> getIDAlumno(@PathVariable long alumnoId) {
        Optional<CursoAlumnoModel> optional;
        try {
            optional = cursoAlumnoService.findByIdAlumno(alumnoId);
            if (optional == null || optional.isEmpty());
        } catch (Exception e) {
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-curso-alumno", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody CursoAlumnoModel cursoAlumnoModel, BindingResult result) {
        if(validIdAlumno(Integer.parseInt(cursoAlumnoModel.getAlumnoId().toString())));
        
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoAlumnoService.save(cursoAlumnoModel));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-curso-alumno/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<CursoAlumnoModel> optional;
        try {
            optional = cursoAlumnoService.findById(id);
            if (optional == null || optional.isEmpty());
            cursoAlumnoService.deleteById(id);
        } catch (Exception e) {
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }
}
