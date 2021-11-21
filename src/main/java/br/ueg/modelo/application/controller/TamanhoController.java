package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.util.Validation;
import br.ueg.modelo.application.dto.FiltroTamanhoDTO;
import br.ueg.modelo.application.dto.TamanhoDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.mapper.TamanhoMapper;
import br.ueg.modelo.application.model.Pedido;
import br.ueg.modelo.application.model.Tamanho;
import br.ueg.modelo.application.service.TamanhoService;
import br.ueg.modelo.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Tamanho API")
@RestController
@RequestMapping(path = "${app.api.base}/tamanho")
public class TamanhoController extends AbstractController {

    @Autowired
    private TamanhoMapper tamanhoMapper;


    @Autowired
    private TamanhoService tamanhoService;


//    @PreAuthorize("hasRole('ROLE_TAMANHO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de tamanho.",
            notes = "Incluir/Alterar tamanho")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TamanhoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Tamanho", required = true) @Valid @RequestBody TamanhoDTO tamanhoDTO) {
            Tamanho tamanho = tamanhoMapper.toEntity(tamanhoDTO);
            return ResponseEntity.ok(tamanhoMapper.toDTO(tamanhoService.salvar(tamanho)));
    }

    /**
     * Altera a instância de {@link TamanhoDTO} na base de dados.
     *
     * @param id
     * @param tamanhoDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_GRUPO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Tamanho.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TamanhoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Sistema", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de Tamanho", required = true) @Valid @RequestBody TamanhoDTO tamanhoDTO) {
        Validation.max("id", id, 99999999L);
        Tamanho tamanho = tamanhoMapper.toEntity(tamanhoDTO);
        tamanho.setId(id.longValue());
        Tamanho tamanhoSaved = tamanhoService.salvar(tamanho);
        return ResponseEntity.ok(tamanhoMapper.toDTO(tamanhoSaved));
    }

    /**
     * Retorna a buscar de {@link Tamanho} por {@link FiltroTamanhoDTO}
     *
     * @param filtroGrupoDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_GRUPO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de tamanhos.",
            notes = "Recupera as informações de Grupo conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TamanhoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroTamanhoDTO filtroGrupoDTO) {
        List<TamanhoDTO> tamanhosDTO = new ArrayList<>();
        List<Tamanho> tamanhos = tamanhoService.getTamanhoByFiltro(filtroGrupoDTO);
        if(tamanhos.size() > 0){
            for (Tamanho g:
             tamanhos) {
                TamanhoDTO tamanhoDTO = tamanhoMapper.toDTO(g);
                tamanhosDTO.add(tamanhoDTO);
            }
        }

        return ResponseEntity.ok(tamanhosDTO);
    }

    /**
     * Retorna a instância de {@link TamanhoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
//    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Tamanho pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TamanhoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Tamanho tamanho = tamanhoService.getById(id.longValue());
        TamanhoDTO tamanhoDTO = tamanhoMapper.toDTO(tamanho);

        return ResponseEntity.ok(tamanhoDTO);
    }


    /**
     * Retorna uma lista de {@link TamanhoDTO} cadastrados.
     *
     * @return
     */
//    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Tamanhos cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TamanhoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getGrupos() {
        List<Tamanho> tamanhos = tamanhoService.getTodos();
        List<TamanhoDTO> tamanhoDTO = new ArrayList<>();
        for (Tamanho tamanho : tamanhos) {
            TamanhoDTO grupoDTO = tamanhoMapper.toDTO(tamanho);
            tamanhoDTO.add(grupoDTO);
        }
        return ResponseEntity.ok(tamanhoDTO);
    }

    /**
     * Ativar {@link Pedido} pelo 'id' informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Ativar Tamanho pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TamanhoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/ativar-tamanho", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> ativarSabor(@ApiParam(value = "Id do sabor", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Tamanho tamanho = tamanhoService.getById(id.longValue());
        tamanho.setAtivo(StatusSimNao.SIM);
        tamanhoService.salvar(tamanho);
        return ResponseEntity.ok(tamanhoMapper.toDTO(tamanho));
    }


    /**
     * Desativar {@link Pedido} pelo 'id' informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Desativar Tamanho pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TamanhoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/desativar-tamanho", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> desativarSabor(@ApiParam(value = "Id do Tamanho", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Tamanho tamanho = tamanhoService.getById(id.longValue());
        tamanho.setAtivo(StatusSimNao.NAO);
        tamanhoService.salvar(tamanho);
        return ResponseEntity.ok(tamanhoMapper.toDTO(tamanho));
    }

}
