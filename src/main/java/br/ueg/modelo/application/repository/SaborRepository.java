package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.model.Sabor;
import br.ueg.modelo.application.model.Tamanho;
import br.ueg.modelo.application.model.TipoAmigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Classe de persistência referente a entidade {@link Tamanho}.
 *
 * @author UEG
 */
@Repository
public interface SaborRepository extends JpaRepository<Sabor, Long>, SaborRepositoryCustom {

    /**
     * Retorna o número de {@link Sabor} pelo 'nome' , desconsiderando o
     * 'TipoAmigo' com o 'id' informado.
     *
     * @param nome
     * @param idSabor
     * @return
     */
    @Query("SELECT COUNT(sabor) FROM Sabor sabor " +
            " WHERE lower(sabor.sabor) LIKE lower(:nome)" +
            " AND (:idSabor IS NULL OR sabor.id != :idSabor)")
    public Long countByNomeAndNotId(String nome, Long idSabor);

}
