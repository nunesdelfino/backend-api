package br.com.fabricadechocolate.application.repository.impl;

import br.com.fabricadechocolate.application.dto.FiltroRelatoriosDTO;
import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.repository.RelatoriosRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RelatoriosRepositoryImpl implements RelatoriosRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Pedido> findRealtoriosFiltro(FiltroRelatoriosDTO filtroPedidoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT pedido FROM Pedido pedido");
        jpql.append(" INNER JOIN FETCH pedido.tamanho tamanho");
        jpql.append(" INNER JOIN FETCH pedido.saborUm sabor");
        jpql.append(" LEFT JOIN FETCH pedido.saborDois sabor");
        jpql.append(" LEFT JOIN FETCH pedido.saborTres sabor");
        jpql.append(" LEFT JOIN FETCH pedido.saborQuatro sabor");
        jpql.append(" LEFT JOIN FETCH pedido.saborCinco sabor");

        jpql.append(" WHERE 1=1 ");
        jpql.append(" AND pedido.statusEntrega = 'true' ");

        if (filtroPedidoDTO.getDataFinal() != null) {
            jpql.append(" AND pedido.dataEntrega >= :dataInicio ");
            parametros.put("dataInicio", filtroPedidoDTO.getDataInicio());
        }

        if (filtroPedidoDTO.getDataFinal() != null) {
            jpql.append(" AND pedido.dataEntrega <= :dataFinal ");
            parametros.put("dataFinal", filtroPedidoDTO.getDataFinal());
        }

        TypedQuery<Pedido> query = entityManager.createQuery(jpql.toString(), Pedido.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }

}
