package br.com.fabricadechocolate.application.mapper;


import br.com.fabricadechocolate.application.dto.TamanhoDTO;
import br.com.fabricadechocolate.application.model.Tamanho;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Tamanho}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface TamanhoMapper {
    /**
     * Converte a entidade {@link Tamanho} em DTO {@link TamanhoDTO}
     *
     * @param modulo
     * @return
     */

    public TamanhoDTO toDTO(Tamanho modulo);

    /**
     * Converte o DTO {@link TamanhoDTO} para entidade {@link Tamanho}
     *
     * @param tamanhoDTO
     * @return
     */

    public Tamanho toEntity(TamanhoDTO tamanhoDTO);
}
