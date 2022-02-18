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
public interface PedidoRepository extends JpaRepository<Pedido, Long>, PedidoRepositoryCustom {

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
            " LEFT JOIN FETCH pedido.saborCinco sabor " +
            " WHERE pedido.status = 'aceitopg' OR pedido.status = 'aceitonpg' "
    )
    public List<Pedido> getPedidosAceitos();

    /**
     * Busca uma {@link Pedido} pelo id Informado
     *
     * @param idPedido
     * @return
     */
    @Query("SELECT pedido from Pedido pedido " +
            " INNER JOIN FETCH pedido.tamanho tamanho " +
            " INNER JOIN FETCH pedido.saborUm sabor " +
            " LEFT JOIN FETCH pedido.saborDois sabor " +
            " LEFT JOIN FETCH pedido.saborTres sabor " +
            " LEFT JOIN FETCH pedido.saborQuatro sabor " +
            " LEFT JOIN FETCH pedido.saborCinco sabor " +
            " WHERE pedido.id = :idPedido ")
    public Optional<Pedido> findByIdFetch( @Param("idPedido") final Long idPedido);

    /**
     * Listar todos os Pedidos Entregues
     * @return
     */
    @Query("SELECT pedido from Pedido pedido " +
            " INNER JOIN FETCH pedido.tamanho tamanho " +
            " INNER JOIN FETCH pedido.saborUm sabor " +
            " LEFT JOIN FETCH pedido.saborDois sabor " +
            " LEFT JOIN FETCH pedido.saborTres sabor " +
            " LEFT JOIN FETCH pedido.saborQuatro sabor " +
            " LEFT JOIN FETCH pedido.saborCinco sabor " +
            " WHERE pedido.statusEntrega = true"
    )
    public List<Pedido> listarEntregues();

    /**
     * Listar todos os Pedidos Entregues
     * @return
     */
    @Query("SELECT pedido from Pedido pedido " +
            " INNER JOIN FETCH pedido.tamanho tamanho " +
            " INNER JOIN FETCH pedido.saborUm sabor " +
            " LEFT JOIN FETCH pedido.saborDois sabor " +
            " LEFT JOIN FETCH pedido.saborTres sabor " +
            " LEFT JOIN FETCH pedido.saborQuatro sabor " +
            " LEFT JOIN FETCH pedido.saborCinco sabor " +
            " WHERE pedido.statusEntrega = false"
    )
    public List<Pedido> listarNaoEntregues();

    @Query("SELECT pedido from Pedido pedido " +
            " INNER JOIN FETCH pedido.tamanho tamanho " +
            " INNER JOIN FETCH pedido.saborUm sabor " +
            " LEFT JOIN FETCH pedido.saborDois sabor " +
            " LEFT JOIN FETCH pedido.saborTres sabor " +
            " LEFT JOIN FETCH pedido.saborQuatro sabor " +
            " LEFT JOIN FETCH pedido.saborCinco sabor " +
            " WHERE pedido.status = 'aceitopg' OR pedido.status = 'aceitonpg' OR  pedido.status = 'pendente'")
    public List<Pedido> getAceitosPendentes();

}
