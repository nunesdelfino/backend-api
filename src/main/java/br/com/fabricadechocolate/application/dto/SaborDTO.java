package br.com.fabricadechocolate.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Sabor")
public @Data class SaborDTO implements Serializable {

    @ApiModelProperty(value = "id sabor")
    private Long id;

    @ApiModelProperty(value = "sabor ovo")
    private String sabor;

    @ApiModelProperty(value = "sabor ativo")

    private boolean ativo;
}
