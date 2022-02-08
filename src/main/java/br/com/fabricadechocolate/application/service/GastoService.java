package br.com.fabricadechocolate.application.service;

import br.com.fabricadechocolate.application.configuration.Constante;
import br.com.fabricadechocolate.application.dto.FiltroGastoDTO;
import br.com.fabricadechocolate.application.exception.SistemaMessageCode;
import br.com.fabricadechocolate.application.model.Gasto;
import br.com.fabricadechocolate.application.repository.GastoRepository;
import br.com.fabricadechocolate.comum.exception.BusinessException;
import br.com.fabricadechocolate.comum.util.CollectionUtil;
import br.com.fabricadechocolate.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;


    /**
     * Retorna uma lista de {@link Gasto} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Gasto> getGastoByFiltro(final FiltroGastoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Gasto> gastos = gastoRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(gastos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return gastos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo menos 1 caracter.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroGastoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNomeEstabelecimento())) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getData() != null) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorno um a {@link Gasto} pelo Id informado.
     * @param id - id to sabor
     * @return
     */
    public Gasto getById(Long id){
        Optional<Gasto> saborOptional = gastoRepository.findById(id);

        if(!saborOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return saborOptional.get();
    }

    /**
     * Retorna uma lista de {@link Gasto} cadatrados .
     *
     * @return
     */
    public List<Gasto> getTodos() {
        List<Gasto> gastos = gastoRepository.findAll();

        if (CollectionUtil.isEmpty(gastos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return gastos;
    }

    /**
     * Salva a instânica de {@link Gasto} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param gasto
     * @return
     */
    public Gasto salvar(Gasto gasto) {
        validarCamposObrigatorios(gasto);
        Gasto gastoSaved = gastoRepository.save(gasto);
        return gastoSaved;
    }


    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param gasto
     */
    private void validarCamposObrigatorios(final Gasto gasto) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(gasto.getNomeEstabelecimento())) {
            vazio = Boolean.TRUE;
        }

        if (gasto.getValor() == null) {
            vazio = Boolean.TRUE;
        }

        if (gasto.getData() == null) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Gasto a ser salvo já existe na base de dados.
     *
     * @param gasto
     */
}
