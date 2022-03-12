package br.com.fabricadechocolate.application.model;

import br.com.fabricadechocolate.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_GASTO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_GASTO", sequenceName = "TBL_S_GASTO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_GASTO")
    @Column(name = "ID_GASTO", nullable = false)
    private Long id;

    @Column(name="ITEM")
    private String item;

    @Column(name = "DATA", length = 40, nullable = false)
    private LocalDate data;

    @Column(name = "NOME_ESTABELECIMENTO", nullable = false)
    private String nomeEstabelecimento;

    @Column(name = "VALOR", nullable = false)
    private Float valor;

}
