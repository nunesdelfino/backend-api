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
class RelatorioVendasDTO implements Serializable {

    @ApiModelProperty(value = "id do Pedido")
    private Long id;

    @ApiModelProperty(value = "Tipo Ovo")
    private String tipoOvo;

    @ApiModelProperty(value = "Nome do Tamanho")
    private String nomeTamanho;
//
    @ApiModelProperty(value = "Nome do Sabor Um")
    private String nomeSaborUm;

    @ApiModelProperty(value = "Nome do Sabor Dois")
    private String nomeSaborDois;

    @ApiModelProperty(value = "Nome do Sabor Tres")
    private String nomeSaborTres;

    @ApiModelProperty(value = "Nome do Sabor Quatro")
    private String nomeSaborQuatro;

    @ApiModelProperty(value = "Nome do Sabor Cinco")
    private String nomeSaborCinco;

}
