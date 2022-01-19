package br.com.fabricadechocolate.application.service;

import br.com.fabricadechocolate.application.configuration.Constante;
import br.com.fabricadechocolate.application.model.Tamanho;
import br.com.fabricadechocolate.comum.exception.BusinessException;
import br.com.fabricadechocolate.comum.util.CollectionUtil;
import br.com.fabricadechocolate.comum.util.Util;
import br.com.fabricadechocolate.application.dto.FiltroTamanhoDTO;
import br.com.fabricadechocolate.application.exception.SistemaMessageCode;
import br.com.fabricadechocolate.application.repository.TamanhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TamanhoService {

    @Autowired
    private TamanhoRepository tamanhoRepository;


    /**
     * Retorna uma lista de {@link Tamanho} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Tamanho> getTamanhoByFiltro(FiltroTamanhoDTO filtroDTO) {
        filtroDTO = validaPesquisa(filtroDTO);
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Tamanho> tamanhos = tamanhoRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(tamanhos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return tamanhos;
    }

    private FiltroTamanhoDTO validaPesquisa(FiltroTamanhoDTO filtroDTO) {
        if(Util.isEmpty(filtroDTO.getTamanho()) && !Util.isEmpty(filtroDTO.getAtivo())){
            if(filtroDTO.getAtivo().equalsIgnoreCase("T")){
                filtroDTO.setTamanho("%%%");
                filtroDTO.setAtivo(null);
            }
        }

        return filtroDTO;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo menos 1 caracter.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroTamanhoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getTamanho())) {
            vazio = Boolean.FALSE;

            if (filtroDTO.getTamanho().trim().length() < Integer.parseInt(Constante.TAMANHO_MINIMO_PESQUISA)) {
                throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_INSUFICIENTE_FILTRO_TAMANHO);
            }
        }

        if (!Util.isEmpty(filtroDTO.getAtivo())) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorno um a {@link Tamanho} pelo Id informado.
     * @param id - id to tamanho
     * @return
     */
    public Tamanho getById(Long id){
        Optional<Tamanho> saborOptional = tamanhoRepository.findById(id);

        if(!saborOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return saborOptional.get();
    }

    /**
     * Retorna uma lista de {@link Tamanho} cadatrados .
     *
     * @return
     */
    public List<Tamanho> getTodos() {
        List<Tamanho> tamanhos = tamanhoRepository.findAll();

        if (CollectionUtil.isEmpty(tamanhos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return tamanhos;
    }

    /**
     * Salva a instânica de {@link Tamanho} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param tamanho
     * @return
     */
    public Tamanho salvar(Tamanho tamanho) {

        if(tamanho.getId() == null && tamanho.getAtivo() == null){
            tamanho.setAtivo("S");
        }

        validarCamposObrigatorios(tamanho);
        validarDuplicidade(tamanho);


        Tamanho tamanhoSaved = tamanhoRepository.save(tamanho);
        return tamanhoSaved;
    }


    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param tamanho
     */
    private void validarCamposObrigatorios(final Tamanho tamanho) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(tamanho.getTamanho())) {
            vazio = Boolean.TRUE;
        }

        if (tamanho.getAtivo() == null) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Tamanho a ser salvo já existe na base de dados.
     *
     * @param tamanho
     */
    private void validarDuplicidade(final Tamanho tamanho) {
        Long count = tamanhoRepository.countByNomeAndNotId(tamanho.getTamanho(), tamanho.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_DUPLICADO);
        }
    }

}
