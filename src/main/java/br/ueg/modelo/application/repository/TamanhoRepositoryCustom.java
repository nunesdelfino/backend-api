package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.dto.FiltroTamanhoDTO;
import br.ueg.modelo.application.model.Tamanho;

import java.util.List;

public interface TamanhoRepositoryCustom {

    public List<Tamanho> findAllByFiltro(FiltroTamanhoDTO filtroTamanho);

    public Long countByTamanho(String nome);

}
