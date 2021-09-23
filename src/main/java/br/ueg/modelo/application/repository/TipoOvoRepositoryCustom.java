package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.dto.FiltroTipoOvoDTO;
import br.ueg.modelo.application.model.TipoOvo;

import java.util.List;

public interface TipoOvoRepositoryCustom {

    public List<TipoOvo> findAllByFiltro(FiltroTipoOvoDTO filtroTipoOvoDTO);

    public Long countByTipo(String nome);

}
