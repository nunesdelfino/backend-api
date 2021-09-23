package br.ueg.modelo.application.repository.impl;

import br.ueg.modelo.application.dto.FiltroSaborDTO;
import br.ueg.modelo.application.dto.FiltroTamanhoDTO;
import br.ueg.modelo.application.model.Sabor;
import br.ueg.modelo.application.model.Tamanho;
import br.ueg.modelo.application.repository.SaborRepositoryCustom;
import br.ueg.modelo.application.repository.TamanhoRepositoryCustom;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SaborRepositoryImpl implements SaborRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Sabor> findAllByFiltro(FiltroSaborDTO filtroSaborDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT sabor FROM Sabor sabor");
        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroSaborDTO.getSabor())) {
            jpql.append(" AND UPPER(sabor.sabor) LIKE UPPER('%' || :sabor || '%')  ");
            parametros.put("sabor", filtroSaborDTO.getSabor());
        }

        if (filtroSaborDTO.getAtivo() != null) {
            jpql.append(" AND sabor.ativo = :ativo  ");
            parametros.put("ativo", filtroSaborDTO.getAtivo());
        }

        TypedQuery<Sabor> query = entityManager.createQuery(jpql.toString(), Sabor.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }

    @Override
    public Long countBySaborOvo(String nome) {
        return null;
    }

}
