package br.com.fabricadechocolate.application.repository.impl;

import br.com.fabricadechocolate.application.dto.FiltroTamanhoDTO;
import br.com.fabricadechocolate.application.model.Tamanho;
import br.com.fabricadechocolate.application.repository.TamanhoRepositoryCustom;
import br.com.fabricadechocolate.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TamanhoRepositoryImpl implements TamanhoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Tamanho> findAllByFiltro(FiltroTamanhoDTO filtroTamanhoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT tamanho FROM Tamanho tamanho");
        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroTamanhoDTO.getTamanho())) {
            jpql.append(" AND UPPER(tamanho.tamanho) LIKE UPPER('%' || :tamanho || '%')  ");
            parametros.put("tamanho", filtroTamanhoDTO.getTamanho());
        }

        if (filtroTamanhoDTO.getAtivo() != null) {
            jpql.append(" AND tamanho.ativo = :ativo  ");
            parametros.put("ativo", filtroTamanhoDTO.getAtivo());
        }

        TypedQuery<Tamanho> query = entityManager.createQuery(jpql.toString(), Tamanho.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }

    @Override
    public Long countByTamanho(String nome) {
        return null;
    }

}
