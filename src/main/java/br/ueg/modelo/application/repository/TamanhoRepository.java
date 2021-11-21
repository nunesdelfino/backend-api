package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.model.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Classe de persistência referente a entidade {@link Tamanho}.
 *
 * @author UEG
 */
@Repository
public interface TamanhoRepository extends JpaRepository<Tamanho, Long>, TamanhoRepositoryCustom {

    /**
     * Retorna o número de {@link Tamanho} pelo 'nome'
     *
     * @param nome
     * @param idTamanho
     * @return
     */
    @Query("SELECT COUNT(tamanho) FROM Tamanho tamanho " +
            " WHERE lower(tamanho.tamanho) LIKE lower(:nome)" +
            " AND (:idTamanho IS NULL OR tamanho.id != :idTamanho)")
    public Long countByNomeAndNotId(String nome, Long idTamanho);

}
