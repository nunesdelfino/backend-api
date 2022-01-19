package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.TipoOvo;
import br.com.fabricadechocolate.application.dto.FiltroTipoOvoDTO;

import java.util.List;

public interface TipoOvoRepositoryCustom {

    public List<TipoOvo> findAllByFiltro(FiltroTipoOvoDTO filtroTipoOvoDTO);

    public Long countByTipo(String nome);

}
