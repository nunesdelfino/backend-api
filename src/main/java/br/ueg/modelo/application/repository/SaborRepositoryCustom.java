package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.dto.FiltroSaborDTO;
import br.ueg.modelo.application.dto.FiltroTamanhoDTO;
import br.ueg.modelo.application.model.Sabor;
import br.ueg.modelo.application.model.Tamanho;

import java.util.List;

public interface SaborRepositoryCustom {

    public List<Sabor> findAllByFiltro(FiltroSaborDTO filtrosabor);

    public Long countBySaborOvo(String nome);

}
