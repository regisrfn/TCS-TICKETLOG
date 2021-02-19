package com.ticketlog.server.dao;

import com.ticketlog.server.model.Estado;
import com.ticketlog.server.model.Estado.UF;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDao extends JpaRepository<Estado, UF> {
}