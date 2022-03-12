package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.Sabor;
import br.com.fabricadechocolate.application.dto.filtro.FiltroSaborDTO;

import java.util.List;

public interface SaborRepositoryCustom {

    public List<Sabor> findAllByFiltro(FiltroSaborDTO filtrosabor);

    public Long countBySaborOvo(String nome);

}
