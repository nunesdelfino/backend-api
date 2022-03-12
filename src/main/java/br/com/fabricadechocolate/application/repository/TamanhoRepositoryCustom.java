package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.Tamanho;
import br.com.fabricadechocolate.application.dto.filtro.FiltroTamanhoDTO;

import java.util.List;

public interface TamanhoRepositoryCustom {

    public List<Tamanho> findAllByFiltro(FiltroTamanhoDTO filtroTamanho);

    public Long countByTamanho(String nome);

}
