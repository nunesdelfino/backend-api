package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface RelatoriosRepository extends JpaRepository<Pedido, Long>, RelatoriosRepositoryCustom {

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
