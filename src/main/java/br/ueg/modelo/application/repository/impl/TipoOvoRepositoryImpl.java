package br.ueg.modelo.application.repository.impl;

import br.ueg.modelo.application.dto.FiltroTamanhoDTO;
import br.ueg.modelo.application.dto.FiltroTipoOvoDTO;
import br.ueg.modelo.application.model.Tamanho;
import br.ueg.modelo.application.model.TipoOvo;
import br.ueg.modelo.application.repository.TamanhoRepositoryCustom;
import br.ueg.modelo.application.repository.TipoOvoRepositoryCustom;
import br.ueg.modelo.comum.util.Util;
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
