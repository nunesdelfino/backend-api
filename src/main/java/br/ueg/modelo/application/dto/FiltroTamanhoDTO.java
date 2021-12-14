package br.ueg.modelo.application.dto;

import br.ueg.modelo.application.enums.StatusAtivoInativo;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.comum.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Tamanho")
public @Data class FiltroTamanhoDTO implements Serializable {
    private static final long serialVersionUID = 7616722014159043532L;

    @ApiModelProperty(value = "Tamanho")
    private String tamanho;

    @ApiModelProperty(value = "Ativo")
    private String ativo;

}
