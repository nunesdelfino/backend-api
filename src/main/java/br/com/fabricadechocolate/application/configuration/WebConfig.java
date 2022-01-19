/*
 * WebConfig.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.configuration;

import br.com.fabricadechocolate.api.config.ApiWebConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Classe de configuração referente aos recursos Web MVC da aplicação.
 * 
 * @author UEG
 */
@Configuration
public class WebConfig extends ApiWebConfig {

	/**
	 * Retorna a instância {@link MethodValidationPostProcessor}.
	 * 
	 * @return
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

}
