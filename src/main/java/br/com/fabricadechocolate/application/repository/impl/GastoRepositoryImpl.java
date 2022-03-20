package br.com.fabricadechocolate.application.repository.impl;

import br.com.fabricadechocolate.application.dto.filtro.FiltroGastoDTO;
import br.com.fabricadechocolate.application.model.Gasto;
import br.com.fabricadechocolate.comum.util.Util;
import br.com.fabricadechocolate.application.repository.GastoRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GastoRepositoryImpl implements GastoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Gasto> findAllByFiltro(FiltroGastoDTO filtroGastoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT gasto FROM Gasto gasto");
        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroGastoDTO.getItem())){
            jpql.append(" AND UPPER(gasto.item) LIKE UPPER('%' || :item || '%')  ");
            parametros.put("item", filtroGastoDTO.getItem());
        }

        if (!Util.isEmpty(filtroGastoDTO.getNomeEstabelecimento())){
            jpql.append(" AND UPPER(gasto.nomeEstabelecimento) LIKE UPPER('%' || :nomeEstabelecimento || '%')  ");
            parametros.put("nomeEstabelecimento", filtroGastoDTO.getNomeEstabelecimento());
        }

        if (!Util.isEmpty(filtroGastoDTO.getData())){
            jpql.append(" AND UPPER(gasto.data) LIKE UPPER('%' || :data || '%')  ");
            parametros.put("data", filtroGastoDTO.getData());
        }

        TypedQuery<Gasto> query = entityManager.createQuery(jpql.toString(), Gasto.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }

}
