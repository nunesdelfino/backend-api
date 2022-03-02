package br.com.fabricadechocolate.application.controller;

import br.com.fabricadechocolate.application.dto.FiltroRelatoriosDTO;
import br.com.fabricadechocolate.application.dto.PedidoDTO;
import br.com.fabricadechocolate.application.dto.RelatorioClienteDTO;
import br.com.fabricadechocolate.application.dto.RelatorioVendasDTO;
import br.com.fabricadechocolate.application.mapper.RelatorioClienteMapper;
import br.com.fabricadechocolate.application.mapper.RelatorioVendasMapper;
import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.service.RelatoriosService;
import br.com.fabricadechocolate.comum.exception.MessageResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "${app.api.base}/relatorios")
public class RelatoriosController extends AbstractController {

    @Autowired
    private RelatorioClienteMapper relatorioClienteMapper;

    @Autowired
    private RelatorioVendasMapper relatorioVendasMapper;

    @Autowired
    private RelatoriosService relatoriosService;

    @ApiOperation(value = "Pesquisa de Pedido.",
            notes = "Recupera as informações de Pedido conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/cliente", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getRelatorioCliente(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroRelatoriosDTO filtroPedidoDTO) {
        List<RelatorioClienteDTO> pedidoDTOS = new ArrayList<>();
        List<Pedido> pedidos = relatoriosService.getDadosRealtorios(filtroPedidoDTO);
        if(pedidos.size() > 0){
            for (Pedido g:
                    pedidos) {
                RelatorioClienteDTO pedidoDTO = relatorioClienteMapper.toDTO(g);
                pedidoDTOS.add(pedidoDTO);
            }
        }

        return ResponseEntity.ok(pedidoDTOS);
    }

    @ApiOperation(value = "Pesquisa de Pedido.",
            notes = "Recupera as informações de Pedido conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/vendas", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getRelatorioVendas(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroRelatoriosDTO filtroPedidoDTO) {
        List<RelatorioVendasDTO> vendasDTOS = new ArrayList<>();
        List<Pedido> pedidos = relatoriosService.getDadosRealtorios(filtroPedidoDTO);
        if(pedidos.size() > 0){
            for (Pedido g:
                    pedidos) {
                RelatorioVendasDTO pedidoDTO = relatorioVendasMapper.toDTO(g);
                vendasDTOS.add(pedidoDTO);
            }
        }

        return ResponseEntity.ok(vendasDTOS);
    }

}
