/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.repository;


import br.com.fabricadechocolate.application.dto.FiltroRelatoriosDTO;
import br.com.fabricadechocolate.application.model.Grupo;
import br.com.fabricadechocolate.application.model.Pedido;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface RelatoriosRepositoryCustom {

	/**
	 * Retorna uma lista de {@link Pedido} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroPedidoDTO
	 * @return
	 */
//	public List<Pedido> findAllByFiltro(FiltroRelatorioClienteDTO filtroPedidoDTO);

	public List<Pedido> findRealtoriosFiltro(FiltroRelatoriosDTO filtroPedidoDTO);

//	public List<Pedido> findRealtorioVendasFiltro(FiltroRelatoriosDTO filtroPedidoDTO);



}
