package br.com.fabricadechocolate.application.service;

import br.com.fabricadechocolate.application.configuration.Constante;
import br.com.fabricadechocolate.comum.exception.BusinessException;
import br.com.fabricadechocolate.comum.util.CollectionUtil;
import br.com.fabricadechocolate.comum.util.Util;
import br.com.fabricadechocolate.application.dto.FiltroSaborDTO;
import br.com.fabricadechocolate.application.exception.SistemaMessageCode;
import br.com.fabricadechocolate.application.model.Sabor;
import br.com.fabricadechocolate.application.repository.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SaborService {

    @Autowired
    private SaborRepository saborRepository;


    /**
     * Retorna uma lista de {@link Sabor} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Sabor> getSaborByFiltro(final FiltroSaborDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Sabor> sabor = saborRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(sabor)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return sabor;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo menos 1 caracter.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroSaborDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getSabor())) {
            vazio = Boolean.FALSE;

            if (filtroDTO.getSabor().trim().length() < Integer.parseInt(Constante.TAMANHO_MINIMO_PESQUISA)) {
                throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_INSUFICIENTE_FILTRO_SABOR);
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
     * Retorno um a {@link Sabor} pelo Id informado.
     * @param id - id to sabor
     * @return
     */
    public Sabor getById(Long id){
        Optional<Sabor> saborOptional = saborRepository.findById(id);

        if(!saborOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return saborOptional.get();
    }

    /**
     * Retorna uma lista de {@link Sabor} cadatrados .
     *
     * @return
     */
    public List<Sabor> getTodos() {
        List<Sabor> sabores = saborRepository.findAll();

        if (CollectionUtil.isEmpty(sabores)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return sabores;
    }

    /**
     * Salva a instânica de {@link Sabor} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param sabor
     * @return
     */
    public Sabor salvar(Sabor sabor) {

        if(sabor.getId() == null && sabor.getAtivo() == null){
            sabor.setAtivo("S");
        }

        validarCamposObrigatorios(sabor);
        validarDuplicidade(sabor);


        Sabor saborSaved = saborRepository.save(sabor);
        return saborSaved;
    }


    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param sabor
     */
    private void validarCamposObrigatorios(final Sabor sabor) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(sabor.getSabor())) {
            vazio = Boolean.TRUE;
        }

        if (Util.isEmpty(sabor.getAtivo())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o SAbor a ser salvo já existe na base de dados.
     *
     * @param sabor
     */
    private void validarDuplicidade(final Sabor sabor) {
        Long count = saborRepository.countByNomeAndNotId(sabor.getSabor(), sabor.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_DUPLICADO);
        }
    }

}
