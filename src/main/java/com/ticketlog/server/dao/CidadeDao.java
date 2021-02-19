package com.ticketlog.server.dao;

import java.util.List;
import java.util.UUID;

import com.ticketlog.server.model.Cidade;
public interface CidadeDao {
    Cidade insertCidade(Cidade cidade);

    boolean deleteCidadeById(UUID id);

    List<Cidade> getAll();

    Cidade getCidade(UUID id);

    Cidade updateCidade(UUID id, Cidade cidade);
    
}