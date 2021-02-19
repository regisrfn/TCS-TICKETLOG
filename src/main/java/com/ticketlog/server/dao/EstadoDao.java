package com.ticketlog.server.dao;

import java.util.List;

import com.ticketlog.server.model.Estado;
import com.ticketlog.server.model.Estado.UF;

public interface EstadoDao {
    Estado insertEstado(Estado estado);

    boolean deleteEstadoById(UF id);

    List<Estado> getAll();

    Estado getEstado(UF id);

    Estado updateEstado(UF id, Estado estado);

    Estado updatePopulacao(Estado estado, Long populacao);

    Estado updateCustoEstado(Estado estado, Double custo);
}