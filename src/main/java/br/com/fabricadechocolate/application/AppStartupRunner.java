package br.com.fabricadechocolate.application;

import br.com.fabricadechocolate.application.enums.StatusAtivoInativo;
import br.com.fabricadechocolate.application.enums.StatusSimNao;
import br.com.fabricadechocolate.application.model.*;
import br.com.fabricadechocolate.application.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe para ser executado após o Spring Inicializar.
 * Verificado o modo de start para spring.jpa.hibernate.ddl-auto==create-drop
 */

@Component
public class AppStartupRunner implements ApplicationRunner {

    public static final String CREATE_DROP="create-drop";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    private static final Logger LOG =
            LoggerFactory.getLogger(AppStartupRunner.class);


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Autowired
    ModuloRepository moduloRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    TamanhoRepository tamanhoRepository;

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Application started with option names : {}",
                args.getOptionNames());
        LOG.info("spring.jpa.hibernate.ddl-auto={}",ddlAuto);

        if(this.ddlAuto.trim().equals(CREATE_DROP) ||
                this.ddlAuto.trim().equals(CREATE) ||
                this.ddlAuto.trim().equals(UPDATE)
        ){
            this.initiateDemoInstance();
            this.createTamanho();
            this.createSabor();
        }
    }

    private void initiateDemoInstance() {
        LOG.info("initiateDemoInstance");
        Modulo moduloUsuario = createModuloCrud("USUARIO", "Manter Usuário");

        Modulo moduloGrupo = createModuloCrud("GRUPO", "Manter Grupo");

        Grupo grupo = createGrupoAdmin(Arrays.asList(moduloUsuario, moduloGrupo));

        createUsuarioAdmin(grupo);

    }

    private void createTamanho() {
        Tamanho t = new Tamanho();

        t.setTamanho("100");
        t.setAtivo("S");

        t = tamanhoRepository.save(t);
    }

    private void createSabor() {
        Sabor s = new Sabor();

        s.setSabor("Brigadeiro");
        s.setAtivo("S");

        s = saborRepository.save(s);
    }

    /**
     * Cria o Modulo de tipo de amigo e salva.
     * @return tipo amigo salvo no banco.
     */
    private Modulo createModuloTipoAmigo() {
        Modulo moduloTipoAmigo = new Modulo();

        moduloTipoAmigo.setMnemonico("TIPOAMIGO");
        moduloTipoAmigo.setNome("Manter Tipo Amigo ");
        moduloTipoAmigo.setStatus(StatusAtivoInativo.ATIVO);
        moduloTipoAmigo = moduloRepository.save(moduloTipoAmigo);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesCrud().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloTipoAmigo);
        }

        moduloTipoAmigo.setFuncionalidades(funcionalidades);
        moduloTipoAmigo = moduloRepository.save(moduloTipoAmigo);

        return moduloTipoAmigo;
    }

    /**
     * Cria o Modulo de amigo e salva.
     * @return tipo amigo salvo no banco.
     */
    private Modulo createModuloAmigo() {
        Modulo moduloAmigo = new Modulo();

        moduloAmigo.setMnemonico("AMIGO");
        moduloAmigo.setNome("Manter Amigo ");
        moduloAmigo.setStatus(StatusAtivoInativo.ATIVO);
        moduloAmigo = moduloRepository.save(moduloAmigo);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesCrud().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        Funcionalidade fAmigo = new Funcionalidade();
        fAmigo.setMnemonico("STATUS");
        fAmigo.setNome("É Amigo");
        fAmigo.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fAmigo);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloAmigo);
        }

        moduloAmigo.setFuncionalidades(funcionalidades);
        moduloAmigo = moduloRepository.save(moduloAmigo);

        return moduloAmigo;
    }

    private void createUsuarioAdmin(Grupo grupo) {
        Usuario usuario = new Usuario();
        usuario.setStatus(StatusAtivoInativo.ATIVO);
        usuario.setDataCadastrado(LocalDate.now());
        usuario.setDataAtualizado(LocalDate.now());
//        usuario.setTelefones(new HashSet<>());
//        usuario.setCpf("10010010017");
        usuario.setLogin("admin");
        usuario.setNome("Administrador");
//        usuario.setEmail("admin@teste.com.br");
        // TODO
        usuario.setSenha(new BCryptPasswordEncoder().encode("admin"));

        usuario = usuarioRepository.save(usuario);

        Set<UsuarioGrupo> usuarioGrupos = new HashSet<>();
        usuarioGrupos.add(new UsuarioGrupo(null,usuario,grupo));
        usuario.setGrupos(usuarioGrupos);
        usuario = usuarioRepository.save(usuario);
    }

    private Grupo createGrupoAdmin(List<Modulo> modulos) {
        Grupo grupo = new Grupo();
        grupo.setNome("Administradores");
        grupo.setAdministrador(StatusSimNao.SIM);
        grupo.setDescricao("Grupo de Administradores");
        grupo.setStatus(StatusAtivoInativo.ATIVO);
        grupo = grupoRepository.save(grupo);
        final Grupo lGrupo = grupo;
        grupo.setGrupoFuncionalidades(new HashSet<>());

        modulos.forEach(modulo -> {
            lGrupo.getGrupoFuncionalidades().addAll(
                    modulo.getFuncionalidades().stream().map(
                            funcionalidade -> new GrupoFuncionalidade(null, lGrupo, funcionalidade)
                    ).collect(Collectors.toSet())
            );
        });

        grupoRepository.save(grupo);
        return grupo;
    }

    private Modulo createModuloCrud(String moduloMNemonico, String moduloNome) {
        Modulo moduloUsuario = new Modulo();

        moduloUsuario.setMnemonico(moduloMNemonico);
        moduloUsuario.setNome(moduloNome);
        moduloUsuario.setStatus(StatusAtivoInativo.ATIVO);
        moduloUsuario = moduloRepository.save(moduloUsuario);

        final Modulo lModuloUsuario = moduloUsuario;
        Set<Funcionalidade> funcionaldiades = getFuncionalidadesCrud();

/*        funcionaldiades.stream().map(
                funcionalidade -> {
                    funcionalidade.setModulo(lModuloUsuario);
                    return funcionalidade;
                }).collect(Collectors.toSet());
        // equivalente com for*/
        for(Funcionalidade funcionalidade: funcionaldiades){
            funcionalidade.setModulo(moduloUsuario);
        }

        moduloUsuario.setFuncionalidades(funcionaldiades);
        moduloUsuario = moduloRepository.save(moduloUsuario);
        return moduloUsuario;
    }

    /**
     * retorna Funcionalidades padrão de um CRRUD
     * @return
     */
    private Set<Funcionalidade> getFuncionalidadesCrud() {
        Set<Funcionalidade> funcionalidades = new HashSet<>();

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("INCLUIR");
        fManter.setNome("Incluir");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("ALTERAR");
        fManter.setNome("Alterar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("ATIVAR_INATIVAR");
        fManter.setNome("Ativar/Inativar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("PESQUISAR");
        fManter.setNome("Pesquisar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("VISUALIZAR");
        fManter.setNome("Visualizar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);
        return funcionalidades;
    }
}
