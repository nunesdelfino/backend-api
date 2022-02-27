package br.com.fabricadechocolate.application.repository.impl;

import br.com.fabricadechocolate.application.dto.FiltroPedidoDTO;
import br.com.fabricadechocolate.application.dto.FiltroRelatorioClienteDTO;
import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.repository.PedidoRepositoryCustom;
import br.com.fabricadechocolate.application.repository.RelatorioClienteRepositoryCustom;
import br.com.fabricadechocolate.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RelatorioClienteRepositoryImpl implements RelatorioClienteRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Pedido> findAllByFiltro(FiltroRelatorioClienteDTO filtroPedidoDTO) {
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

        if (filtroPedidoDTO.getDataFinal() != null) {
            jpql.append(" AND pedido.dataEntrega > :dataInicio ");
            parametros.put("dataInicio", filtroPedidoDTO.getDataInicio());
        }

        if (filtroPedidoDTO.getDataFinal() != null) {
            jpql.append(" AND pedido.dataEntrega < :dataFinal ");
            parametros.put("dataFinal", filtroPedidoDTO.getDataFinal());
        }

        TypedQuery<Pedido> query = entityManager.createQuery(jpql.toString(), Pedido.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
