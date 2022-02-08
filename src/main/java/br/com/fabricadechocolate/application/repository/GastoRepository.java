package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Classe de persistência referente a entidade {@link Gasto}.
 *
 * @author UEG
 */
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long>, GastoRepositoryCustom {

}
