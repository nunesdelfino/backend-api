package br.ueg.modelo.application.mapper;


import br.ueg.modelo.application.dto.PedidoDTO;
import br.ueg.modelo.application.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link Pedido}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface PedidoMapper {
    /**
     * Converte a entidade {@link Pedido} em DTO {@link PedidoDTO}
     *
     * @param pedido
     * @return
     */
    @Mapping(source = "tamanho.id", target = "idTamanho")
    @Mapping(source = "tamanho.tamanho", target = "nomeTamanho")
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
    public PedidoDTO toDTO(Pedido pedido);

    /**
     * Converte o DTO {@link PedidoDTO} para entidade {@link Pedido}
     *
     * @param pedidoDTO
     * @return
     */
    @Mapping(source = "pedidoDTO.idTamanho", target = "tamanho.id")
    @Mapping(source = "pedidoDTO.idSaborUm", target = "saborUm.id")
    @Mapping(source = "pedidoDTO.idSaborDois", target = "saborDois.id")
    @Mapping(source = "pedidoDTO.idSaborTres", target = "saborTres.id")
    @Mapping(source = "pedidoDTO.idSaborQuatro", target = "saborQuatro.id")
    @Mapping(source = "pedidoDTO.idSaborCinco", target = "saborCinco.id")
    public Pedido toEntity(PedidoDTO pedidoDTO);
}
