package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.dto.filtro.FiltroGastoDTO;
import br.com.fabricadechocolate.application.model.Gasto;

import java.util.List;

public interface GastoRepositoryCustom {

    public List<Gasto> findAllByFiltro(FiltroGastoDTO filtrogasto);

}
