package br.com.fabricadechocolate.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Tamanho")
public @Data class TamanhoDTO implements Serializable {

    @ApiModelProperty(value = "id tamanho")
    private Long id;

    @ApiModelProperty(value = "tamanho ovo")
    private String tamanho;

    @ApiModelProperty(value = "tamanho ativo")
    private String ativo;


}
