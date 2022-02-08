/*
 * AuthenticationProviderImpl.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.security;

import br.com.fabricadechocolate.api.security.AuthenticationProvider;
import br.com.fabricadechocolate.application.dto.CredencialDTO;
import br.com.fabricadechocolate.application.service.AuthService;
import br.com.fabricadechocolate.comum.exception.BusinessException;
import br.com.fabricadechocolate.comum.security.Credential;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por prover a instância de {@link Authentication} com as
 * credenciais do Usuário logado.
 * 
 * @author UEG
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private AuthService authService;

	/**
	 * @see AuthenticationProvider#getAuthentication(String)
	 */
	@Override
	public Credential getAuthentication(final String accessToken) {
		Credential credential = null;

		try {
			CredencialDTO credencialDTO = authService.getInfoByToken(accessToken);

			if (credencialDTO != null) {
				credential = CredentialImpl.newInstance(credencialDTO, accessToken);
			}
		} catch (BusinessException e) {
			logger.error("Acesso negado.", e);
		}
		return credential;
	}

}
