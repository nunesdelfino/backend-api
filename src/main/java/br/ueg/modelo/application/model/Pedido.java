package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_PEDIDO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_PEDIDO", sequenceName = "TBL_S_PEDIDO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_PEDIDO")
    @Column(name = "ID_PEDIDO", nullable = false)
    private Long id;

    @Column(name = "NOME_CLIENTE", length = 100, nullable = false)
    private String nome;

    @Column(name = "NUMERO_CLIENTE", nullable = false, length = 12 )
    private String numero;

    @Column(name = "TIPO_OVO", length = 20, nullable = false)
    private String tipoOvo;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TAMANHO", referencedColumnName = "ID_TAMANHO", nullable = false)
    private Tamanho tamanho;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SABOR_UM", referencedColumnName = "ID_SABOR", nullable = false)
    private Sabor saborUm;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SABOR_DOIS", referencedColumnName = "ID_SABOR")
    private Sabor saborDois;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SABOR_TRES", referencedColumnName = "ID_SABOR")
    private Sabor saborTres;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SABOR_QUATRO", referencedColumnName = "ID_SABOR")
    private Sabor saborQuatro;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SABOR_CINCO", referencedColumnName = "ID_SABOR")
    private Sabor saborCinco;

    @Column(name = "OBSERVACAO")
    private String observacao;

    @Column(name = "PRECO")
    private Double preco;

    @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "ENTREGAR", nullable = false)
    private StatusSimNao entregar;

    @Column(name = "DATA_ENTREGA", nullable = false)
    private LocalDate dataEntrega;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "ATIVO", nullable = false)
    private String ativo;

}
