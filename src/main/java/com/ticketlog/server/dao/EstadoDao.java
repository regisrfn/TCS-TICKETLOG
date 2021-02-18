package com.ticketlog.server.dao;

import java.util.List;
import java.util.UUID;

import com.ticketlog.server.model.Estado;

public interface EstadoDao {
    Estado insertEstado(Estado Estado);

    boolean deleteEstadoById(UUID id);

    List<Estado> getAll();

    Estado getEstado(UUID id);

    Estado updateEstado(UUID id, Estado Estado);
}