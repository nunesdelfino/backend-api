package br.com.fabricadechocolate.application.dto;

import br.com.fabricadechocolate.application.enums.StatusSimNao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Amigo")
public @Data
class PedidoDTO implements Serializable {

    @ApiModelProperty(value = "id do Pedido")
    private Long id;

    @ApiModelProperty(value = "Nome do Cliente Pedido")
    private String nome;

    @ApiModelProperty(value = "Numero do Cliente Pedido")
    private String numero;

    @ApiModelProperty(value = "Tipo Ovo")
    private String tipoOvo;

    @ApiModelProperty(value = "Id do Tamanho")
    private Long idTamanho;

    @ApiModelProperty(value = "Nome do Tamanho")
    private String nomeTamanho;

    @ApiModelProperty(value = "Id do Sabor Um")
    private Long idSaborUm;

    @ApiModelProperty(value = "Nome do Sabor Um")
    private String nomeSaborUm;

    @ApiModelProperty(value = "Id do Sabor Dois")
    private Long idSaborDois;

    @ApiModelProperty(value = "Nome do Sabor Dois")
    private String nomeSaborDois;

    @ApiModelProperty(value = "Id do Sabor Tres")
    private Long idSaborTres;

    @ApiModelProperty(value = "Nome do Sabor Tres")
    private String nomeSaborTres;

    @ApiModelProperty(value = "Id do Sabor Quatro")
    private Long idSaborQuatro;

    @ApiModelProperty(value = "Nome do Sabor Quatro")
    private String nomeSaborQuatro;

    @ApiModelProperty(value = "Id do Sabor Cinco")
    private Long idSaborCinco;

    @ApiModelProperty(value = "Nome do Sabor Cinco")
    private String nomeSaborCinco;

    @ApiModelProperty(value = "Observacao")
    private String observacao;

    @ApiModelProperty(value = "Preco")
    private Double preco;

    @ApiModelProperty(value = "Se é para entregar")
    private StatusSimNao entregar;

    @ApiModelProperty(value = "Data da Entega")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dataEntrega;

    @ApiModelProperty(value = "Endereço")
    private String endereco;

    @ApiModelProperty(value = "Indica se o Pedido está ativo")
    private String status;

    //Criado para producao
    @ApiModelProperty(value = "Indica se o Pedido está em produção")
    private Boolean statusProducao;

}
