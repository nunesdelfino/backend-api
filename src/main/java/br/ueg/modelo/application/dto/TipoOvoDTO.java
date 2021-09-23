package br.ueg.modelo.application.dto;

import br.ueg.modelo.application.enums.StatusSimNao;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Tamanho")
public @Data class TipoOvoDTO implements Serializable {

    @ApiModelProperty(value = "id tamanho")
    private Long id;

    @ApiModelProperty(value = "Tipo ovo")
    private String tipo;

    @ApiModelProperty(value = "Tipo ovo ativo")
    private StatusSimNao ativo;


}
