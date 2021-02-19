package com.ticketlog.server.repository;

import java.util.List;
import java.util.UUID;

import com.ticketlog.server.dao.CidadeDao;
import com.ticketlog.server.dao.CidadeJpa;
import com.ticketlog.server.model.Cidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class CidadeRepository implements CidadeDao {

    private CidadeJpa jpaDataAccess;

    @Autowired
    public CidadeRepository(CidadeJpa jpaDataAccess) {
        this.jpaDataAccess = jpaDataAccess;
    }

    @Override
    public Cidade insertCidade(Cidade cidade) {
        return jpaDataAccess.save(cidade);
    }

    @Override
    public boolean deleteCidadeById(UUID id) {
        try {
            jpaDataAccess.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Cidade> getAll() {
        return jpaDataAccess.findAll();
    }

    @Override
    public Cidade getCidade(UUID id) {
        return jpaDataAccess.findById(id).orElse(null);
    }

    @Override
    public Cidade updateCidade(UUID id, Cidade cidade) {
        cidade.setId(id);
        return jpaDataAccess.save(cidade);
    }
}