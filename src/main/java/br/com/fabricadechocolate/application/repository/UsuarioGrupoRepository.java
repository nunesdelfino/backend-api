package br.com.fabricadechocolate.application.repository;

import br.com.fabricadechocolate.application.enums.StatusAtivoInativo;
import br.com.fabricadechocolate.application.model.Grupo;
import br.com.fabricadechocolate.application.model.Usuario;
import br.com.fabricadechocolate.application.model.UsuarioGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Long> {

    UsuarioGrupo findByUsuario(Usuario usuario);

    @Query("select distinct g from UsuarioGrupo ug " +
            "LEFT JOIN ug.usuario u " +
            "LEFT JOIN ug.grupo g " +
            "LEFT JOIN FETCH g.grupoFuncionalidades gf " +
            "LEFT JOIN FETCH gf.funcionalidade f " +
            "where ug.usuario.id=:idUsuario")
    List<Grupo> findByIdUsuario(Long idUsuario);

    @Query("select distinct ug.grupo from UsuarioGrupo ug " +
            "LEFT JOIN ug.usuario u " +
            "LEFT JOIN ug.grupo g " +
            "where ug.grupo.status=:status " +
            "and ug.grupo.nome=:nome")
    List<Grupo> findByFiltro(StatusAtivoInativo status, String nome);
}
