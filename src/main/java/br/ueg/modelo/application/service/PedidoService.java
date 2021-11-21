package br.ueg.modelo.application.service;

import br.ueg.modelo.application.dto.FiltroPedidoDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.exception.SistemaMessageCode;
import br.ueg.modelo.application.model.Pedido;
import br.ueg.modelo.application.model.Sabor;
import br.ueg.modelo.application.model.Tamanho;
import br.ueg.modelo.application.repository.PedidoRepository;
import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.comum.util.CollectionUtil;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private SaborService saborService;

    @Autowired
    private TamanhoService tamanhoService;

    /**
     * Retorna uma lista de {@link Pedido} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Pedido> getPedidosByFiltro(final FiltroPedidoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Pedido> pedidos = pedidoRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(pedidos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return pedidos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Pedido
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroPedidoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getIdTamanho()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getDataEntrega()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getAtivo()!=null) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Pedido} cadatrados .
     *
     * @return
     */
    public List<Pedido> getTodos() {
        List<Pedido> pedidos = pedidoRepository.getTodos() ;

        if (CollectionUtil.isEmpty(pedidos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return pedidos;
    }

    /**
     * Retorno um a {@link Pedido} pelo Id informado.
     * @param id - id to Pedido
     * @return
     */
    public Pedido getById(Long id){
        Optional<Pedido> pedidoOptional = pedidoRepository.findByIdFetch(id);

        if(!pedidoOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return pedidoOptional.get();
    }

    /**
     * Salva a instânica de {@link Pedido} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param pedido
     * @return
     */
    public Pedido salvar(Pedido pedido) {

        if(pedido.getId() == null && pedido.getAtivo() == null){
            pedido.setAtivo("pendente");
            pedido.setEntregar(StatusSimNao.SIM);
        } else {

            buscaObjetosSabores(pedido);
            buscaObjetoTamanho(pedido);

        }

        prepararParaSalvar(pedido);
        validarCamposObrigatorios(pedido);


        Pedido pedidoSaved = pedidoRepository.save(pedido);
        return pedidoSaved;
    }

    private void buscaObjetoTamanho(Pedido pedido){
        Tamanho t;

        if(pedido.getTamanho() != null && pedido.getTamanho().getId() != null){
            t = tamanhoService.getById(pedido.getTamanho().getId());
            pedido.setTamanho(t);
        } else {
            pedido.setTamanho(null);
        }
    }

    private void buscaObjetosSabores(Pedido pedido) {
        Sabor s;

        if(pedido.getSaborUm() != null && pedido.getSaborUm().getId() != null){
            s = saborService.getById(pedido.getSaborUm().getId());
            pedido.setSaborUm(s);
        } else {
            pedido.setSaborUm(null);
        }

        if(pedido.getSaborDois() != null && pedido.getSaborDois().getId() != null){
            s = saborService.getById(pedido.getSaborDois().getId());
            pedido.setSaborDois(s);
        } else {
            pedido.setSaborDois(null);
        }

        if(pedido.getSaborTres() != null && pedido.getSaborTres().getId() != null){
            s = saborService.getById(pedido.getSaborTres().getId());
            pedido.setSaborTres(s);
        } else {
            pedido.setSaborTres(null);
        }

        if(pedido.getSaborQuatro() != null && pedido.getSaborQuatro().getId() != null){
            s = saborService.getById(pedido.getSaborQuatro().getId());
            pedido.setSaborQuatro(s);
        } else {
            pedido.setSaborQuatro(null);
        }

        if(pedido.getSaborCinco() != null && pedido.getSaborCinco().getId() != null){
            s = saborService.getById(pedido.getSaborCinco().getId());
            pedido.setSaborCinco(s);
        } else {
            pedido.setSaborCinco(null);
        }
    }

    private void prepararParaSalvar(Pedido pedido) {

        if (pedido.getSaborDois() != null && pedido.getSaborDois().getId() == null) {
            pedido.setSaborDois(null);
        }

        if (pedido.getSaborTres() != null && pedido.getSaborTres().getId() == null) {
            pedido.setSaborTres(null);
        }

        if (pedido.getSaborQuatro() != null && pedido.getSaborQuatro().getId() == null) {
            pedido.setSaborQuatro(null);
        }

        if (pedido.getSaborCinco() != null && pedido.getSaborCinco().getId() == null) {
            pedido.setSaborCinco(null);
        }

    }

    public Pedido remover(Long id){
        Pedido pedido = this.getById(id);

        pedidoRepository.delete(pedido);

        return pedido;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param pedido
     */
    private void validarCamposObrigatorios(final Pedido pedido) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(pedido.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (Util.isEmpty(pedido.getNumero())) {
            vazio = Boolean.TRUE;
        }

        if (pedido.getTamanho()==null || pedido.getTamanho().getId()== null) {
            vazio = Boolean.TRUE;
        }

        if (pedido.getSaborUm()==null || pedido.getSaborUm().getId()== null) {
            vazio = Boolean.TRUE;
        }

        if (pedido.getEntregar()==null) {
            vazio = Boolean.TRUE;
        }

        if (pedido.getDataEntrega()==null) {
            vazio = Boolean.TRUE;
        }

        if (pedido.getAtivo()==null) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

}
