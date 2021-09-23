package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.util.Validation;
import br.ueg.modelo.application.dto.*;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.mapper.SaborMapper;
import br.ueg.modelo.application.model.Pedido;
import br.ueg.modelo.application.model.Sabor;
import br.ueg.modelo.application.service.SaborService;
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

@Api(tags = "Sabor API")
@RestController
@RequestMapping(path = "${app.api.base}/sabor")
public class SaborController extends AbstractController {

    @Autowired
    private SaborMapper saborMapper;


    @Autowired
    private SaborService saborService;


//    @PreAuthorize("hasRole('ROLE_TAMANHO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de sabor.",
            notes = "Incluir/Alterar sabor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SaborDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Sabor", required = true) @Valid @RequestBody SaborDTO saborDTO) {
            Sabor sabor = saborMapper.toEntity(saborDTO);
            return ResponseEntity.ok(saborMapper.toDTO(saborService.salvar(sabor)));
    }

    /**
     * Retorna a instância de {@link SaborDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
//    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Sabor pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SaborDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Sabor sabor = saborService.getById(id.longValue());
        SaborDTO saborDTO = saborMapper.toDTO(sabor);

        return ResponseEntity.ok(saborDTO);
    }

    /**
     * Altera a instância de {@link SaborDTO} na base de dados.
     *
     * @param id
     * @param saborDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_GRUPO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Sabor.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SaborDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Sistema", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de Sabor", required = true) @Valid @RequestBody SaborDTO saborDTO) {
        Validation.max("id", id, 99999999L);
        Sabor sabor = saborMapper.toEntity(saborDTO);
        sabor.setId(id.longValue());
        Sabor saborSaved = saborService.salvar(sabor);
        return ResponseEntity.ok(saborMapper.toDTO(saborSaved));
    }

    /**
     * Retorna a buscar de {@link Sabor} por {@link FiltroSaborDTO}
     *
     * @param filtroSaborDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_GRUPO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de sabores.",
            notes = "Recupera as informações de Sabor conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SaborDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroSaborDTO filtroSaborDTO) {
        List<SaborDTO> saboresDTO = new ArrayList<>();
        List<Sabor> sabores = saborService.getSaborByFiltro(filtroSaborDTO);
        if(sabores.size() > 0){
            for (Sabor g:
             sabores) {
                SaborDTO saborDTO = saborMapper.toDTO(g);
                saboresDTO.add(saborDTO);
            }
        }

        return ResponseEntity.ok(saboresDTO);
    }


    /**
     * Retorna uma lista de {@link SaborDTO} cadastrados.
     *
     * @return
     */
//    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Sabores cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SaborDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getGrupos() {
        List<Sabor> sabores = saborService.getTodos();
        List<SaborDTO> saborDTO = new ArrayList<>();
        for (Sabor sabor : sabores) {
            SaborDTO saboresDTO = saborMapper.toDTO(sabor);
            saborDTO.add(saboresDTO);
        }
        return ResponseEntity.ok(saborDTO);
    }


    /**
     * Ativar {@link Pedido} pelo 'id' informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Ativar Pedido pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SaborDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/ativar-sabor", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> ativarSabor(@ApiParam(value = "Id do sabor", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Sabor sabor = saborService.getById(id.longValue());
        sabor.setAtivo(StatusSimNao.SIM);
        saborService.salvar(sabor);
        return ResponseEntity.ok(saborMapper.toDTO(sabor));
    }


    /**
     * Desativar {@link Pedido} pelo 'id' informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Desativar Sabor pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SaborDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/desativar-sabor", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> desativarSabor(@ApiParam(value = "Id do Sabor", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Sabor sabor = saborService.getById(id.longValue());
        sabor.setAtivo(StatusSimNao.NAO);
        saborService.salvar(sabor);
        return ResponseEntity.ok(saborMapper.toDTO(sabor));
    }


}
