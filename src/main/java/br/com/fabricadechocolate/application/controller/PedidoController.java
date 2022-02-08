package br.com.fabricadechocolate.application.controller;

import br.com.fabricadechocolate.api.util.Validation;
import br.com.fabricadechocolate.application.dto.FiltroPedidoDTO;
import br.com.fabricadechocolate.application.dto.PedidoDTO;
import br.com.fabricadechocolate.application.mapper.PedidoMapper;
import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.service.PedidoService;
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

@Api(tags = "Pedido API")
@RestController
@RequestMapping(path = "${app.api.base}/pedido")
public class PedidoController extends AbstractController {

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoService pedidoService;

    //@PreAuthorize("hasRole('ROLE_AMIGO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão de pedido.",
            notes = "Incluir Pedido.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Pedido", required = true) @Valid @RequestBody PedidoDTO pedidoDTO) {
            Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
            return ResponseEntity.ok(pedidoMapper.toDTO(pedidoService.salvar(pedido)));
    }

    /**
     * Altera a instância de {@link PedidoDTO} na base de dados.
     *
     * @param id
     * @param pedidoDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Pedido.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Pedido", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Pedido", required = true) @Valid @RequestBody PedidoDTO pedidoDTO) {
        Validation.max("id", id, 99999999L);
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido.setId(id.longValue());
        Pedido pedidoSaved = pedidoService.salvar(pedido);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedidoSaved));
    }

    /**
     * Retorna a instância de {@link PedidoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Pedido pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Pedido", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Pedido pedido = pedidoService.getById(id.longValue());
        PedidoDTO pedidoDTO = pedidoMapper.toDTO(pedido);

        return ResponseEntity.ok(pedidoDTO);
    }

    /**
     * Retorna a buscar de {@link Pedido} por {@link FiltroPedidoDTO}
     *
     * @param filtroPedidoDTO
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de Pedido.",
            notes = "Recupera as informações de Pedido conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroPedidoDTO filtroPedidoDTO) {
        List<PedidoDTO> pedidoDTOS = new ArrayList<>();
        List<Pedido> pedidos = pedidoService.getPedidosByFiltro(filtroPedidoDTO);
        if(pedidos.size() > 0){
            for (Pedido g:
             pedidos) {
                PedidoDTO pedidoDTO = pedidoMapper.toDTO(g);
                pedidoDTOS.add(pedidoDTO);
            }
        }

        return ResponseEntity.ok(pedidoDTOS);
    }


    /**
     * Retorna uma lista de {@link PedidoDTO} cadastrados.
     *
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna uma lista de Pedido cadastrados e ativos.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getPedidos() {
        List<Pedido> pedidos = pedidoService.getTodos();
        List<PedidoDTO> pedidoDTOS = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = pedidoMapper.toDTO(pedido);
            pedidoDTOS.add(pedidoDTO);
        }
        return ResponseEntity.ok(pedidoDTOS);
    }

    /**
     * Remover o {@link Pedido} pelo 'id' informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_REMOVER')")
    @ApiOperation(value = "Remove um Amigo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Pedido", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Pedido pedido = pedidoService.remover(id.longValue());
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
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
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/aceitar-pedido-pago", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> aceitarPedidoPago(@ApiParam(value = "Id do Pedido", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Pedido pedido = pedidoService.getById(id.longValue());
        pedido.setStatus("aceitopg");
        pedidoService.salvar(pedido);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    @PutMapping(path = "/{id:[\\d]+}/aceitar-pedido-nao-pago", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> aceitarPedidoNaoPago(@ApiParam(value = "Id do Pedido", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Pedido pedido = pedidoService.getById(id.longValue());
        pedido.setStatus("aceitonpg");
        pedidoService.salvar(pedido);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    /**
     * Desativar {@link Pedido} pelo 'id' informado.
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Desativar Pedido pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/cancelar-pedido", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> cancelarPedido(@ApiParam(value = "Id do Pedido", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Pedido pedido = pedidoService.getById(id.longValue());
        pedido.setStatus("cancelado");
        pedidoService.salvar(pedido);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    //Adicionei para colocar os pedidos em produção.
    /**
     * Adiciona o {@link Pedido} em produção pelo id informado.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Adiciona o Pedido em produção pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/pedido-em-producao", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> emProducao(@ApiParam(value = "Id do Pedido", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Pedido pedido = pedidoService.getById(id.longValue());
        pedido.setStatusProducao(true);
        pedidoService.salvar(pedido);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    //Adicionei para colocar os pedidos em produção.
    /**
     * Adiciona o {@link Pedido} em produção pelo id informado.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Adiciona o Pedido em produção pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/pedido-nao-producao", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> naoProducao(@ApiParam(value = "Id do Pedido", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Pedido pedido = pedidoService.getById(id.longValue());
        pedido.setStatusProducao(false);
        pedidoService.salvar(pedido);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    /**
     * Retorna uma lista de {@link PedidoDTO} cadastrados.
     *
     * @return
     */
//    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna uma lista de Pedido cadastrados e ativos.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/producao",produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getPedidoAceito() {
        //Aproveitar o filtro existente (status="Aceito")
        List<Pedido> pedidos = pedidoService.getPedidoAceito(); //PedidoService
        List<PedidoDTO> pedidoDTOS = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = pedidoMapper.toDTO(pedido);
            pedidoDTOS.add(pedidoDTO);
        }
        return ResponseEntity.ok(pedidoDTOS);
    }
}
