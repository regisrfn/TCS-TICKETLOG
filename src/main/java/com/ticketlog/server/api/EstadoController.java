package com.ticketlog.server.api;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.ticketlog.server.model.Estado;
import com.ticketlog.server.service.EstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/estado")
@CrossOrigin
public class EstadoController {

    private EstadoService estadoService;

    @Autowired
    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @PostMapping("save")
    public ResponseEntity<Object> saveEstado(@Valid @RequestBody Estado estado) {
        Estado estadoSaved = estadoService.saveEstado(estado);
        return new ResponseEntity<>(estadoSaved, HttpStatus.OK);
    }

    @GetMapping("get")
    public List<Estado> getAllEstados() {
        return estadoService.getAllEstados();
    }

    @GetMapping("get/{id}")
    public Estado getEstadoById(@PathVariable String id) {
        return estadoService.getEstadoById(id);
    }

    @DeleteMapping("delete/{id}")
    public Map<String, String> deleteEstadoById(@PathVariable String id) {
        estadoService.deleteEstadoById(id);
        return Map.of("message", "successfully operation");
    }

    @PutMapping("update/{id}")
    public Estado updateEstado(@PathVariable String id, @Valid @RequestBody Estado estado) {
        return estadoService.updateEstado(id, estado);
    }

}