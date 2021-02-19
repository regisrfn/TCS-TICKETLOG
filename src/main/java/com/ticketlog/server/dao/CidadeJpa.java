package com.ticketlog.server.dao;

import java.util.UUID;

import com.ticketlog.server.model.Cidade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeJpa extends JpaRepository<Cidade, UUID> {
}