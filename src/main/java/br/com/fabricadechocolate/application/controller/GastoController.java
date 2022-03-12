package br.com.fabricadechocolate.application.controller;

import br.com.fabricadechocolate.api.util.Validation;
import br.com.fabricadechocolate.application.dto.PedidoDTO;
import br.com.fabricadechocolate.application.dto.filtro.FiltroGastoDTO;
import br.com.fabricadechocolate.application.dto.GastoDTO;
import br.com.fabricadechocolate.application.dto.SaborDTO;
import br.com.fabricadechocolate.application.mapper.GastoMapper;
import br.com.fabricadechocolate.application.model.Gasto;
import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.service.GastoService;
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

@Api(tags = "Gasto API")
@RestController
@RequestMapping(path = "${app.api.base}/gasto")
public class GastoController extends AbstractController {

    @Autowired
    private GastoMapper gastoMapper;


    @Autowired
    private GastoService gastoService;


//    @PreAuthorize("hasRole('ROLE_TAMANHO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de gasto.",
            notes = "Incluir/Alterar gasto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GastoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Gasto", required = true) @Valid @RequestBody GastoDTO gastoDTO) {
            Gasto gasto = gastoMapper.toEntity(gastoDTO);
            return ResponseEntity.ok(gastoMapper.toDTO(gastoService.salvar(gasto)));
    }

    /**
     * Retorna a instância de {@link SaborDTO} pelo id informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Sabor pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GastoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Gasto gasto = gastoService.getById(id.longValue());
        GastoDTO gastoDTO = gastoMapper.toDTO(gasto);

        return ResponseEntity.ok(gastoDTO);
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
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Sistema", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de Sabor", required = true) @Valid @RequestBody GastoDTO gastoDTO) {
        Validation.max("id", id, 99999999L);
        Gasto gasto = gastoMapper.toEntity(gastoDTO);
        gasto.setId(id.longValue());
        Gasto gastoSaved = gastoService.salvar(gasto);
        return ResponseEntity.ok(gastoMapper.toDTO(gastoSaved));
    }

    /**
     * Retorna a buscar de {@link Gasto} por {@link FiltroGastoDTO}
     *
     * @param filtroGastoDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_GRUPO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de gastos.",
            notes = "Recupera as informações de Gasto conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GastoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroGastoDTO filtroGastoDTO) {
        List<GastoDTO> gastosDTO = new ArrayList<>();
        List<Gasto> gastos = gastoService.getGastoByFiltro(filtroGastoDTO);
        if(gastos.size() > 0){
            for (Gasto g:
             gastos) {
                GastoDTO gastoDTO = gastoMapper.toDTO(g);
                gastosDTO.add(gastoDTO);
            }
        }

        return ResponseEntity.ok(gastosDTO);
    }

    /**
     * Retorna uma lista de {@link SaborDTO} cadastrados.
     *
     * @return
     */
//    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Gastos cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GastoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getGastos() {
        List<Gasto> gastos = gastoService.getTodos();
        List<GastoDTO> gastoDTO = new ArrayList<>();
        for (Gasto gasto : gastos) {
            GastoDTO gastosDTO = gastoMapper.toDTO(gasto);
            gastoDTO.add(gastosDTO);
        }
        return ResponseEntity.ok(gastoDTO);
    }

    @ApiOperation(value = "Remove um Amigo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Custo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Gasto gasto = gastoService.remover(id.longValue());
        return ResponseEntity.ok(gastoMapper.toDTO(gasto));
    }
}
