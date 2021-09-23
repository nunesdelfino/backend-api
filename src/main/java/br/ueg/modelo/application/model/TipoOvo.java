package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "TBL_TIPO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_TIPO", sequenceName = "TBL_S_TIPO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class TipoOvo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TIPO")
    @Column(name = "ID_TIPO", nullable = false)
    private Long id;

    @Column(name = "TIPO", length = 20, nullable = false)
    private String tipo;

    @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "ATIVO", nullable = false)
    private StatusSimNao ativo;

}
