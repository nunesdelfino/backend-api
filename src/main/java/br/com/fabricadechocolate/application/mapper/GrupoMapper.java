package br.com.fabricadechocolate.application.mapper;


import br.com.fabricadechocolate.application.dto.outros.GrupoDTO;
import br.com.fabricadechocolate.application.model.Grupo;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { StatusAtivoInativoMapper.class, SimNaoMapper.class, FuncionalidadeMapper.class})
public interface GrupoMapper {
    /**
     * Converte a entidade {@link Grupo} em DTO {@link GrupoDTO}
     *
     * @param grupo
     * @return
     */

    public GrupoDTO toDTO(Grupo grupo);

    /**
     * Converte o DTO {@link GrupoDTO} para entidade {@link Grupo}
     *
     * @param grupoDTO
     * @return
     */

   // @Mapping(target = "status", expression = "java( StatusAtivoInativo.getById( grupoDTO.getIdStatus() ) )")
    public Grupo toEntity(GrupoDTO grupoDTO);
}
