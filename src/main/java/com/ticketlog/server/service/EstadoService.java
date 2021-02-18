package com.ticketlog.server.service;

import java.util.List;
import java.util.UUID;

import com.ticketlog.server.dao.EstadoDao;
import com.ticketlog.server.exception.ApiRequestException;
import com.ticketlog.server.model.Estado;

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

    public Estado getEstadoById(String id) {
        try {
            UUID estadoId = UUID.fromString(id);
            Estado estado = estadoDao.getEstado(estadoId);
            if (estado == null)
                throw new ApiRequestException("Estado não encontrado", HttpStatus.NOT_FOUND);
            return estado;
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        }

    }

    public boolean deleteEstadoById(String id) {
        try {
            UUID estadoId = UUID.fromString(id);
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
            UUID estadoId = UUID.fromString(id);
            return estadoDao.updateEstado(estadoId, estado);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Estado updatePopulacao(String id, Long populacao) {
        try {
            UUID estadoId = UUID.fromString(id);
            Estado estado = estadoDao.getEstado(estadoId);
            if (estado == null)
                throw new ApiRequestException("Estado não encontrado", HttpStatus.NOT_FOUND);
            return estadoDao.updatePopulacao(estado, populacao);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        }
    }

    public Estado updateCusto(String id, Double custo) {
        try {
            UUID estadoId = UUID.fromString(id);
            Estado estado = estadoDao.getEstado(estadoId);
            if (estado == null)
                throw new ApiRequestException("Estado não encontrado", HttpStatus.NOT_FOUND);
            return estadoDao.updateCustoPorPessoa(estado, custo);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        }
    }
}