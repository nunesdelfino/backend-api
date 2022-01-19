/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.repository;


import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.dto.FiltroPedidoDTO;
import br.com.fabricadechocolate.application.model.Grupo;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface PedidoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link Pedido} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroPedidoDTO
	 * @return
	 */
	public List<Pedido> findAllByFiltro(FiltroPedidoDTO filtroPedidoDTO);



}
