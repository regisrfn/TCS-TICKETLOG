package com.ticketlog.server.repository;

import java.util.List;
import java.util.UUID;

import com.ticketlog.server.dao.EstadoDao;
import com.ticketlog.server.dao.JpaDao;
import com.ticketlog.server.model.Estado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoRepository implements EstadoDao {

    private JpaDao jpaDataAccess;

    @Autowired
    public EstadoRepository(JpaDao jpaDataAccess, JdbcTemplate jdbcTemplate) {
        this.jpaDataAccess = jpaDataAccess;
    }

    @Override
    public Estado insertEstado(Estado Estado) {
        return jpaDataAccess.save(Estado);
    }

    @Override
    public boolean deleteEstadoById(UUID id) {
        try {
            jpaDataAccess.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Estado> getAll() {
        return jpaDataAccess.findAll();
    }

    @Override
    public Estado getEstado(UUID id) {
        return jpaDataAccess.findById(id).orElse(null);
    }

    @Override
    public Estado updateEstado(UUID id, Estado Estado) {
        Estado.setId(id);
        return jpaDataAccess.save(Estado);
    }

    // retorna null se o estado não foi encontrado
    @Override
    public Estado updatePopulacao(Estado estado, Long populacao) {
        Long novaPopulacao = populacao + estado.getPopulacao();
        estado.setPopulacao(novaPopulacao);
        return jpaDataAccess.save(estado);
    }

    @Override
    public Estado updateCustoPorPessoa(Estado estado, Double custo) {
        Double novoCusto = custo + estado.getCustoEstadoUs();
        estado.setCustoEstadoUs(novoCusto);
        return jpaDataAccess.save(estado);
    }
}