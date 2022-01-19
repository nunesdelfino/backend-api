package br.com.fabricadechocolate.application.repository.impl;

import br.com.fabricadechocolate.application.model.TipoOvo;
import br.com.fabricadechocolate.comum.util.Util;
import br.com.fabricadechocolate.application.dto.FiltroTipoOvoDTO;
import br.com.fabricadechocolate.application.repository.TipoOvoRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TipoOvoRepositoryImpl implements TipoOvoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<TipoOvo> findAllByFiltro(FiltroTipoOvoDTO filtroTipoOvoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT tipo FROM TipoOvo tipo");
        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroTipoOvoDTO.getTipo())) {
            jpql.append(" AND UPPER(tipo.tipo) LIKE UPPER('%' || :tipo || '%')  ");
            parametros.put("tipo", filtroTipoOvoDTO.getTipo());
        }

        if (filtroTipoOvoDTO.getAtivo() != null) {
            jpql.append(" AND tipo.ativo = :ativo  ");
            parametros.put("ativo", filtroTipoOvoDTO.getAtivo());
        }

        TypedQuery<TipoOvo> query = entityManager.createQuery(jpql.toString(), TipoOvo.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }

    @Override
    public Long countByTipo(String nome) {
        return null;
    }

}
