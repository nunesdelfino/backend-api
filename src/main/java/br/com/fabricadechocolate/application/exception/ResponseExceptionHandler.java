/*
 * ResponseExceptionHandler.java
 * Copyright (c) UEG.
 */
package br.com.fabricadechocolate.application.exception;

import br.com.fabricadechocolate.api.exception.ApiRestResponseExceptionHandler;
import br.com.fabricadechocolate.comum.exception.MessageCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.BadRequestException;

/**
 * Classe handler responsável por interceptar e tratar as exceções de forma
 * amigavel para o client.
 * 
 * @author Squadra Tecnologia
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ApiRestResponseExceptionHandler {

	/**
	 * @see ApiRestResponseExceptionHandler#getCodeInternalServerError()
	 */
	@Override
	protected MessageCode getCodeInternalServerError() {
		return SistemaMessageCode.ERRO_INESPERADO;
	}

	/**
	 * Handler responsável por tratar exeções do tipo {@link BadRequestException}.
	 * 
	 * @param e
	 */
	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<Object> badRequestExceptionHandler(final BadRequestException e) {
//		KeycloakResponseError response = KeycloakResponseError.newInstance(e);
//
//		if (response.getKeycloakError() != null) {
//			return handleBusinessException(new BusinessException(response.getKeycloakError().getCode()));
//		}
		return handleException(e);
	}

}
