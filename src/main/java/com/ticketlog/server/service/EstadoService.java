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

    private EstadoDao userDao;

    @Autowired
    public EstadoService(EstadoDao userDao) {
        this.userDao = userDao;
    }

    public Estado saveEstado(Estado user) {        
        try {
            return userDao.insertEstado(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Estado não pode ser salvo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    public List<Estado> getAllEstados() {
        try {
            return userDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Listagem dos estados não pode ser efetuada", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Estado getEstadoById(String id) {
        try {
            UUID userId = UUID.fromString(id);
            Estado user = userDao.getEstado(userId);
            if (user == null)
                throw new ApiRequestException("Estado não encontrado", HttpStatus.NOT_FOUND);
            return user;
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        }

    }

    public boolean deleteEstadoById(String id) {
        try {
            UUID userId = UUID.fromString(id);
            boolean ok = userDao.deleteEstadoById(userId);
            if (!ok)
                throw new ApiRequestException("Estado não encontrado", HttpStatus.NOT_FOUND);
            return ok;
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        }
    }

    public Estado updateEstado(String id, Estado user) {
        try {
            UUID userId = UUID.fromString(id);
            return userDao.updateEstado(userId, user);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}