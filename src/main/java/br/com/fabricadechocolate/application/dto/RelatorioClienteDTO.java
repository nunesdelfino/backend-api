package br.com.fabricadechocolate.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data
class RelatorioClienteDTO implements Serializable {

    private Long id;

    private String nome;

    private String numero;

    private String tipoOvo;

    private Long idSaborUm;

    private String nomeSaborUm;

    private Long idSaborDois;

    private String nomeSaborDois;

    private Long idSaborTres;

    private String nomeSaborTres;

    private Long idSaborQuatro;

    private String nomeSaborQuatro;

    private Long idSaborCinco;

    private String nomeSaborCinco;

    private Double preco;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dataEntrega;

}
