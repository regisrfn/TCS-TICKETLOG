package com.ticketlog.server.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estados", uniqueConstraints = { @UniqueConstraint(columnNames = "nome", name = "uk_estado_nome") })

@JsonInclude(Include.NON_NULL)
public class Estado {

    @Id
    private UF id;

    @NotBlank(message = "Campo nao deve ser vazio")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Campo nao deve ser vazio")
    @Min(value = 0, message = "Populacao deve ser maior ou igual a zero")
    private Long populacao;

    @NotNull(message = "Campo nao deve ser vazio")
    private Double custoEstadoUs;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cidade> cidadesList;

    public Estado(){
        setPopulacao(0L);
        setCustoEstadoUs(0.0);
    }

    public Estado(String id){
        setId(id);
        setPopulacao(0L);
        setCustoEstadoUs(0.0);
    }

    public void setId(String id) {
        try {
            this.id = UF.valueOf(id.toUpperCase());
        } catch (Exception e) {
            this.id = null;
        }
    }

    public void setId(UF id) {
        this.id = id;
    }

    public String getId() {
        return this.id.toString();
    }

    public enum UF {
        SC, PR, RS
    }
}
