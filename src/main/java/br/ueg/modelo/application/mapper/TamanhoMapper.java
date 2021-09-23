package br.ueg.modelo.application.mapper;


import br.ueg.modelo.application.dto.ModuloDTO;
import br.ueg.modelo.application.dto.TamanhoDTO;
import br.ueg.modelo.application.model.Modulo;
import br.ueg.modelo.application.model.Tamanho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
