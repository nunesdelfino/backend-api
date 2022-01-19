/*
 * Usuario.java
 * Copyright (c) UEG.
 */
package br.com.fabricadechocolate.application.model;

import br.com.fabricadechocolate.application.configuration.Constante;
import br.com.fabricadechocolate.application.enums.StatusAtivoInativo;
import br.com.fabricadechocolate.application.enums.converter.StatusAtivoInativoConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe de representação de 'Sistema'.
 *
 * @author UEG
 */
@Entity
// Indica qual a tabela de versionamento será utilzada, opcional se se utilizar o padrão
@Table(name = "TBL_USUARIO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name = "TBL_S_USUARIO", sequenceName = "TBL_S_USUARIO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data class Usuario implements Serializable{

	private static final long serialVersionUID = -8899975090870463525L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_USUARIO")
	@Column(name = "CODG_USUARIO", nullable = false)
	private Long id;

	@Column(name = "LOGIN_USUARIO", length = 20, nullable = false)
	private String login;

	@Column(name = "SENHA_USUARIO", length = 255, nullable = false)
	private String senha;

	@Column(name = "NOME_USUARIO", length = 65, nullable = false)
	private String nome;

	@Convert(converter = StatusAtivoInativoConverter.class)
	@Column(name = "STAT_USUARIO", nullable = false, length = 1)
	private StatusAtivoInativo status;
//
//	@EqualsAndHashCode.Exclude
//	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<UsuarioGrupo> grupos;


	/**
	 * Verifica se o Status do Usuário é igual a 'Ativo'.
	 *
	 * @return -
	 */
	@Transient
	public boolean isStatusAtivo() {
		return status != null && StatusAtivoInativo.ATIVO.getId().equals(status.getId());
	}

}
