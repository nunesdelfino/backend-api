/*
 * UsuarioRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.repository;

import java.util.List;

import br.com.fabricadechocolate.application.dto.filtro.FiltroUsuarioDTO;
import br.com.fabricadechocolate.application.dto.outros.UsuarioDTO;
import br.com.fabricadechocolate.application.model.Usuario;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 * 
 * @author UEG
 */
public interface UsuarioRepositoryCustom {

	/**
	 * Retorna a Lista de {@link UsuarioDTO} conforme o login pesquisado.
	 * 
	 * @param login -
	 * @return -
	 */
	public List<UsuarioDTO> findAllByLoginIgnoreCaseContaining(final String login);

	/**
	 * Retorna a Lista de {@link UsuarioDTO} conforme o filtro pesquisado.
	 * 
	 * @param filtroTO -
	 * @return -
	 */
	public List<Usuario> findAllByFiltro(FiltroUsuarioDTO filtroTO);

}
