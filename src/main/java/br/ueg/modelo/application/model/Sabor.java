package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "TBL_SABOR", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_SABOR", sequenceName = "TBL_S_SABOR", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Sabor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_SABOR")
    @Column(name = "ID_SABOR", nullable = false)
    private Long id;

    @Column(name = "SABOR", length = 40, nullable = false)
    private String sabor;

    @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "ATIVO", nullable = false)
    private StatusSimNao ativo;

}
