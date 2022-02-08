package br.com.fabricadechocolate.application.mapper;
import br.com.fabricadechocolate.application.dto.GastoDTO;
import br.com.fabricadechocolate.application.model.Gasto;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Gasto}.
 *
 * @author Karen
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface GastoMapper {
    /**
     * Converte a entidade {@link Gasto} em DTO {@link GastoDTO}
     *
     * @param modulo
     * @return
     */

    public GastoDTO toDTO(Gasto modulo);

    /**
     * Converte o DTO {@link GastoDTO} para entidade {@link Gasto}
     *
     * @param gastoDTO
     * @return
     */

    public Gasto toEntity(GastoDTO gastoDTO);
}
