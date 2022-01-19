package br.com.fabricadechocolate.application.controller;

import br.com.fabricadechocolate.api.util.Validation;
import br.com.fabricadechocolate.application.service.TipoOvoService;
import br.com.fabricadechocolate.application.dto.FiltroTipoOvoDTO;
import br.com.fabricadechocolate.application.dto.TipoOvoDTO;
import br.com.fabricadechocolate.application.enums.StatusSimNao;
import br.com.fabricadechocolate.application.mapper.TipoOvoMapper;
import br.com.fabricadechocolate.application.model.TipoOvo;
import br.com.fabricadechocolate.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Tipo Ovo API")
@RestController
@RequestMapping(path = "${app.api.base}/tipoovo")
public class TipoOvoController extends AbstractController {

    @Autowired
    private TipoOvoMapper tipoOvoMapper;


    @Autowired
    private TipoOvoService tipoOvoService;


//    @PreAuthorize("hasRole('ROLE_TAMANHO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de tipo ovo.",
            notes = "Incluir/Alterar tamanho")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoOvoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Tamanho", required = true) @Valid @RequestBody TipoOvoDTO tipoOvoDTO) {
            TipoOvo tipoOvo = tipoOvoMapper.toEntity(tipoOvoDTO);
            return ResponseEntity.ok(tipoOvoMapper.toDTO(tipoOvoService.salvar(tipoOvo)));
    }

    /**
     * Altera a instância de {@link TipoOvoDTO} na base de dados.
     *
     * @param id
     * @param tipoOvoDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_GRUPO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Tamanho.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoOvoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Sistema", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de Tamanho", required = true) @Valid @RequestBody TipoOvoDTO tipoOvoDTO) {
        Validation.max("id", id, 99999999L);
        TipoOvo tipoOvo = tipoOvoMapper.toEntity(tipoOvoDTO);
        tipoOvo.setId(id.longValue());
        TipoOvo tipoOvoSaved = tipoOvoService.salvar(tipoOvo);
        return ResponseEntity.ok(tipoOvoMapper.toDTO(tipoOvoSaved));
    }

    /**
     * Retorna a buscar de {@link TipoOvo} por {@link FiltroTipoOvoDTO}
     *
     * @param filtroTipoOvoDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_GRUPO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de tamanhos.",
            notes = "Recupera as informações de Grupo conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoOvoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroTipoOvoDTO filtroTipoOvoDTO) {
        List<TipoOvoDTO> tipoOvoDTOS = new ArrayList<>();
        List<TipoOvo> tipoOvos = tipoOvoService.getTipoOvoByFiltro(filtroTipoOvoDTO);
        if(tipoOvos.size() > 0){
            for (TipoOvo g:
             tipoOvos) {
                TipoOvoDTO tipoOvoDTO = tipoOvoMapper.toDTO(g);
                tipoOvoDTOS.add(tipoOvoDTO);
            }
        }

        return ResponseEntity.ok(tipoOvoDTOS);
    }

    /**
     * Retorna a instância de {@link TipoOvoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
//    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Tamanho pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoOvoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Tipo Ovo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        TipoOvo tipoOvo = tipoOvoService.getById(id.longValue());
        TipoOvoDTO tamanhoDTO = tipoOvoMapper.toDTO(tipoOvo);

        return ResponseEntity.ok(tamanhoDTO);
    }


    /**
     * Retorna uma lista de {@link TipoOvoDTO} cadastrados.
     *
     * @return
     */
//    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Tamanhos cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoOvoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getGrupos() {
        List<TipoOvo> tipoOvos = tipoOvoService.getTodos();
        List<TipoOvoDTO> tipoOvoDTOS = new ArrayList<>();
        for (TipoOvo tipoOvo : tipoOvos) {
            TipoOvoDTO tipoOvoDTO = tipoOvoMapper.toDTO(tipoOvo);
            tipoOvoDTOS.add(tipoOvoDTO);
        }
        return ResponseEntity.ok(tipoOvoDTOS);
    }

    /**
     * Ativar {@link TipoOvo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Ativar Tamanho pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoOvoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/ativar-tamanho", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> ativarSabor(@ApiParam(value = "Id do sabor", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        TipoOvo tipoOvo = tipoOvoService.getById(id.longValue());
        tipoOvo.setAtivo(StatusSimNao.SIM);
        tipoOvoService.salvar(tipoOvo);
        return ResponseEntity.ok(tipoOvoMapper.toDTO(tipoOvo));
    }


    /**
     * Desativar {@link TipoOvo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Desativar Tamanho pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoOvoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/desativar-tamanho", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> desativarSabor(@ApiParam(value = "Id do Tamanho", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        TipoOvo tipoOvo = tipoOvoService.getById(id.longValue());
        tipoOvo.setAtivo(StatusSimNao.NAO);
        tipoOvoService.salvar(tipoOvo);
        return ResponseEntity.ok(tipoOvoMapper.toDTO(tipoOvo));
    }

}
