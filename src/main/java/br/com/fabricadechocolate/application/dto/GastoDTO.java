package br.com.fabricadechocolate.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Gasto")
public @Data class GastoDTO implements Serializable {

    @ApiModelProperty(value = "id gasto")
    private Long id;

    @ApiModelProperty(value = "data")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate data;

    @ApiModelProperty(value = "nome estabelecimento")
    private String nomeEstabelecimento;

    @ApiModelProperty(value = "item")
    private String item;

    @ApiModelProperty(value = "valor")
    private Float valor;

}