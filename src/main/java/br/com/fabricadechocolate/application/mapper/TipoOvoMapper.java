package br.com.fabricadechocolate.application.mapper;


import br.com.fabricadechocolate.application.dto.TipoOvoDTO;
import br.com.fabricadechocolate.application.model.TipoOvo;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link TipoOvo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface TipoOvoMapper {
    /**
     * Converte a entidade {@link TipoOvo} em DTO {@link TipoOvoDTO}
     *
     * @param modulo
     * @return
     */

    public TipoOvoDTO toDTO(TipoOvo modulo);

    /**
     * Converte o DTO {@link TipoOvoDTO} para entidade {@link TipoOvo}
     *
     * @param tipoOvoDTO
     * @return
     */

    public TipoOvo toEntity(TipoOvoDTO tipoOvoDTO);
}
