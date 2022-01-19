package br.com.fabricadechocolate.application.dto;

import br.com.fabricadechocolate.application.enums.StatusSimNao;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Tamanho")
public @Data class FiltroTipoOvoDTO implements Serializable {
    private static final long serialVersionUID = 7616722014159043532L;

    @ApiModelProperty(value = "Tipo")
    private String tipo;

    @ApiModelProperty(value = "Ativo")
    private StatusSimNao ativo;

}
