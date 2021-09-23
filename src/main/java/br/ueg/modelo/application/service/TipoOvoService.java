package br.ueg.modelo.application.service;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.dto.FiltroTipoOvoDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.exception.SistemaMessageCode;
import br.ueg.modelo.application.model.TipoOvo;
import br.ueg.modelo.application.repository.TipoOvoRepository;
import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.comum.util.CollectionUtil;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TipoOvoService {

    @Autowired
    private TipoOvoRepository tipoOvoRepository;


    /**
     * Retorna uma lista de {@link TipoOvo} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<TipoOvo> getTipoOvoByFiltro(final FiltroTipoOvoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<TipoOvo> tipoOvos = tipoOvoRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(tipoOvos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return tipoOvos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo menos 1 caracter.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroTipoOvoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getTipo())) {
            vazio = Boolean.FALSE;

            if (filtroDTO.getTipo().trim().length() < Integer.parseInt(Constante.TAMANHO_MINIMO_PESQUISA)) {
                throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_INSUFICIENTE_FILTRO_TAMANHO);
            }
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorno um a {@link TipoOvo} pelo Id informado.
     * @param id - id to tamanho
     * @return
     */
    public TipoOvo getById(Long id){
        Optional<TipoOvo> saborOptional = tipoOvoRepository.findById(id);

        if(!saborOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return saborOptional.get();
    }

    /**
     * Retorna uma lista de {@link TipoOvo} cadatrados .
     *
     * @return
     */
    public List<TipoOvo> getTodos() {
        List<TipoOvo> tipoOvos = tipoOvoRepository.findAll();

        if (CollectionUtil.isEmpty(tipoOvos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return tipoOvos;
    }

    /**
     * Salva a instânica de {@link TipoOvo} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param tipoOvo
     * @return
     */
    public TipoOvo salvar(TipoOvo tipoOvo) {

        if(tipoOvo.getId() == null && tipoOvo.getAtivo() == null) {
            tipoOvo.setAtivo(StatusSimNao.SIM);
        }

        validarCamposObrigatorios(tipoOvo);
        validarDuplicidade(tipoOvo);


        TipoOvo tipoOvoSaved = tipoOvoRepository.save(tipoOvo);
        return tipoOvoSaved;
    }


    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param tipoOvo
     */
    private void validarCamposObrigatorios(final TipoOvo tipoOvo) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(tipoOvo.getTipo())) {
            vazio = Boolean.TRUE;
        }

        if (tipoOvo.getAtivo() == null) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Tamanho a ser salvo já existe na base de dados.
     *
     * @param tipoOvo
     */
    private void validarDuplicidade(final TipoOvo tipoOvo) {
        Long count = tipoOvoRepository.countByNomeAndNotId(tipoOvo.getTipo(), tipoOvo.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_DUPLICADO);
        }
    }

}
