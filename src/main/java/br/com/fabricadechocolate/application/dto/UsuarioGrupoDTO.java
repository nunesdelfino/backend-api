/*
 * UsuarioTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.com.fabricadechocolate.application.dto;

import br.com.fabricadechocolate.application.model.UsuarioGrupo;
import br.com.fabricadechocolate.comum.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Classe de transferência referente a entidade {@link UsuarioGrupo}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Usuario Grupos")
public @Data class UsuarioGrupoDTO implements Serializable {

	private static final long serialVersionUID = -813015525141429296L;

	@ApiModelProperty(value = "Código do Usuário Grupo")
	private String id;

	@ApiModelProperty(value = "Código do Usuário")
	private String idUsuario;

	@ApiModelProperty(value = "Código do Grupo")
	private String idGrupo;

	@ApiModelProperty(value = "Nome do Grupo")
	private String nomeGrupo;

	/**
	 * @return the id
	 */
	@JsonIgnore
	public Long getIdLong() {
		Long idLong = null;

		if (!Util.isEmpty(id)) {
			idLong = Long.parseLong(id);
		}
		return idLong;
	}
}
