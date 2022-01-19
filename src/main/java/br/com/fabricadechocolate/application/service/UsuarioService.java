/*
 * UsuarioService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.service;

import br.com.fabricadechocolate.application.configuration.Constante;
import br.com.fabricadechocolate.application.exception.SistemaMessageCode;
import br.com.fabricadechocolate.application.repository.UsuarioRepository;
import br.com.fabricadechocolate.comum.exception.BusinessException;
import br.com.fabricadechocolate.comum.util.CollectionUtil;
import br.com.fabricadechocolate.comum.util.Util;
import br.com.fabricadechocolate.application.dto.AuthDTO;
import br.com.fabricadechocolate.application.dto.FiltroUsuarioDTO;
import br.com.fabricadechocolate.application.dto.UsuarioDTO;
import br.com.fabricadechocolate.application.dto.UsuarioSenhaDTO;
import br.com.fabricadechocolate.application.enums.StatusAtivoInativo;
import br.com.fabricadechocolate.application.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe de négocio referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailEngineService emailService;

	@Autowired
	private AuthService authService;

    /**
     * Persiste os dados do {@link Usuario}.
     *
     * @param usuario
     * @return
     */
	public Usuario salvar(Usuario usuario) {
		validarCamposObrigatorios(usuario);

		if (usuario.getId() == null) {

			usuario.setStatus(StatusAtivoInativo.ATIVO);
			LocalDate dataCadastro = LocalDate.now();
			usuario.setSenha(new BCryptPasswordEncoder().encode("123456"));

		} else {
			Usuario vigente = getById(usuario.getId());

			usuario.setStatus(vigente.getStatus());
			usuario.setSenha(vigente.getSenha());
		}

		usuario = usuarioRepository.save(usuario);
		return usuario;
	}

    /**
     * Verifica se os campos obrigatorios de {@link Usuario} foram preenchidos.
     *
     * @param usuario
     */
	private void validarCamposObrigatorios(final Usuario usuario) {
		boolean invalido = Boolean.FALSE;

		if (Util.isEmpty(usuario.getLogin())) {
			invalido = Boolean.TRUE;
		}

		if (invalido) {
			throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
		}
	}

    /**
     * Retorna a instância do {@link Usuario} conforme o 'login' informado.
     * 
     * @param login
     * @return
     */
	public Usuario getByLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}

    /**
     * Retorna a Lista de {@link UsuarioDTO} conforme o filtro pesquisado.
     * 
     * @param filtroDTO
     * @return
     */
	public List<Usuario> getUsuariosByFiltro(FiltroUsuarioDTO filtroDTO) {
		validarCamposObrigatoriosFiltro(filtroDTO);

		List<Usuario> usuarios = usuarioRepository.findAllByFiltro(filtroDTO);

		if (CollectionUtil.isEmpty(usuarios)) {
			throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
		}

		return usuarios;
	}

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
	private void validarCamposObrigatoriosFiltro(final FiltroUsuarioDTO filtroDTO) {
		boolean vazio = Boolean.TRUE;

		if (!Util.isEmpty(filtroDTO.getNome())) {
			vazio = Boolean.FALSE;

			if (filtroDTO.getNome().trim().length() < Integer.parseInt(Constante.TAMANHO_MINIMO_PESQUISA_NOME)) {
				throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_INSUFICIENTE_FILTRO_NOME);
			}
		}

		if (!Util.isEmpty(filtroDTO.getLogin())) {
			vazio = Boolean.FALSE;
		}

		if (!Util.isEmpty(filtroDTO.getIdStatus())) {
			vazio = Boolean.FALSE;
		}

		if (vazio) {
			throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
		}
	}

    /**
     * Retorna a instância de {@link Usuario} conforme o 'id' informado.
     *
     * @param id -
     * @return -
     */
	public Usuario getById(final Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	/**
	 * Retorna a instância de {@link Usuario} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	public Usuario getByIdFetch(final Long id) {
		return usuarioRepository.findByIdFetch(id).orElse(null);
	}

    /**
     * Valida se as senhas foram preenchidas e se são iguais
     *
     * @param redefinirSenhaDTO -
     */
	private void validarConformidadeSenha(final UsuarioSenhaDTO redefinirSenhaDTO) {
		if (Util.isEmpty(redefinirSenhaDTO.getNovaSenha()) || Util.isEmpty(redefinirSenhaDTO.getConfirmarSenha())) {
			throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
		}

		if (!redefinirSenhaDTO.getNovaSenha().equals(redefinirSenhaDTO.getConfirmarSenha())) {
			throw new BusinessException(SistemaMessageCode.ERRO_SENHAS_DIFERENTES);
		}
	}

    /**
     * Valida se a senha corrente foi preenchida e corresponde a senha armazenada no
     * keycloak.
     *
     * @param usuario -
     * @param senhaAntiga -
     */
	private void validarSenhaCorrente(Usuario usuario, String senhaAntiga) {
		if (Util.isEmpty(senhaAntiga)) {
			throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
		}

		AuthDTO authDTO = new AuthDTO();
		authDTO.setLogin(usuario.getLogin());
		authDTO.setSenha(senhaAntiga);
		if (!authService.loginByPassword(usuario, authDTO)) {
			throw new BusinessException(SistemaMessageCode.ERRO_SENHA_ANTERIOR_INCORRETA);
		}
	}

    /**
     * Realiza a inclusão ou alteração de senha do {@link Usuario}.
     *
     * @param usuarioSenhaDTO -
     */
	public Usuario redefinirSenha(final UsuarioSenhaDTO usuarioSenhaDTO) {
		Usuario usuario = getById(usuarioSenhaDTO.getIdUsuario());

		validarConformidadeSenha(usuarioSenhaDTO);

		if (!usuarioSenhaDTO.isAtivacao() && !usuarioSenhaDTO.isRecuperacao()) {
			validarSenhaCorrente(usuario, usuarioSenhaDTO.getSenhaAntiga());
		} else {
			usuario.setStatus(StatusAtivoInativo.ATIVO);
		}
		usuario.setSenha(usuarioSenhaDTO.getNovaSenha());
		return usuarioRepository.save(usuario);
	}

    /**
     * Inativa o {@link Usuario}.
     *
     * @param id
     * @return
     */
	public Usuario inativar(final Long id) {
		Usuario usuario = getById(id);
		usuario.setStatus(StatusAtivoInativo.INATIVO);
		return usuarioRepository.save(usuario);
	}

    /**
     * Ativa o {@link Usuario}.
     *
     * @param id
     * @return
     */
	public Usuario ativar(final Long id) {
		Usuario usuario = getById(id);
		usuario.setStatus(StatusAtivoInativo.ATIVO);
		return usuarioRepository.save(usuario);
	}
}
