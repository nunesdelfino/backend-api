package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.TipoOvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Classe de persistência referente a entidade {@link TipoOvo}.
 *
 * @author UEG
 */
@Repository
public interface TipoOvoRepository extends JpaRepository<TipoOvo, Long>, TipoOvoRepositoryCustom {

    /**
     * Retorna o número de {@link TipoOvo} pelo 'nome'
     *
     * @param nome
     * @param idTipo
     * @return
     */
    @Query("SELECT COUNT(tipo) FROM TipoOvo tipo " +
            " WHERE lower(tipo.tipo) LIKE lower(:nome)" +
            " AND (:idTipo IS NULL OR tipo.id != :idTipo)")
    public Long countByNomeAndNotId(String nome, Long idTipo);

}
