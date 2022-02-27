package br.com.fabricadechocolate.application.controller;

import br.com.fabricadechocolate.application.dto.FiltroRelatorioClienteDTO;
import br.com.fabricadechocolate.application.dto.PedidoDTO;
import br.com.fabricadechocolate.application.dto.RelatorioClienteDTO;
import br.com.fabricadechocolate.application.mapper.RelatorioClienteMapper;
import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.service.RelatorioClienteService;
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
    private RelatorioClienteService relatorioClienteService;

    @ApiOperation(value = "Pesquisa de Pedido.",
            notes = "Recupera as informações de Pedido conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PedidoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/cliente", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroRelatorioClienteDTO filtroPedidoDTO) {
        List<RelatorioClienteDTO> pedidoDTOS = new ArrayList<>();
        List<Pedido> pedidos = relatorioClienteService.getPedidosByFiltro(filtroPedidoDTO);
        if(pedidos.size() > 0){
            for (Pedido g:
                    pedidos) {
                RelatorioClienteDTO pedidoDTO = relatorioClienteMapper.toDTO(g);
                pedidoDTOS.add(pedidoDTO);
            }
        }

        return ResponseEntity.ok(pedidoDTOS);
    }

}
