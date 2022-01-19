/*
 * UsuarioController.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.com.fabricadechocolate.application.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import br.com.fabricadechocolate.api.util.Validation;
import br.com.fabricadechocolate.application.dto.FiltroUsuarioDTO;
import br.com.fabricadechocolate.application.dto.UsuarioDTO;
import br.com.fabricadechocolate.application.enums.StatusAtivoInativo;
import br.com.fabricadechocolate.application.mapper.UsuarioMapper;
import br.com.fabricadechocolate.application.model.Usuario;
import br.com.fabricadechocolate.application.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabricadechocolate.comum.exception.MessageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Classe de controle referente a entidade {@link Usuario}.
 * 
 * @author UEG
 */
@Api(tags = "Usuario API")
@RestController
@RequestMapping("${app.api.base}/usuarios")
public class UsuarioController extends AbstractController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioMapper usuarioMapper;

	/**
	 * Salva uma instância de {@link Usuario} na base de dados.
	 * 
	 * @param usuarioDTO
	 * @return
	 */
//	@PreAuthorize("hasRole('ROLE_USUARIO_INCLUIR')")
	@ApiOperation(value = "Inclui um novo Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> incluir(@ApiParam(value = "Informações do Usuário", required = true) @Valid @RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
		usuario = usuarioService.salvar(usuario);
		usuarioDTO = usuarioMapper.toDTO(usuario);
		return ResponseEntity.ok(usuarioDTO);
	}

    /**
	 * Altera a instância de {@link Usuario} na base de dados.
	 * 
	 * @param id
	 * @param usuarioDTO
	 * @return
	 */
//    @PreAuthorize("hasRole('ROLE_USUARIO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de um Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações do Usuário", required = true) @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Validation.max("id", id, 99999999L);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setId(id.longValue());
        usuarioService.salvar(usuario);
		return ResponseEntity.ok(usuarioDTO);
    }

	/**
	 * Retorna a instância de {@link UsuarioDTO} conforme o 'id'
	 * informado.
	 *
	 * @param id
	 * @return
	 */
//	@PreAuthorize("hasRole('ROLE_USUARIO_VISUALIZAR')")
	@ApiOperation(value = "Recupera o usuario pela id.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@GetMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUsuarioById(@ApiParam(value = "Id do Usuario") @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		Usuario usuario = usuarioService.getByIdFetch(id.longValue());
		UsuarioDTO usuarioTO = new UsuarioDTO();

		if(usuario != null)
			usuarioTO = usuarioMapper.toDTO(usuario);

		return ResponseEntity.ok(usuarioTO);
	}

	/**
	 * Retorna a lista de {@link UsuarioDTO} de acordo com os filtros
	 * informados.
	 * 
	 * @param filtroDTO
	 * @return
	 */
//	@PreAuthorize("hasRole('ROLE_USUARIO_PESQUISAR')")
	@ApiOperation(value = "Recupera os usuarios pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@GetMapping(path = "/filtro-ativo", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUsuariosAtivosByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") FiltroUsuarioDTO filtroDTO) {
		String idStatusUsuario = StatusAtivoInativo.ATIVO.getId();
		filtroDTO.setIdStatus(idStatusUsuario);
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		List<Usuario> usuarios = usuarioService.getUsuariosByFiltro(filtroDTO);
		if(usuarios != null){
			for (Usuario usuario: usuarios) {
				usuariosDTO.add(usuarioMapper.toDTO(usuario));
			}
		}

		return ResponseEntity.ok(usuariosDTO);
	}

	/**
	 * Retorna a lista de {@link UsuarioDTO} de acordo com os filtros
	 * informados.
	 * 
	 * @param filtroDTO
	 * @return
	 */
//	@PreAuthorize("hasRole('ROLE_USUARIO_PESQUISAR')")
	@ApiOperation(value = "Recupera os usuarios pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUsuariosByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") final FiltroUsuarioDTO filtroDTO) {
		List<Usuario> usuarios = usuarioService.getUsuariosByFiltro(filtroDTO);
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		for (Usuario usuario: usuarios) {
			usuariosDTO.add (usuarioMapper.toDTO(usuario));
		}

		return ResponseEntity.ok(usuariosDTO);
	}

	/**
	 * Inativa o {@link Usuario}.
	 * 
	 * @param id
	 * @return
	 */
//	@PreAuthorize("hasRole('ROLE_USUARIO_ATIVAR_INATIVAR')")
	@ApiOperation(value = "Inativa o usuario.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class) 
	})
	@PutMapping(path = "/{id:[\\d]+}/inativo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inativar(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		usuarioService.inativar(id.longValue());
		return ResponseEntity.ok().build();
	}

	/**
	 * Ativa o {@link Usuario}.
	 *
	 * @param id
	 * @return
	 */
//	@PreAuthorize("hasRole('ROLE_USUARIO_ATIVAR_INATIVAR')")
	@ApiOperation(value = "Ativa o usuário.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
	})
	@PutMapping(path = "/{id:[\\d]+}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ativar(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		usuarioService.ativar(id.longValue());
		return ResponseEntity.ok().build();
	}
}
