package com.ticketlog.server.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estados", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "nome", name = "uk_estado_nome") 
})

@JsonInclude(Include.NON_NULL)
public class Estado {
    
    @Id
    private UUID id;

    @NotBlank(message = "Campo não deve ser vazio")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Campo não deve ser vazio")
    private Long populacao;

    @NotNull(message = "Campo não deve ser vazio")
    private Double custoEstadoUs;

    public Estado() {
        setId(UUID.randomUUID());
    }
}
