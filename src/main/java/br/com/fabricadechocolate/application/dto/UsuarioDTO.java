/*
 * UsuarioDTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.com.fabricadechocolate.application.dto;

import br.com.fabricadechocolate.application.model.Usuario;
import br.com.fabricadechocolate.comum.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Classe de transferência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Usuario")
public @Data class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = -3145730384721847808L;

	@ApiModelProperty(value = "Código do Usuário")
	private String id;

	@Size(max = 20)
	@ApiModelProperty(value = "Login do Usuário")
	private String login;

	@Size(max = 65)
	@ApiModelProperty(value = "Nome do Usuário")
	private String nome;

	@ApiModelProperty(value = "Código do Status do Usuário")
	private String status;

	@ApiModelProperty(value = "Senha do Usuário")
	private String senha;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Long idUsuarioLogado;

	public UsuarioDTO(String id, String login) {
		this.id = id;
		this.login = login;
	}

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
