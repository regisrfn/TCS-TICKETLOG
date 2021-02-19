package com.ticketlog.server.service;

import java.util.List;

import com.ticketlog.server.dao.EstadoDao;
import com.ticketlog.server.exception.ApiRequestException;
import com.ticketlog.server.model.Estado;
import com.ticketlog.server.model.Estado.UF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    private EstadoDao estadoDao;

    @Autowired
    public EstadoService(EstadoDao estadoDao) {
        this.estadoDao = estadoDao;
    }

    public Estado saveEstado(Estado estado) {
        try {
            return estadoDao.insertEstado(estado);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Estado não pode ser salvo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<Estado> getAllEstados() {
        try {
            return estadoDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Listagem dos estados não pode ser efetuada",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Estado getEstadoById(String estadoUf) {
        try {
            UF estadoId = UF.valueOf(estadoUf.toUpperCase());
            Estado estado = estadoDao.getEstado(estadoId);
            if (estado == null)
                throw new ApiRequestException("Estado não encontrado", HttpStatus.NOT_FOUND);
                estado.getCidadesList();
            return estado;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        }

    }

    public boolean deleteEstadoById(String estadoUf) {
        try {
            UF estadoId = UF.valueOf(estadoUf.toUpperCase());
            boolean ok = estadoDao.deleteEstadoById(estadoId);
            if (!ok)
                throw new ApiRequestException("Estado não encontrado", HttpStatus.NOT_FOUND);
            return ok;
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        }
    }

    public Estado updateEstado(String id, Estado estado) {
        try {
            UF estadoId = UF.valueOf(id.toUpperCase());
            return estadoDao.updateEstado(estadoId, estado);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}