package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "TBL_TAMANHO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_TAMANHO", sequenceName = "TBL_S_TAMANHO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Tamanho {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TAMANHO")
    @Column(name = "ID_TAMANHO", nullable = false)
    private Long id;

    @Column(name = "TAMANHO", length = 5, nullable = false)
    private String tamanho;

    @Column(name = "ATIVO", nullable = false)
    private String ativo;

}
