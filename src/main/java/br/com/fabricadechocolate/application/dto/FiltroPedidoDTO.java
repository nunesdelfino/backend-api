package br.com.fabricadechocolate.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Pedido")
public @Data class FiltroPedidoDTO implements Serializable {
    @ApiModelProperty(value = "Nome do Pedido")
    private String nome;

    @ApiModelProperty(value = "Id do Tamanho")
    private Long idTamanho;

    @ApiModelProperty(value = "Data do Entrega")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataEntrega;

    @ApiModelProperty(value = "Indica se o Pedido está ativo ou não")
    private String status; //Se status aceito
}
