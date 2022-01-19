/*
 * AuthClaimResolve.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.service;

import br.com.fabricadechocolate.application.configuration.Constante;
import br.com.fabricadechocolate.application.security.TokenBuilder;
import br.com.fabricadechocolate.application.dto.UsuarioSenhaDTO;
import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

/**
 * Class resolves responsible for encapsulating the complexity of retrieving JWT
 * Token claim parameters.
 * 
 * @author UEG
 */
public class AuthClaimResolve {

	private final Map<String, Claim> claims;

	/**
	 * Constructor classe.
	 * 
	 * @param claims -
	 */
	private AuthClaimResolve(final Map<String, Claim> claims) {
		this.claims = claims;
	}

	/**
	 * Instance Factory {@link AuthClaimResolve}.
	 * 
	 * @param claims -
	 * @return -
	 */
	public static AuthClaimResolve newInstance(final Map<String, Claim> claims) {
		return new AuthClaimResolve(claims);
	}

	/**
	 * @return Retorna o login do usuário conforme o mapa de claims informado.
	 */
	public String getLogin() {
		Claim claim = claims.get(Constante.PARAM_LOGIN);
		return claim != null && !claim.isNull() ? claim.asString() : null;
	}

	/**
	 * @return Retorna o email do usuário conforme o mapa de claims informado.
	 */
	public String getEmail() {
		Claim claim = claims.get(Constante.PARAM_EMAIL);
		return claim != null && !claim.isNull() ? claim.asString() : null;
	}

	/**
	 * @return Retorna o nome do usuário conforme o mapa de claims informado.
	 */
	public String getNome() {
		Claim claim = claims.get(Constante.PARAM_NAME);
		return claim != null && !claim.isNull() ? claim.asString() : null;
	}

	/**
	 * @return Retorna o expiresIn do token conforme o mapa de claims informado.
	 */
	public Long getExpiresIn() {
		Claim claim = claims.get(Constante.PARAM_EXPIRES_IN);
		return claim != null && !claim.isNull() ? claim.asLong() : null;
	}

	/**
	 * @return Retorna o expiresIn do token conforme o mapa de claims informado.
	 */
	public Long getRefreshExpiresIn() {
		Claim claim = claims.get(Constante.PARAM_REFRESH_EXPIRES_IN);
		return claim != null && !claim.isNull() ? claim.asLong() : null;
	}

	/**
	 * @return Retorna o id do Usuário conforme o mapa de claims informado.
	 */
	public Long getIdUsuario() {
		Claim claim = claims.get(Constante.PARAM_ID_USUARIO);
		return claim != null && !claim.isNull() ? claim.asLong() : null;
	}

	/**
	 * @return Retorna o {@link TokenBuilder.TokenType} conforme o mapa de claims informado.
	 */
	public TokenBuilder.TokenType getTokenType() {
		TokenBuilder.TokenType type = null;
		Claim claim = claims.get(Constante.PARAM_TYPE);

		if (claim != null && !claim.isNull()) {
			String value = claim.asString();
			type = TokenBuilder.TokenType.valueOf(value);
		}
		return type;
	}

	/**
	 * @return Verifica se o {@link TokenBuilder.TokenType} é igual a 'ACCESS'.
	 */
	public boolean isTokenTypeAccess() {
		TokenBuilder.TokenType type = getTokenType();
		return TokenBuilder.TokenType.ACCESS.equals(type);
	}

	/**
	 * @return Verifica se o {@link TokenBuilder.TokenType} é igual a 'REFRESH'.
	 */
	public boolean isTokenTypeRefresh() {
		TokenBuilder.TokenType type = getTokenType();
		return TokenBuilder.TokenType.REFRESH.equals(type);
	}

	/**
	 * @return Retorna a instância de {@link UsuarioSenhaDTO.TipoRedefinicaoSenha} conforme o mapa de claims informado.
	 */
	public UsuarioSenhaDTO.TipoRedefinicaoSenha getTipoRedefinicaoSenha() {
		UsuarioSenhaDTO.TipoRedefinicaoSenha tipoRedefinicaoSenha = null;
		Claim claim = claims.get(Constante.PARAM_TIPO_REDEFINICAO_SENHA);

		if (claim != null && !claim.isNull()) {
			tipoRedefinicaoSenha = claim.as(UsuarioSenhaDTO.TipoRedefinicaoSenha.class);
		}
		return tipoRedefinicaoSenha;
	}

}
