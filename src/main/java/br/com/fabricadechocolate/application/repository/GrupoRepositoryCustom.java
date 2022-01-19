/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.repository;


import br.com.fabricadechocolate.application.dto.FiltroGrupoDTO;
import br.com.fabricadechocolate.application.model.Grupo;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface GrupoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link FiltroGrupoDTO} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroGrupoDTO
	 * @return
	 */
	public List<Grupo> findAllByFiltro(FiltroGrupoDTO filtroGrupoDTO);

	/**
	 * Retorna uma lista de {@link FiltroGrupoDTO} conforme o 'id' do Sistema informado.
	 * 
	 * @param idSistema
	 * @return
	 */
	public List<Grupo> getGruposAtivosByIdSistema(final Long idSistema);

}
