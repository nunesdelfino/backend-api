package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface RelatorioClienteRepository extends JpaRepository<Pedido, Long>, RelatorioClienteRepositoryCustom {

    /**
     * Listar todos os Pedidos
     * @return
     */
    @Query("SELECT pedido from Pedido pedido " +
            " INNER JOIN FETCH pedido.tamanho tamanho " +
            " INNER JOIN FETCH pedido.saborUm sabor " +
            " LEFT JOIN FETCH pedido.saborDois sabor " +
            " LEFT JOIN FETCH pedido.saborTres sabor " +
            " LEFT JOIN FETCH pedido.saborQuatro sabor " +
            " LEFT JOIN FETCH pedido.saborCinco sabor ")
    public List<Pedido> getTodos();



}
