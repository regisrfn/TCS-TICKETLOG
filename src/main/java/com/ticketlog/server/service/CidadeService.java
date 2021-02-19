package com.ticketlog.server.service;

import java.util.List;
import java.util.UUID;

import com.ticketlog.server.dao.CidadeDao;
import com.ticketlog.server.exception.ApiRequestException;
import com.ticketlog.server.model.Cidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    private CidadeDao cidadeDao;

    @Autowired
    public CidadeService(CidadeDao cidadeDao) {
        this.cidadeDao = cidadeDao;
    }

    public Cidade saveCidade(Cidade cidade) {
        return cidadeDao.insertCidade(cidade);
    }

    public List<Cidade> getAllCidades() {
        return cidadeDao.getAll();
    }

    public Cidade getCidadeById(UUID id) {
        return cidadeDao.getCidade(id);
    }

    public boolean deleteCidadeById(UUID id) {
        try {
            return cidadeDao.deleteCidadeById(id);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public Cidade updateCidade(UUID id, Cidade cidade) {
        try {
            return cidadeDao.updateCidade(id, cidade);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
       
    }

}