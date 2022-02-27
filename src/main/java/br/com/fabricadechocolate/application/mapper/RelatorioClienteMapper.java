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
    @Mapping(source = "saborUm.id", target = "idSaborUm")
    @Mapping(source = "saborUm.sabor", target = "nomeSaborUm")
    @Mapping(source = "saborDois.id", target = "idSaborDois")
    @Mapping(source = "saborDois.sabor", target = "nomeSaborDois")
    @Mapping(source = "saborTres.id", target = "idSaborTres")
    @Mapping(source = "saborTres.sabor", target = "nomeSaborTres")
    @Mapping(source = "saborQuatro.id", target = "idSaborQuatro")
    @Mapping(source = "saborQuatro.sabor", target = "nomeSaborQuatro")
    @Mapping(source = "saborCinco.id", target = "idSaborCinco")
    @Mapping(source = "saborCinco.sabor", target = "nomeSaborCinco")
    public RelatorioClienteDTO toDTO(Pedido pedido);

    /**
     * Converte o DTO {@link PedidoDTO} para entidade {@link Pedido}
     *
     * @param pedidoDTO
     * @return
     */
    @Mapping(source = "pedidoDTO.idSaborUm", target = "saborUm.id")
    @Mapping(source = "pedidoDTO.idSaborDois", target = "saborDois.id")
    @Mapping(source = "pedidoDTO.idSaborTres", target = "saborTres.id")
    @Mapping(source = "pedidoDTO.idSaborQuatro", target = "saborQuatro.id")
    @Mapping(source = "pedidoDTO.idSaborCinco", target = "saborCinco.id")
    public Pedido toEntity(RelatorioClienteDTO pedidoDTO);
}
