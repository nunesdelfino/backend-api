/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.repository;


import br.ueg.modelo.application.dto.FiltroPedidoDTO;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.Pedido;

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
