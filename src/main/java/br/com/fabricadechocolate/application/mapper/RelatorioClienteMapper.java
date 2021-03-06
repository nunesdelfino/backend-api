package br.com.fabricadechocolate.application.mapper;


import br.com.fabricadechocolate.application.dto.PedidoDTO;
import br.com.fabricadechocolate.application.dto.RelatorioClienteDTO;
import br.com.fabricadechocolate.application.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link Pedido}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface RelatorioClienteMapper {
    /**
     * Converte a entidade {@link Pedido} em DTO {@link PedidoDTO}
     *
     * @param pedido
     * @return
     */
    @Mapping(source = "tamanho.tamanho", target = "nomeTamanho")
    public RelatorioClienteDTO toDTO(Pedido pedido);

    /**
     * Converte o DTO {@link PedidoDTO} para entidade {@link Pedido}
     *
     * @param pedidoDTO
     * @return
     */
    public Pedido toEntity(RelatorioClienteDTO pedidoDTO);
}
