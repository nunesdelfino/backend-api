package br.com.fabricadechocolate.application.mapper;


import br.com.fabricadechocolate.application.dto.SaborDTO;
import br.com.fabricadechocolate.application.model.Sabor;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Sabor}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface SaborMapper {
    /**
     * Converte a entidade {@link Sabor} em DTO {@link SaborDTO}
     *
     * @param modulo
     * @return
     */

    public SaborDTO toDTO(Sabor modulo);

    /**
     * Converte o DTO {@link SaborDTO} para entidade {@link Sabor}
     *
     * @param saborDTO
     * @return
     */

    public Sabor toEntity(SaborDTO saborDTO);
}
