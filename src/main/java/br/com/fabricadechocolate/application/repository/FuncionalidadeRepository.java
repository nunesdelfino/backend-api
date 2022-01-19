package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.Funcionalidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {
}
