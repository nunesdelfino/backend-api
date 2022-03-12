package br.com.fabricadechocolate.application.dto.filtro;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Sabor")
public @Data class FiltroSaborDTO implements Serializable {
    private static final long serialVersionUID = 7616722014159043532L;

    @ApiModelProperty(value = "sabor")
    private String sabor;

    @ApiModelProperty(value = "ativo")
    private String ativo;

}
