package br.com.fabricadechocolate.application.repository.impl;

import br.com.fabricadechocolate.application.dto.filtro.FiltroPedidoDTO;
import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.repository.PedidoRepositoryCustom;
import br.com.fabricadechocolate.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PedidoRepositoryImpl implements PedidoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Pedido> findAllByFiltro(FiltroPedidoDTO filtroPedidoDTO) {
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

        if (!Util.isEmpty(filtroPedidoDTO.getNome())) {
            jpql.append(" AND UPPER(pedido.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroPedidoDTO.getNome());
        }

        if (filtroPedidoDTO.getIdTamanho()!=null) {
            jpql.append(" AND pedido.tamanho.id = :idTamanho ");
            parametros.put("idTamanho", filtroPedidoDTO.getIdTamanho());
        }

        if (!Util.isEmpty(filtroPedidoDTO.getStatus())) {
            jpql.append(" AND UPPER(pedido.status) LIKE UPPER('%' || :status || '%') ");
            parametros.put("status", filtroPedidoDTO.getStatus());
        }

        if (!Util.isEmpty(filtroPedidoDTO.getDataEntrega())) {
            jpql.append(" AND UPPER(pedido.dataEntrega) LIKE UPPER('%' || :dataEntrega || '%') ");
            parametros.put("dataEntrega", filtroPedidoDTO.getDataEntrega());
        }

        TypedQuery<Pedido> query = entityManager.createQuery(jpql.toString(), Pedido.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
