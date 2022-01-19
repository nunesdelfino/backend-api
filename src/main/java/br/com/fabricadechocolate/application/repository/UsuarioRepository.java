/*
 * UsuarioRepository.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface UsuarioRepository extends UsuarioRepositoryCustom, JpaRepository<Usuario, Long> {
	// TODO Garantir que somente usuários com ID_ORIGEM_CADASTRO = PORTALSSO

	/**
	 * Retorna a instância do {@link Usuario} conforme o 'login' informado.
	 * 
	 * @param login
	 * @return
	 */
	public Usuario findByLogin(final String login);

	/**
	 * Retorna uma instância de {@link Usuario} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	@Query(" SELECT usuario FROM Usuario usuario WHERE usuario.id = :id")
	public Optional<Usuario> findByIdFetch(@Param("id") final Long id);


}
