package br.com.fabricadechocolate.application.service;

import br.com.fabricadechocolate.application.dto.filtro.FiltroRelatoriosDTO;
import br.com.fabricadechocolate.application.exception.SistemaMessageCode;
import br.com.fabricadechocolate.application.model.Pedido;
import br.com.fabricadechocolate.application.repository.RelatoriosRepository;
import br.com.fabricadechocolate.comum.exception.BusinessException;
import br.com.fabricadechocolate.comum.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RelatoriosService {

    @Autowired
    private RelatoriosRepository relatoriosRepository;

    public List<Pedido> getDadosRealtorios(FiltroRelatoriosDTO filtroDTO) {
        validarCamposDataFiltro(filtroDTO);

        List<Pedido> pedidos = relatoriosRepository.findRealtoriosFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(pedidos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return pedidos;
    }

    private void validarCamposDataFiltro(final FiltroRelatoriosDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (filtroDTO.getDataInicio() != null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getDataFinal() != null) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

//    /**
//     * Retorna uma lista de {@link Pedido} conforme o filtro de pesquisa informado.
//     *
//     * @param filtroDTO
//     * @return
//     */
//    public List<Pedido> getPedidosByFiltro(FiltroRelatorioClienteDTO filtroDTO) {
//        validarCamposObrigatoriosFiltro(filtroDTO);
//
//        List<Pedido> pedidos = relatorioClienteCustomRepository.findAllByFiltro(filtroDTO);
//
//        if (CollectionUtil.isEmpty(pedidos)) {
//            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
//        }
//
//        return pedidos;
//    }
//
//    /**
//     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
//     * nome do Pedido
//     *
//     * @param filtroDTO
//     */
//    private void validarCamposObrigatoriosFiltro(final FiltroRelatorioClienteDTO filtroDTO) {
//        boolean vazio = Boolean.TRUE;
//
//        if (filtroDTO.getDataInicio() != null) {
//            vazio = Boolean.FALSE;
//        }
//
//        if (filtroDTO.getDataFinal() != null) {
//            vazio = Boolean.FALSE;
//        }
//
//        if (vazio) {
//            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
//        }
//    }

//    /**
//     * Retorna uma lista de {@link Pedido} cadatrados .
//     *
//     * @return
//     */
//    public List<Pedido> getTodos() {
//        List<Pedido> pedidos = pedidoRepository.getTodos() ;
//
//        if (CollectionUtil.isEmpty(pedidos)) {
//            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
//        }
//        return pedidos;
//    }
//
//    /**
//     * Retorna uma lista de {@link Pedido} cadatrados .
//     *
//     * @return
//     */
//    public List<Pedido> getAceitosPendentes() {
//        List<Pedido> pedidos = pedidoRepository.getAceitosPendentes() ;
//
//        if (CollectionUtil.isEmpty(pedidos)) {
//            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
//        }
//        return pedidos;
//    }
//
//    /**
//     * Retorna uma lista de {@link Pedido} cadatrados .
//     *
//     * @return
//     */
//    public List<Pedido> getPedidoAceito() {
//        List<Pedido> pedidos = pedidoRepository.getPedidosAceitos() ;
//
//        if (CollectionUtil.isEmpty(pedidos)) {
//            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
//        }
//        return pedidos;
//    }
//
//    /**
//     * Retorno um a {@link Pedido} pelo Id informado.
//     * @param id - id to Pedido
//     * @return
//     */
//    public Pedido getById(Long id){
//        Optional<Pedido> pedidoOptional = pedidoRepository.findByIdFetch(id);
//
//        if(!pedidoOptional.isPresent()){
//            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
//        }
//        return pedidoOptional.get();
//    }
//
//    private void buscaObjetoTamanho(Pedido pedido){
//        Tamanho t;
//
//        if(pedido.getTamanho() != null && pedido.getTamanho().getId() != null){
//            t = tamanhoService.getById(pedido.getTamanho().getId());
//            pedido.setTamanho(t);
//        } else {
//            pedido.setTamanho(null);
//        }
//    }
//
//    private void buscaObjetosSabores(Pedido pedido) {
//        Sabor s;
//
//        if(pedido.getSaborUm() != null && pedido.getSaborUm().getId() != null){
//            s = saborService.getById(pedido.getSaborUm().getId());
//            pedido.setSaborUm(s);
//        } else {
//            pedido.setSaborUm(null);
//        }
//
//        if(pedido.getSaborDois() != null && pedido.getSaborDois().getId() != null){
//            s = saborService.getById(pedido.getSaborDois().getId());
//            pedido.setSaborDois(s);
//        } else {
//            pedido.setSaborDois(null);
//        }
//
//        if(pedido.getSaborTres() != null && pedido.getSaborTres().getId() != null){
//            s = saborService.getById(pedido.getSaborTres().getId());
//            pedido.setSaborTres(s);
//        } else {
//            pedido.setSaborTres(null);
//        }
//
//        if(pedido.getSaborQuatro() != null && pedido.getSaborQuatro().getId() != null){
//            s = saborService.getById(pedido.getSaborQuatro().getId());
//            pedido.setSaborQuatro(s);
//        } else {
//            pedido.setSaborQuatro(null);
//        }
//
//        if(pedido.getSaborCinco() != null && pedido.getSaborCinco().getId() != null){
//            s = saborService.getById(pedido.getSaborCinco().getId());
//            pedido.setSaborCinco(s);
//        } else {
//            pedido.setSaborCinco(null);
//        }
//    }
//
//    private void prepararParaSalvar(Pedido pedido) {
//
//        if (pedido.getSaborDois() != null && pedido.getSaborDois().getId() == null) {
//            pedido.setSaborDois(null);
//        }
//
//        if (pedido.getSaborTres() != null && pedido.getSaborTres().getId() == null) {
//            pedido.setSaborTres(null);
//        }
//
//        if (pedido.getSaborQuatro() != null && pedido.getSaborQuatro().getId() == null) {
//            pedido.setSaborQuatro(null);
//        }
//
//        if (pedido.getSaborCinco() != null && pedido.getSaborCinco().getId() == null) {
//            pedido.setSaborCinco(null);
//        }
//
//    }
//
//    public Pedido remover(Long id){
//        Pedido pedido = this.getById(id);
//
//        pedidoRepository.delete(pedido);
//
//        return pedido;
//    }
//
//
//
//    /**
//     * Verifica se os Campos Obrigatórios foram preenchidos.
//     *
//     * @param pedido
//     */
//    private void validarCamposObrigatorios(final Pedido pedido) {
//        boolean vazio = Boolean.FALSE;
//
//        if (Util.isEmpty(pedido.getNome())) {
//            vazio = Boolean.TRUE;
//        }
//
//        if (Util.isEmpty(pedido.getNumero())) {
//            vazio = Boolean.TRUE;
//        }
//
//        if (pedido.getTamanho()==null || pedido.getTamanho().getId()== null) {
//            vazio = Boolean.TRUE;
//        }
//
//        if (pedido.getSaborUm()==null || pedido.getSaborUm().getId()== null) {
//            vazio = Boolean.TRUE;
//        }
//
//        if (pedido.getEntregar()==null) {
//            vazio = Boolean.TRUE;
//        }
//
//        if (pedido.getDataEntrega()==null) {
//            vazio = Boolean.TRUE;
//        }
//
//        if (Util.isEmpty(pedido.getStatus())) {
//            vazio = Boolean.TRUE;
//        }
//
//        if (vazio) {
//            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
//        }
//    }
//
//    /**
//     * Salva a instânica de {@link Pedido} na base de dados conforme os critérios
//     * especificados na aplicação.
//     *
//     * @param pedido
//     * @return
//     */
//    public Pedido salvar(Pedido pedido) {
//
//        if(pedido.getId() == null && Util.isEmpty(pedido.getStatus())){
//            pedido.setStatus("pendente");
//            pedido.setEntregar(StatusSimNao.SIM);
//            pedido.setStatusEntrega(false);
//
//            //Adicionado, caso o id nao exista ele vai criar um pedido com campo false
//            //No quesito producao
//            pedido.setStatusProducao(false);
//        } else {
//            buscaObjetosSabores(pedido);
//            buscaObjetoTamanho(pedido);
//
//        }
//
//        prepararParaSalvar(pedido);
//        validarCamposObrigatorios(pedido);
//
//
//        Pedido pedidoSaved = pedidoRepository.save(pedido);
//        return pedidoSaved;
//    }
//
//    /**Lista os pedidos entregues*/
//    public List<Pedido> listarEntregues() {
//        List<Pedido> pedidos = pedidoRepository.listarEntregues() ;
//
//        if (CollectionUtil.isEmpty(pedidos)) {
//            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
//        }
//        return pedidos;
//    }
//
//    /**Lista os pedidos a serem entregues*/
//    public List<Pedido> listarNaoEntregues() {
//        List<Pedido> pedidos = pedidoRepository.listarNaoEntregues() ;
//
//        if (CollectionUtil.isEmpty(pedidos)) {
//            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
//        }
//        return pedidos;
//    }
}
